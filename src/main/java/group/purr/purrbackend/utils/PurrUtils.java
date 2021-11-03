package group.purr.purrbackend.utils;

import group.purr.purrbackend.constant.HttpConstants;
import group.purr.purrbackend.constant.SearchConstants;
import group.purr.purrbackend.dto.*;
import group.purr.purrbackend.entity.ESEntity.ElasticArticle;
import group.purr.purrbackend.entity.ESEntity.ElasticComment;
import group.purr.purrbackend.entity.ESEntity.ElasticMoment;
import group.purr.purrbackend.entity.ESEntity.ElasticPage;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import static group.purr.purrbackend.constant.ExternalAPIConstants.*;

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
                    & 0xFF) | 0x100).substring(1, 3));
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

//  0 /articles/
//  1 /articles/year(yyyy)/month(mm)
//  2 /articles/year(yy)/month(mm)
//  3 /articles/year(yyyy)/month(mm)/day(dd)
//  4 /articles/year(yy)/month(mm)/day(dd)
    public static String getArticleLinkNamePrefix(String option){
        String prefix = "/articles";
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DATE);

        switch (option){
            case "0": prefix += "/"; break;
            case "1": prefix += "/" + year.toString() + "/" + month.toString(); break;
            case "2": prefix += "/" + year.toString().substring(2) + "/" + month.toString(); break;
            case "3": prefix += "/" + year.toString() + "/" + month.toString() + "/" + day.toString(); break;
            case "4": prefix += "/" + year.toString().substring(2) + "/" + month.toString() + "/" + day.toString(); break;
        }

        return prefix;
    }

    public static String getPageLinkNamePrefix(String option){
        String prefix = "/pages";
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DATE);

        switch (option){
            case "0": prefix += "/"; break;
            case "1": prefix += "/" + year.toString() + "/" + month.toString(); break;
            case "2": prefix += "/" + year.toString().substring(2) + "/" + month.toString(); break;
            case "3": prefix += "/" + year.toString() + "/" + month.toString() + "/" + day.toString(); break;
            case "4": prefix += "/" + year.toString().substring(2) + "/" + month.toString() + "/" + day.toString(); break;
        }

        return prefix;
    }
}
