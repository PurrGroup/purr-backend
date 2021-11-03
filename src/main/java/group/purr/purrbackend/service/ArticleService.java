package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.ArticleDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    /**
     * 创建新文章接口
     *
     * @param articleDTO
     * @return 返回文章ID
     */
    Long createArticle(ArticleDTO articleDTO);

    void updateArticle(ArticleDTO articleDTO);

    void deleteArticleTags(Long articleId);

    void deleteArticleById(Long articleId);

    void deleteAll();

    void addTag(Long articleID, Long tagID);

    List<ArticleDTO> findRecentArticle(Pageable pageable);

    Long getTotal();

    Long getTotalExceptDeleted();

    String getArticleUrlByID(Long postID);

    List<ArticleDTO> findRecommendedArticle();

    ArticleDTO getArticleByID(Long postID);

    ArticleDTO getArticleByLinkName(String linkName);
}
