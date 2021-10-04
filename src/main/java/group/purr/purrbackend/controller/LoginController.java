package group.purr.purrbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import group.purr.purrbackend.config.TokenProperties;
import group.purr.purrbackend.constant.HttpConstants;
import group.purr.purrbackend.dto.AuthorDTO;
import group.purr.purrbackend.entity.Author;
import group.purr.purrbackend.security.JwtUtils;
import group.purr.purrbackend.service.JwtService;
import group.purr.purrbackend.utils.IPUtil;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private JwtService jwtService;

    private final TokenProperties tokenProperties;

    @GetMapping("/login")
    public ResultVO login(@RequestBody JSONObject loginJSON, HttpServletRequest request){
        // 获取用户信息
        String username = loginJSON.getString("username");
        String password = loginJSON.getString("password");
//        String IPAddress = IPUtil.getIP(request);
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");
        // 检验密码是否正确

        // 如果登陆失败，检验IP封禁次数

        // 登陆成功返回access-token和refresh-token

        return ResultVOUtil.success(true);
    }

    private Boolean passwordCheck(String password){
        return true;
    }

    @GetMapping("/getToken")
    public ResultVO getToken(HttpServletResponse response, @RequestParam("userId") String userId){
        String accessToken = JwtUtils.tokenGeneration(userId,
                tokenProperties.getUserId(),
                tokenProperties.getAccessTokenExpireSecond(),
                tokenProperties.getSecretKey());

        String refreshToken = JwtUtils.tokenGeneration(userId,
                tokenProperties.getUserId(),
                tokenProperties.getRefreshTokenExpiredSecond(),
                tokenProperties.getSecretKey());

        response.addHeader(tokenProperties.getAuthorizationHeaderName(), accessToken);
        response.addHeader(tokenProperties.getRefreshHeaderName(), refreshToken);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(tokenProperties.getAuthorizationHeaderName(), accessToken);
        jsonObject.put(tokenProperties.getRefreshHeaderName(), refreshToken);
        return ResultVOUtil.success(jsonObject);
    }
}
