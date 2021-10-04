package group.purr.purrbackend.config;

import group.purr.purrbackend.utils.JwtUtil;

/**
 * 暂时没用
 */
public class JwtConfig {
    private String tokenName = JwtUtil.DEFAULT_TOKEN_NAME;

    // JKS密钥路径 用于RSA算法
    private String jksFileName;
    // TODO: set(jwt.jks)

    // JKS密钥密码 用于RSA算法
    private String jksPassword;
    // TODO: purrjwt

    // 证书密码 用于支持RSA算法
    private String certPassword;
    // TODO: purrjwt

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

    public String getJksFileName() { return jksFileName; }

    public void setJksFileName(String jksFileName) {
        this.jksFileName = jksFileName;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Long getNotBeforeIn() {
        return notBeforeIn;
    }

    public void setNotBeforeIn(Long notBeforeIn) {
        this.notBeforeIn = notBeforeIn;
    }

    public Long getNotBeforeAt() {
        return notBeforeAt;
    }

    public void setNotBeforeAt(Long notBeforeAt) {
        this.notBeforeAt = notBeforeAt;
    }

    public Long getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(Long expiredIn) {
        this.expiredIn = expiredIn;
    }

    public Long getExpiredAt() {
        return ExpiredAt;
    }

    public void setExpiredAt(Long expiredAt) {
        ExpiredAt = expiredAt;
    }

    public String getJksPassword() { return jksPassword; }

    public void setJksPassword(String jksPassword) { this.jksPassword = jksPassword; }
}
