package group.purr.purrbackend.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtil {
    private EncryptUtil(){}

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String password){
        return encoder.encode(password).trim();
    }

    public static Boolean checkPassword(String password, String encryptedPassword){
        return encoder.matches(password, encryptedPassword);
    }
}
