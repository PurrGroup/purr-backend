package group.purr.purrbackend.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PurrConfigConstants {
    public static final Long accessTokenExpireSecond = 7 * 24 * 60 * 60L;
    public static final String tokenHeaderPrefix = "Bearer";
    public static final String accessHeaderName = "Access-Token";
    public static final Long refreshTokenExpiredSecond = 15 * 24 * 60 * 60L;
    public static final String refreshHeaderName = "Refresh-Token";
    public static final String userKey = "Encrypted-Password";
    public static final String accessExpiredTime = "Access-Token-Expired-Time";
    public static String secretKey = "";
    public static String uploadPath = "";

    @Value("${purr.security.secretKey}")
    public void setSecretKey(String key) {
        secretKey = key;
    }

    @Value("${purr.media.path}")
    public void setMediaUploadPath(String path) {
        uploadPath = path;
    }
}
