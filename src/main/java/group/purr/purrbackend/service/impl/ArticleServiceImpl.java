package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.ArticleDTO;
import group.purr.purrbackend.entity.Article;
import group.purr.purrbackend.entity.Content;
import group.purr.purrbackend.repository.ContentRepository;
import group.purr.purrbackend.repository.ArticleRepository;
import group.purr.purrbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ContentRepository contentRepository;

    @Override
    public Long createArticle(ArticleDTO articleDTO) {

        Article article = articleDTO.convertTo();
        Date currentTime = new Date();
        article.setCreateTime(currentTime);
        Article result = articleRepository.saveAndFlush(article);


        Content content = new Content();
        content.setID(result.getID());
        content.setContent(articleDTO.content);
        content.setCreateTime(currentTime);
        contentRepository.save(content);

        return result.getID();
    }
}
