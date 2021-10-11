package group.purr.purrbackend.controller.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import group.purr.purrbackend.constant.TokenConstants;
import group.purr.purrbackend.exception.DenialOfServiceException;
import group.purr.purrbackend.exception.TokenExpiredException;
import group.purr.purrbackend.security.JwtUtils;
import group.purr.purrbackend.service.AuthorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthorService authorService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 判断是否为系统签发的token
     * @param authorizationHeader
     * @return
     */
    private Boolean checkTokenAuthorizationHeader(String authorizationHeader) {
        // 判空
        if (StringUtils.isBlank(authorizationHeader)) {
            log.error("token是空的");
            return false;
        }

//        // “Bearer”开头
//        if (!StringUtils.startsWith(authorizationHeader, TokenConstants.tokenHeaderPrefix))
//        {
//            log.error("token不是以Bearer开头的");
//            return false;
//        }

        return true;
    }

    private String getTokenContent(String authorizationToken){
//        return StringUtils.substring(authorizationToken, TokenConstants.tokenHeaderPrefix.length()).trim();
//        return null;
        return authorizationToken;
    }

    private void JsonResponse(HttpServletResponse httpServletResponse, String message)throws IOException {
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, String> params = new HashMap<>(3);
        params.put("msg", message);
        httpServletResponse.getWriter().print(OBJECT_MAPPER.writeValueAsString(params));
    }

    private Boolean handleExpiredToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("重新签发access token");
        String refreshTokenHeader = request.getHeader(TokenConstants.refreshHeaderName);
        if(!checkTokenAuthorizationHeader(refreshTokenHeader)){
            log.error("非系统签发token");
            throw new DenialOfServiceException();
        }

        Jws<Claims> refreshToken = JwtUtils.parserToken(getTokenContent(refreshTokenHeader), TokenConstants.secretKey);
        if(refreshToken == null){
            //JsonResponse(response, "token不合法");
            log.error("refresh token不合法");
            throw new DenialOfServiceException();
            //return false;
        }

        if(JwtUtils.checkIsExpired(refreshToken)){
            //JsonResponse(response, "refresh token已经过期");
            log.error("refresh token 已经过期");
            throw new TokenExpiredException();
        }

        String newToken = JwtUtils.tokenGeneration(
                refreshToken.getBody().get(TokenConstants.userKey),
                TokenConstants.userKey,
                TokenConstants.accessTokenExpireSecond,
                TokenConstants.secretKey
        );
        response.addHeader(TokenConstants.accessHeaderName, newToken);

        JwtUtils.buildAuthentication(JwtUtils.parserToken(newToken, TokenConstants.secretKey), TokenConstants.userKey);

        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.error("lalalala");
        String authorizationHeader = request.getHeader(TokenConstants.accessHeaderName);

        // 判断token是否合法
        if(!checkTokenAuthorizationHeader(authorizationHeader)){
            log.error("非系统签发token");
            throw new DenialOfServiceException();
        }

//        log.error("获取到了token");
//        log.error(authorizationHeader);
        // 获取token内容
        String token = getTokenContent(authorizationHeader);
//        log.error(token);
        Jws<Claims> jws = JwtUtils.parserToken(token, TokenConstants.secretKey);

        if(jws == null){
            log.error("token为空");
            throw new DenialOfServiceException();
        }

        // 从数据库读取加密后的密码
        String encryptedPassword = authorService.getEncryptedPassword();
        // 密码验证不正确
        if(!encryptedPassword.equals(jws.getBody().get(TokenConstants.userKey, String.class))){
            log.error(jws.getBody().get(TokenConstants.userKey, String.class));
            log.error(encryptedPassword);
            log.error("登陆凭证不符");
            throw new DenialOfServiceException();
        }

        if(JwtUtils.checkIsExpired(jws)){
            return handleExpiredToken(request, response);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
