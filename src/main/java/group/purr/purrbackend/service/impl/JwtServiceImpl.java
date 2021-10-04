package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.config.JwtConfig;
import group.purr.purrbackend.service.JwtService;
import group.purr.purrbackend.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
//import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;
import com.nimbusds.jose.jwk.RSAKey;


/**
 * 暂时没用
 */
@Service
public class JwtServiceImpl implements JwtService {

    private JwtConfig jwtConfig;

    private RSAKey rsaKey;

    private InputStream getCertInputStream() throws IOException{
        String jksFile = jwtConfig.getJksFileName();

        // 本地文件
        if(jksFile.contains("://")){
            return new FileInputStream(new File(jksFile));
        }

        // classpath
        return getClass().getClassLoader().getResourceAsStream(jwtConfig.getJksFileName());
    }

    @Override
    public RSAKey generateKey() {
        if(rsaKey != null)
            return rsaKey;

        InputStream inputStream = null;
        try{
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

            inputStream = getCertInputStream();
            keyStore.load(inputStream, jwtConfig.getJksPassword().toCharArray());

            Enumeration<String> aliases = keyStore.aliases();
            String alias = null;
            while (aliases.hasMoreElements())
                alias = aliases.nextElement();

            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyStore.getKey(alias, jwtConfig.getCertPassword().toCharArray());
            Certificate certificate = keyStore.getCertificate(alias);
            RSAPublicKey rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();
            rsaKey = new RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).build();

            return rsaKey;

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException e) {
            e.printStackTrace();

            // TODO: throw KeyGenerationException
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public String sign(String payload) {
        return null;
    }

    @Override
    public String verify(String token) {
        return null;
    }
}
