package group.purr.purrbackend.controller;

import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.constant.TokenConstants;
import group.purr.purrbackend.dto.TokenDTO;
import group.purr.purrbackend.exception.DenialOfServiceException;
import group.purr.purrbackend.exception.WrongPasswordException;
import group.purr.purrbackend.security.JwtUtils;
import group.purr.purrbackend.service.AuthorService;
import group.purr.purrbackend.service.IPService;
import group.purr.purrbackend.utils.EncryptUtil;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录验证、登陆保持、登出
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private IPService ipService;

    @PostMapping("/login")
    public ResultVO login(@RequestBody JSONObject loginJSON, HttpServletRequest request){
        log.info("login");
        // 获取用户信息
        String username = loginJSON.getString("username");
        String password = loginJSON.getString("password");
//        String IPAddress = IPUtil.getIP(request);
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        Date date = new Date();

        String encryptedPassword = authorService.getEncryptedPassword();

        log.info("check user agent");
        if(ua == null){
            ipService.loginFailed(ip, date);
            ipService.checkLockdownStatus(ip, date);
            throw new DenialOfServiceException();
        }

        if(ipService.isIPLockdown(ip)){
            // 检验是否被封禁 如果封禁则增加一次失败记录
            ipService.loginFailed(ip, date);
            ipService.checkLockdownStatus(ip, date);
            throw new DenialOfServiceException();
        }

        log.info("check password");
        // 检验密码是否正确
        if(!EncryptUtil.checkPassword(password, encryptedPassword)){
            log.info("密码不正确");
            // 如果登陆失败，增加一次失败记录，检验IP封禁次数
            ipService.loginFailed(ip, date);
            ipService.checkLockdownStatus(ip, date);
            throw new WrongPasswordException();
        }
        log.info("password correct");

        // 登陆成功返回access-token和refresh-token
        String accessToken = JwtUtils.tokenGeneration(encryptedPassword,
                TokenConstants.userKey,
                TokenConstants.accessTokenExpireSecond,
                TokenConstants.secretKey);

        String refreshToken = JwtUtils.tokenGeneration(encryptedPassword,
                TokenConstants.userKey,
                TokenConstants.refreshTokenExpiredSecond,
                TokenConstants.secretKey);

        Date expiredTime = JwtUtils.getExpiredTimeFromToken(accessToken);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(accessToken);
        tokenDTO.setRefreshToken(refreshToken);
        tokenDTO.setAccessExpiredTime(expiredTime);

        return ResultVOUtil.success(tokenDTO);
    }

//    @GetMapping("/getToken")
//    public ResultVO getToken(HttpServletResponse response, @RequestParam("userId") String userId){
//        String accessToken = JwtUtils.tokenGeneration(userId,
//                tokenProperties.getUserId(),
//                tokenProperties.getAccessTokenExpireSecond(),
//                tokenProperties.getSecretKey());
//
//        String refreshToken = JwtUtils.tokenGeneration(userId,
//                tokenProperties.getUserId(),
//                tokenProperties.getRefreshTokenExpiredSecond(),
//                tokenProperties.getSecretKey());
//
//        response.addHeader(tokenProperties.getAuthorizationHeaderName(), accessToken);
//        response.addHeader(tokenProperties.getRefreshHeaderName(), refreshToken);
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put(tokenProperties.getAuthorizationHeaderName(), accessToken);
//        jsonObject.put(tokenProperties.getRefreshHeaderName(), refreshToken);
//        return ResultVOUtil.success(jsonObject);
//    }
}
