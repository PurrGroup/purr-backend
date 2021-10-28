package group.purr.purrbackend.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static group.purr.purrbackend.constant.ExternalAPIConstants.*;

public class PurrUtils {

    private static final List<String> IMAGE_SET_VALUES = Arrays.asList("jpg", "png", "gif", "svg", "jpeg", "JPG", "PNG", "GIF", "SVG", "JPEG");
    private static final Set<String> IMAGE_TYPE_SET = new HashSet<>(IMAGE_SET_VALUES);


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

    public static void checkAndCreate(String folderPath){
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
    }

    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

    public static Integer getFileType(String file){
        int begin = file.lastIndexOf(".");
        String ext =  file.substring(begin+1);
        //picture
        if(IMAGE_TYPE_SET.contains(ext)){
            return 0;
        }
        else{
            return 1;
        }
    }

    public static void createFile(String filePath) {
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException ignored) {

        }
    }



}
