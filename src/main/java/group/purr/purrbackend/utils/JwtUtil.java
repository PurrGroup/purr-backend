package group.purr.purrbackend.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;

import java.text.ParseException;

/**
 * 暂时没用
 */
public class JwtUtil {
    public static final String DEFAULT_TOKEN_NAME = "";
    public static final String ACCESS_TOKEN_NAME = "access_token";
    public static final String REFRESH_TOKEN_NAME = "refresh_token";

    public static String tokenGeneration(String password) {
        return "";
    }



    /**
     * 使用RSA算法进行签名
     * @param info
     * @param rsaKey
     * @return
     */
    public static String signByRSA(String info, RSAKey rsaKey){
        try{
            JWSSigner jwsSigner = new RSASSASigner(rsaKey);
            JWSObject jwsObject = new JWSObject(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
                    new Payload(info)
            );

            jwsObject.sign(jwsSigner);

            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();

            // TODO: throw new PayLoadSignException
        }

        return null;
    }

    /**
     * 使用RSA算法进行验证
     * @param token
     * @param rsaKey
     * @return
     */
    public static String verifyByRSA(String token, RSAKey rsaKey){
        try{
            RSAKey publicKey = rsaKey.toPublicJWK();
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier jwsVerifier = new RSASSAVerifier(publicKey);

            if(jwsObject.verify(jwsVerifier)){
                return jwsObject.getPayload().toString();
            }

            // TODO: throw TokenVerifyException
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();

            // TODO: throw TokenVerifyException
        }

        return null;
    }
}
