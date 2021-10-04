package group.purr.purrbackend.config;

import lombok.Data;

@Data
public class TokenProperties {
    private String secretKey;
    private Long accessTokenExpireSecond;
    private String tokenHeaderPrefix;
    private String authorizationHeaderName;
    private Long refreshTokenExpiredSecond;
    private String refreshHeaderName;
    private String userId;
}
