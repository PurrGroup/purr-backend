package group.purr.purrbackend.utils;

import group.purr.purrbackend.constant.HttpConstants;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    /**
     * Thumbnail width.
     */
    private static final int THUMB_WIDTH = 256;

    /**
     * Thumbnail height.
     */
    private static final int THUMB_HEIGHT = 256;


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
    public static Integer checkAndCreateFolder(String workDir) {

        // Get work path
        Path workPath = Paths.get(workDir);

        if (!Files.isDirectory(workPath)) {

            File folder = new File(workDir);

            if (!folder.mkdirs()) {
                log.error("create upload path " + workDir + " failed");
                return -1;
            }
            return 1;

        } else {
            if (Files.isReadable(workPath) && Files.isWritable(workPath)) {
                return 1;
            } else {
                log.error("no permission to read or write upload path" + workDir);
                return -2;
            }
        }

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

    public static boolean generateThumbNail(String thumbUrl, InputStream originalFile) throws IOException {
        // TODO refactor this: if image is ico ext. extension
        BufferedImage thumbImage = ImageIO.read(originalFile);
        Assert.notNull(thumbImage, "Image must not be null");
        Assert.notNull(thumbUrl, "Thumb path must not be null");

        Path thumbPath = Paths.get(thumbUrl);

        boolean result = false;
        // Create the thumbnail
        try {
            Files.createFile(thumbPath);
            // Convert to thumbnail and copy the thumbnail
            log.debug("Trying to generate thumbnail: [{}]", thumbPath);
            Thumbnails.of(thumbImage).size(THUMB_WIDTH, THUMB_HEIGHT).keepAspectRatio(true)
                    .toFile(thumbPath.toFile());
            log.info("Generated thumbnail image, and wrote the thumbnail to [{}]",
                    thumbPath);
            result = true;
        } catch (Throwable t) {
            // Ignore the error
            log.warn("Failed to generate thumbnail: " + thumbPath, t);
        } finally {
            // Disposes of this graphics context and releases any system resources that it is using.
            thumbImage.getGraphics().dispose();
        }
        return result;

    }
}
