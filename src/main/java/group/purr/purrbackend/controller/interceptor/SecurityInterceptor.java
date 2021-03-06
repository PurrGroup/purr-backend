package group.purr.purrbackend.controller.interceptor;

import group.purr.purrbackend.constant.PurrConfigConstants;
import group.purr.purrbackend.exception.AccessTokenExpiredException;
import group.purr.purrbackend.exception.DenialOfServiceException;
import group.purr.purrbackend.security.JwtUtils;
import group.purr.purrbackend.service.AuthorService;
import group.purr.purrbackend.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TokenService tokenService;

    private String getTokenContent(String authorizationToken) {
        return authorizationToken;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader(PurrConfigConstants.accessHeaderName);

        // 判断token是否合法
        if (!tokenService.checkTokenAuthorizationHeader(authorizationHeader)) {
            log.error("非系统签发token");
            throw new DenialOfServiceException();
        }

        // 获取token内容
        String token = getTokenContent(authorizationHeader);
        Jws<Claims> jws = JwtUtils.parserToken(token, PurrConfigConstants.secretKey);

        if (jws == null) {
            log.error("当前请求未携带token");
            throw new DenialOfServiceException();
        }

        // 从数据库读取加密后的密码
        String encryptedPassword = authorService.getEncryptedPassword();
        String passInRequest = jws.getBody().get(PurrConfigConstants.userKey, String.class);
        // 密码验证不正确
        if (!encryptedPassword.equals(passInRequest)) {
            log.error(String.format("登录凭证不符，encryptedPassword: %s, passInRequest: %s", encryptedPassword, passInRequest));
            throw new DenialOfServiceException();
        }

        if (JwtUtils.checkIsExpired(jws)) {
            log.info("access-token过期");
            throw new AccessTokenExpiredException();
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
