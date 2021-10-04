package group.purr.purrbackend.security;

import group.purr.purrbackend.exception.DenialOfServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import group.purr.purrbackend.config.TokenProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TokenAuthenticateFilter extends OncePerRequestFilter {

    private final TokenProperties tokenProperties;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticateFilter.class);
    /**
     * 判断是否为系统签发的token
     * @param authorizationHeader
     * @return
     */
    private Boolean checkTokenAuthorizationHeader(String authorizationHeader){
        if(StringUtils.isBlank(authorizationHeader))
            return false;
        if(!StringUtils.startsWith(authorizationHeader, tokenProperties.getTokenHeaderPrefix()))
            return false;

        return true;
    }

    private String getTokenContent(String authorizationToken){
        return StringUtils.substring(authorizationToken, tokenProperties.getTokenHeaderPrefix().length()).trim();
    }

    private void JsonResponse(HttpServletResponse httpServletResponse, String message)throws IOException{
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, String> params = new HashMap<>(3);
        params.put("msg", message);
        httpServletResponse.getWriter().print(OBJECT_MAPPER.writeValueAsString(params));
    }

    private void handleExpiredToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException{
        String refreshTokenHeader = httpServletRequest.getHeader(tokenProperties.getRefreshHeaderName());
        if(!checkTokenAuthorizationHeader(refreshTokenHeader)){
            log.debug("非系统签发token");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Jws<Claims> refreshToken = JwtUtils.parserToken(getTokenContent(refreshTokenHeader), tokenProperties.getSecretKey());
        if(refreshToken == null){
            JsonResponse(httpServletResponse, "token不合法");
            return;
        }

        if(JwtUtils.checkIsExpired(refreshToken)){
            JsonResponse(httpServletResponse, "refresh token已经过期");
            return;
        }

        String newToken = JwtUtils.tokenGeneration(
                refreshToken.getBody().get(tokenProperties.getUserId()),
                tokenProperties.getUserId(),
                tokenProperties.getAccessTokenExpireSecond(),
                tokenProperties.getSecretKey()
        );
        httpServletResponse.addHeader(tokenProperties.getAuthorizationHeaderName(), newToken);

        JwtUtils.buildAuthentication(JwtUtils.parserToken(newToken, tokenProperties.getSecretKey()), tokenProperties.getUserId());

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(tokenProperties.getAuthorizationHeaderName());
//        logger.error("doFilterInternal");
        if(!checkTokenAuthorizationHeader(authorizationHeader)){
//            log.error("非系统签发token");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            throw new DenialOfServiceException();
            return;
        }

        String token = getTokenContent(authorizationHeader);
        Jws<Claims> jws = JwtUtils.parserToken(token, tokenProperties.getSecretKey());
        if(jws == null){
            logger.debug("filter");
            // TODO: throw jwsIllegalException???
//            throw new DenialOfServiceException();
            JsonResponse(httpServletResponse, "token不合法");
//            return;
        }

        if(JwtUtils.checkIsExpired(jws)){
            handleExpiredToken(httpServletRequest, httpServletResponse, filterChain);
            return;
        }
    }
}
