package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.config.JwtConfig;
import group.purr.purrbackend.service.JwtService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.interfaces.RSAKey;

@Service
public class JwtServiceImpl implements JwtService {

    private JwtConfig jwtConfig;

    private RSAKey rsaKey;

    private InputStream getCertInputStream() throws IOException{
        String jskFile = jwtConfig.
    }

    @Override
    public RSAKey generateKey() {
        if(rsaKey != null)
            return rsaKey;

        InputStream inputStream = null;
        try{
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream
        } catch (KeyStoreException e) {
            e.printStackTrace();
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
