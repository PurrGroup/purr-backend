package group.purr.purrbackend.utils;

import group.purr.purrbackend.constant.SearchConstants;
import group.purr.purrbackend.dto.SearchDTO;
import group.purr.purrbackend.entity.ESEntity.ElasticArticle;
import group.purr.purrbackend.entity.ESEntity.ElasticComment;
import group.purr.purrbackend.entity.ESEntity.ElasticMoment;
import group.purr.purrbackend.entity.ESEntity.ElasticPage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sxy
 * @since 2021-10-25 23:59
 */
@Slf4j
public class ESUtil {
    private ESUtil(){}

    public static SearchDTO formatArticleToSearch(ElasticArticle article){
        SearchDTO searchDTO = new SearchDTO();

        log.info(article.getCreateTime().toString());

        searchDTO.setId(article.getID());
        searchDTO.setMysqlId(article.getID());
        searchDTO.setCategory(SearchConstants.ArticleCategory);
        searchDTO.setTitle(article.getName());
        searchDTO.setContent(article.getContent());
        searchDTO.setTags("");
        searchDTO.setUrl(article.getLinkName());
        searchDTO.setCreateTime(article.getCreateTime());
        searchDTO.setStatus(article.getStatus());

        return searchDTO;
    }

    public static SearchDTO formatPageToSearch(ElasticPage page){
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setId(page.getID());
        searchDTO.setMysqlId(page.getID());
        searchDTO.setCategory(SearchConstants.PageCategory);
        searchDTO.setTitle(page.getName());
        searchDTO.setContent(page.getContent());
        searchDTO.setTags("");
        searchDTO.setUrl(page.getUrlName());
        searchDTO.setCreateTime(page.getCreateTime());
        searchDTO.setStatus(page.getStatus());

        return searchDTO;
    }

    public static SearchDTO formatCommentToSearch(ElasticComment comment){
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setId(comment.getID());
        searchDTO.setMysqlId(comment.getID());
        searchDTO.setCategory(SearchConstants.CommentCategory);
        searchDTO.setTitle("");
        searchDTO.setContent(comment.getContent());
        searchDTO.setTags("");
        searchDTO.setUrl("");
        searchDTO.setCreateTime(comment.getDate());
        searchDTO.setStatus(1);

        return searchDTO;
    }

    public static SearchDTO formatMomentToSearch(ElasticMoment moment){
        SearchDTO searchDTO = new SearchDTO();

        searchDTO.setId(moment.getID());
        searchDTO.setMysqlId(moment.getID());
        searchDTO.setCategory(SearchConstants.MomentCategory);
        searchDTO.setTitle("");
        searchDTO.setContent(moment.getContent());
        searchDTO.setTags("");
        searchDTO.setUrl("");
        searchDTO.setCreateTime(moment.getCreateTime());
        searchDTO.setStatus(1);

        return searchDTO;
    }
}
