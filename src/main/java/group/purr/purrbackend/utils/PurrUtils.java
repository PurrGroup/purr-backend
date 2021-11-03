package group.purr.purrbackend.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static group.purr.purrbackend.constant.ExternalAPIConstants.*;

@Slf4j
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

    public static boolean checkAndCreate(String folderPath) {
        File folder = new File(folderPath);
        if(!checkWorkDir(folderPath)){
            return false;
        }
        if (!folder.exists() && !folder.isDirectory()) {
            return folder.mkdirs();
        }
        return true;
    }

    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

    public static String getFileType(MultipartFile file) throws IOException {

        Tika tika = new Tika();

        return tika.detect(file.getInputStream());

    }

    public static void createFile(String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();
    }

    /**
     * Check work directory.
     */
    public static Boolean checkWorkDir(String fileUrl) {
        // Get work path
        Path workPath = Paths.get(fileUrl);

        // Check file type
        if (!Files.isDirectory(workPath)
                || !Files.isReadable(workPath)
                || !Files.isWritable(workPath)) {
            log.warn("Please make sure that {} is a directory, readable and writable!", fileUrl);
            return false;
        }

        return true;
    }


    public static String getFileExt(String fileName) {
        if(fileName == null){
            return "";
        }

        int begin = fileName.lastIndexOf(".");
        if(begin == -1){
            return "";
        }
        return fileName.substring(begin);
    }

    /**
     * Ensures the string contain suffix.
     *
     * @param string string must not be blank
     * @param suffix suffix must not be blank
     * @return string contain suffix specified
     */
    @NonNull
    public static String ensureSuffix(@NonNull String string, @NonNull String suffix) {
        Assert.hasText(string, "String must not be blank");
        Assert.hasText(suffix, "Suffix must not be blank");

        return StringUtils.removeEnd(string, suffix) + suffix;
    }
}
