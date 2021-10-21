package group.purr.purrbackend.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static group.purr.purrbackend.constant.ExternalAPIConstants.*;

public class PurrUtils {
    public static String getAvatarUrl(String qq, String email, String username){
        if(qq!=null && qq.length()!=0){
            return QQ_AVATAR_API_BASE_URL + qq;
        }
        if (email!=null && email.length()!=0) {
            return GRAVATAR_API_BASE_URL + md5Hex(email);
        }
        if(username!=null && username.length()!=0) {
            return DICE_BEAR_AVATAR_API_BASE_URL + username + ".svg";
        }

        return DEFAULT_AVATAR_URL;
    }

    public static String hex(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(Integer.toHexString((b
                    & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex (String message) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
        }
        return null;
    }
}
