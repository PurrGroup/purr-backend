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
import org.springframework.stereotype.Service;

import java.util.Date;

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
        Article result = articleRepository.saveAndFlush(article);

        Content content = new Content();
        content.setID(result.getID());
        content.setContent(articleDTO.getContent());
        content.setCreateTime(currentTime);
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
}
