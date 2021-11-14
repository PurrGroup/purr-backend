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

    void undoDeleteArticleById(Long articleId);

    void deleteAll();

    void addTag(Long articleID, Long tagID);

    /**
     * @return 公开的并且未被删除的文章总数
     */
    Long getTotalPublic();

    List<ArticleDTO> findRecentArticlePublic(Pageable pageable);

    /**
     * @return 所有未被删除的文章总数
     */
    Long getTotalExceptDeleted();

    List<ArticleDTO> findRecentArticleExceptDeleted(Pageable pageable);

    String getArticleUrlByID(Long postID);

    void setCommentStatus(Long id, Integer commentStatus);

    void setPinnedStatus(Long id, Integer pinnedStatus);

    void setRecommendStatus(Long id, Integer recommendStatus);

    List<ArticleDTO> findRecommendedArticle();

    ArticleDTO getArticleByID(Long postID);

    ArticleDTO getArticleByLinkName(String linkName);

    List<ArticleDTO> getArticlesByOneTag(Long tagId);

    List<ArticleDTO> getAllArticles();
}
