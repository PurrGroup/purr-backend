package group.purr.purrbackend.utils;

import org.apache.logging.log4j.util.Base64Util;
import org.springframework.util.Base64Utils;

import javax.crypto.KeyGenerator;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSAUtil {
    public static final int MAX_ENCRYPT_BLOCK = 100;
    public static final int MAX_DECRYPT_BLOCK = 256;
    public static final String ALGORITHM_NAME = "rsa";

    public static KeyPair getKeyPair() throws Exception{
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    public static PrivateKey getPrivateKey(String privateKey) throws Exception{
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
        byte[] decodeKey = Base64Utils.decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeKey);
        return keyFactory.generatePrivate(keySpec);
    }
}
