package group.purr.purrbackend.config;

import group.purr.purrbackend.utils.JwtUtil;

public class JwtConfig {
    private String tokenName = JwtUtil.DEFAULT_TOKEN_NAME;

    // JKS密钥路径 用于RSA算法
    private String jksFileName;

    // 证书密码 用于支持RSA算法
    private String certPassword;

    //以下为Payload中需要携带的标准信息
    // JWT 标准信息：签发人 iss
    private String issuer;

    // JWT 标准信息：主题 sub
    private String subject;

    // JWT 标准信息：受众 aud
    private String audience;

    // JWT 标准信息：生效时间 nbf 未来多长时间内生效
    private Long notBeforeIn;

    // JWT 标准信息：生效时间 nbf 具体哪个时间生效
    private Long notBeforeAt;

    // JWT 标准信息：过期时间 exp 未来多长时间内过期
    private Long expiredIn;

    // JWT 标准信息：过期时间 exp 具体哪个时间过期
    private Long ExpiredAt;
}
