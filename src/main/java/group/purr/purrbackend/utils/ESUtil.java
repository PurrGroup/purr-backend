package group.purr.purrbackend.utils;

import group.purr.purrbackend.constant.SearchConstants;
import group.purr.purrbackend.dto.SearchDTO;
import group.purr.purrbackend.entity.ESEntity.ElasticArticle;
import group.purr.purrbackend.entity.ESEntity.ElasticComment;
import group.purr.purrbackend.entity.ESEntity.ElasticMoment;
import group.purr.purrbackend.entity.ESEntity.ElasticPage;

/**
 * @author sxy
 * @since 2021-10-25 23:59
 */
public class ESUtil {
    private ESUtil() {
    }

    public static SearchDTO formatArticleToSearch(ElasticArticle article) {
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

    public static SearchDTO formatPageToSearch(ElasticPage page) {
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

    public static SearchDTO formatCommentToSearch(ElasticComment comment) {
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

    public static SearchDTO formatMomentToSearch(ElasticMoment moment) {
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
