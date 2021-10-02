package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.ArticleDTO;

public interface ArticleService {
    /**
     * 创建新文章接口
     *
     * @param articleDTO
     * @return 返回文章ID
     */
    Long createArticle(ArticleDTO articleDTO);

    void deleteAll();

    void addTag(Long articleID, Long tagID);
}
