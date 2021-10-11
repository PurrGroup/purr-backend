package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.ArticleDTO;
import group.purr.purrbackend.entity.Article;
import group.purr.purrbackend.entity.ArticleTagKey;
import group.purr.purrbackend.entity.ArticleTagRelation;
import group.purr.purrbackend.entity.Content;
import group.purr.purrbackend.repository.ArticleRepository;
import group.purr.purrbackend.repository.ArticleTagRepository;
import group.purr.purrbackend.repository.ContentRepository;
import group.purr.purrbackend.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    final
    ArticleRepository articleRepository;

    final
    ContentRepository contentRepository;

    final
    ArticleTagRepository articleTagRepository;

    final
    ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ContentRepository contentRepository, ArticleTagRepository articleTagRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.contentRepository = contentRepository;
        this.articleTagRepository = articleTagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createArticle(ArticleDTO articleDTO) {

        Article article = modelMapper.map(articleDTO, Article.class);
        Date currentTime = new Date();
        article.setCreateTime(currentTime);
        article.setUpdateTime(currentTime);
        Article result = articleRepository.saveAndFlush(article);

        Content content = new Content();
        content.setID(result.getID());
        content.setContent(articleDTO.getContent());
        content.setCreateTime(currentTime);
        content.setUpdateTime(currentTime);
        contentRepository.save(content);

        return result.getID();
    }

    @Override
    public void deleteAll() {
        articleRepository.deleteAll();
        contentRepository.deleteAll();
        articleTagRepository.deleteAll();
    }

    @Override
    public void addTag(Long articleID, Long tagID) {
        ArticleTagRelation articleTagRelation = new ArticleTagRelation();
        ArticleTagKey articleTagKey = new ArticleTagKey();
        articleTagKey.setArticleID(articleID);
        articleTagKey.setTagID(tagID);
        articleTagRelation.setArticleTagKey(articleTagKey);
        articleTagRepository.save(articleTagRelation);
    }


    @Override
    public List<ArticleDTO> findRecentArticle(Pageable pageable) {

        Page<Article> articles = articleRepository.findAll(pageable);

        List<ArticleDTO> result = new ArrayList<>();
        for (Article article : articles.getContent()) {
            article.setCommentStatus(null);
            article.setPingStatus(null);
            article.setToPing(null);
            article.setPinged(null);
            article.setArticleAbstract(null);
            article.setDeleteTime(null);
            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
            result.add(articleDTO);
        }

        return result;
    }




    @Override
    public Long getTotal() {
        return articleRepository.count();
    }

    @Override
    public Long getTotalExceptDeleted() {
        return articleRepository.countByDeleteTimeNotNull();
    }
}
