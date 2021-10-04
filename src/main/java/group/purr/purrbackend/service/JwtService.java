package group.purr.purrbackend.service;

public interface JwtService {
    Object generateKey();

    /**
     * 对信息签名
     * @param payload
     * @return
     */
    String sign(String payload);

    /**
     * 验证并返回信息
     * @param token
     * @return
     */
    String verify(String token);
}
