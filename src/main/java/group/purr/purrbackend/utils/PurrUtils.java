package group.purr.purrbackend.utils;

import group.purr.purrbackend.constant.SearchConstants;
import group.purr.purrbackend.dto.*;
import group.purr.purrbackend.entity.ESEntity.ElasticArticle;
import group.purr.purrbackend.entity.ESEntity.ElasticComment;
import group.purr.purrbackend.entity.ESEntity.ElasticMoment;
import group.purr.purrbackend.entity.ESEntity.ElasticPage;

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

    public static SearchDTO formatArticleToSearch(ElasticArticle article){
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setMysqlId(article.getID());
        searchDTO.setCategory(SearchConstants.ArticleCategory);
        searchDTO.setTitle(article.getName());
        searchDTO.setContent(article.getContent());
        searchDTO.setTags("");
        searchDTO.setUrl(article.getLinkName());
        searchDTO.setCreateTime(article.getCreateTime());

        return searchDTO;
    }

    public static SearchDTO formatPageToSearch(ElasticPage page){
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setMysqlId(page.getID());
        searchDTO.setCategory(SearchConstants.PageCategory);
        searchDTO.setTitle(page.getName());
        searchDTO.setContent(page.getContent());
        searchDTO.setTags("");
        searchDTO.setUrl(page.getUrlName());
        searchDTO.setCreateTime(page.getCreateTime());

        return searchDTO;
    }

    public static SearchDTO formatCommentToSearch(ElasticComment comment){
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setMysqlId(comment.getID());
        searchDTO.setCategory(SearchConstants.CommentCategory);
        searchDTO.setTitle("");
        searchDTO.setContent(comment.getContent());
        searchDTO.setTags("");
        searchDTO.setUrl("");
        searchDTO.setCreateTime(comment.getDate());

        return searchDTO;
    }

    public static SearchDTO formatMomentToSearch(ElasticMoment moment){
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setMysqlId(moment.getID());
        searchDTO.setCategory(SearchConstants.MomentCategory);
        searchDTO.setTitle("");
        searchDTO.setContent(moment.getContent());
        searchDTO.setTags("");
        searchDTO.setUrl("");
        searchDTO.setCreateTime(moment.getCreateTime());

        return searchDTO;
    }
}
