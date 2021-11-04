package group.purr.purrbackend.utils;

import group.purr.purrbackend.constant.HttpConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static group.purr.purrbackend.constant.ExternalAPIConstants.*;

@Slf4j
public class PurrUtils {
    private PurrUtils() {
    }

    public static String getIP(HttpServletRequest request) {
        String IP = request.getHeader(HttpConstants.X_FORWARDED_FOR);

        if (IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getHeader(HttpConstants.PROXY_CLIENT_IP);
        if (IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getHeader(HttpConstants.WL_PROXY_CLIENT_IP);
        if (IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getHeader(HttpConstants.HTTP_CLIENT_IP);
        if (IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getHeader(HttpConstants.HTTP_X_FORWARDED_FOR);
        if (IP == null || IP.length() == 0 || IP.equalsIgnoreCase(HttpConstants.UNKNOWN))
            IP = request.getRemoteAddr();

        if (IP != null && IP.contains(","))
            IP = IP.substring(0, IP.indexOf(",")).trim();

        return IP;
    }

    public static String getAvatarUrl(String qq, String email, String username) {
        String DICE_BEAR_AVATAR_URL = DICE_BEAR_AVATAR_API_BASE_URL + username + ".svg";
        if (qq != null && qq.length() != 0) {
            return QQ_AVATAR_API_BASE_URL + qq;
        }
        if (email != null && email.length() != 0) {
            return GRAVATAR_API_BASE_URL + md5Hex(email) + "?d=" + URIUtil.encodeURIComponent(DICE_BEAR_AVATAR_URL);
        }
        if (username != null && username.length() != 0) {
            return DICE_BEAR_AVATAR_URL;
        }

        return DEFAULT_AVATAR_URL;
    }

    public static String hex(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(Integer.toHexString((b
                    & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
        }
        return null;
    }

    public static boolean checkAndCreate(String folderPath) {
        File folder = new File(folderPath);
        if (!checkWorkDir(folderPath)) {
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
        if (fileName == null) {
            return "";
        }

        int begin = fileName.lastIndexOf(".");
        if (begin == -1) {
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

    //  0 /articles/
//  1 /articles/year(yyyy)/month(mm)
//  2 /articles/year(yy)/month(mm)
//  3 /articles/year(yyyy)/month(mm)/day(dd)
//  4 /articles/year(yy)/month(mm)/day(dd)
    public static String getArticleLinkNamePrefix(String option) {
        String prefix = "/articles";
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DATE);

        switch (option) {
            case "0":
                prefix += "/";
                break;
            case "1":
                prefix += "/" + year + "/" + month;
                break;
            case "2":
                prefix += "/" + year.toString().substring(2) + "/" + month;
                break;
            case "3":
                prefix += "/" + year + "/" + month + "/" + day;
                break;
            case "4":
                prefix += "/" + year.toString().substring(2) + "/" + month + "/" + day;
                break;
        }

        return prefix;
    }

    public static String getPageLinkNamePrefix(String option) {
        String prefix = "/pages";
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DATE);

        switch (option) {
            case "0":
                prefix += "/";
                break;
            case "1":
                prefix += "/" + year + "/" + month;
                break;
            case "2":
                prefix += "/" + year.toString().substring(2) + "/" + month;
                break;
            case "3":
                prefix += "/" + year + "/" + month + "/" + day;
                break;
            case "4":
                prefix += "/" + year.toString().substring(2) + "/" + month + "/" + day;
                break;
        }

        return prefix;
    }
}
