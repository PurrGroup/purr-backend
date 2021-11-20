package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.ArticleDTO;
import group.purr.purrbackend.dto.RelatedArticleDTO;
import group.purr.purrbackend.dto.TagDTO;
import group.purr.purrbackend.entity.*;
import group.purr.purrbackend.repository.ArticleRepository;
import group.purr.purrbackend.repository.ArticleTagRepository;
import group.purr.purrbackend.repository.ContentRepository;
import group.purr.purrbackend.repository.TagRepository;
import group.purr.purrbackend.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    final
    ArticleRepository articleRepository;

    final TagRepository tagRepository;

    final
    ContentRepository contentRepository;

    final
    ArticleTagRepository articleTagRepository;

    final
    ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, TagRepository tagRepository, ContentRepository contentRepository, ArticleTagRepository articleTagRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.contentRepository = contentRepository;
        this.articleTagRepository = articleTagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long createArticle(ArticleDTO articleDTO) {
        log.info("begin create article");
        log.info("article: " + articleDTO.toString());
        Date date = articleDTO.getCreateTime();

        Article article = modelMapper.map(articleDTO, Article.class);
        Date currentTime = new Date();
        if (date == null)
            article.setCreateTime(currentTime);
        article.setUpdateTime(currentTime);
        if(article.getID() == null) {
            article.setViewCount(0);
            article.setThumbCount(0);
            article.setShareCount(0);
            article.setCommentCount(0);
        }
        else{
            Article origin = articleRepository.findByID(article.getID());
            article.setViewCount(origin.getViewCount());
            article.setThumbCount(origin.getThumbCount());
            article.setShareCount(origin.getShareCount());
            article.setCommentCount(origin.getCommentCount());
        }
        Article result = articleRepository.saveAndFlush(article);

        Content content = new Content();
        content.setID(result.getID());
        content.setContent(articleDTO.getContent());
        content.setHtml(articleDTO.getHtml());
        if (date == null)
            content.setCreateTime(currentTime);
        else
            content.setCreateTime(date);
        content.setUpdateTime(currentTime);
        contentRepository.save(content);

        return result.getID();
    }

    @Override
    public void updateArticle(ArticleDTO articleDTO) {
        Article article = modelMapper.map(articleDTO, Article.class);
        Date currentTime = new Date();
        article.setUpdateTime(currentTime);
        log.info(article.toString());
        articleRepository.save(article);

        log.info("before save content");
        Content content = contentRepository.findByID(article.getID());
        content.setContent(articleDTO.getContent());
        content.setHtml(articleDTO.getHtml());
        content.setUpdateTime(currentTime);
        contentRepository.save(content);
    }

    @Modifying
    @Transactional
    @Override
    public void deleteArticleTags(Long articleId) {
        log.info("delete article tags begin");
        List<ArticleTagRelation> relations = articleTagRepository.findAllByArticleTagKey_ArticleID(articleId);
        log.info("finish find article tag");
        for (ArticleTagRelation relation : relations) {
            log.info("tag id: " + relation.getArticleTagKey().getTagID().toString());
            log.info(tagRepository.findByID(relation.getArticleTagKey().getTagID()).toString());
            log.info("begin delete");
            ArticleTagKey key = relation.getArticleTagKey();
            articleTagRepository.deleteByArticleTagKey(key);
        }
        log.info("finish delete tag relation");
    }

    @Override
    public void deleteArticleById(Long articleId) {
        Article article = articleRepository.findByID(articleId);
        Date date = new Date();
        article.setDeleteTime(date);
        articleRepository.save(article);
    }

    @Override
    public void undoDeleteArticleById(Long articleId) {
        Article article = articleRepository.findByID(articleId);
        article.setDeleteTime(null);
        articleRepository.save(article);
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

    private List<TagDTO> findTagsByArticle(Long id) {
        List<ArticleTagRelation> relations = articleTagRepository.findAllByArticleTagKey_ArticleID(id);
        List<TagDTO> tags = new ArrayList<>();

        for (ArticleTagRelation relation : relations) {
            Tag tag = tagRepository.findByID(relation.getArticleTagKey().getTagID());
            TagDTO tagDTO = modelMapper.map(tag, TagDTO.class);
            tagDTO.setCreateTime(null);
            tagDTO.setUpdateTime(null);
            tagDTO.setDeleteTime(null);
            tagDTO.setBackgroundUrl(null);
            tagDTO.setCiteCount(null);
            tagDTO.setDescription(null);
            tagDTO.setLinkRel(null);
            tagDTO.setLinkRss(null);
            tagDTO.setVisitCount(null);
            tags.add(tagDTO);
        }

        return tags;
    }


    @Override
    public List<ArticleDTO> findRecentArticlePublic(Pageable pageable) {

//        Page<Article> articles = articleRepository.findAll(pageable);
        // 公开发表的并且没有被删除的文章
        Page<Article> articles = articleRepository.findAllByStatusAndDeleteTimeIsNull(1, pageable);

        List<ArticleDTO> result = new ArrayList<>();
        for (Article article : articles.getContent()) {
            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
            articleDTO.setCommentStatus(null);
            articleDTO.setPingStatus(null);
            articleDTO.setPinged(null);
            articleDTO.setToPing(null);
            List<TagDTO> tagDTOS = findTagsByArticle(article.getID());
            articleDTO.setTags(tagDTOS);
            result.add(articleDTO);
        }

        return result;
    }


    @Override
    public Long getTotalPublic() {
        return articleRepository.countByStatusAndDeleteTimeIsNull(1);
    }

    @Override
    public Long getTotalExceptDeleted() {
        return articleRepository.countByDeleteTimeIsNull();
    }

    @Override
    public List<ArticleDTO> findRecentArticleExceptDeleted(Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByDeleteTimeIsNull(pageable);

        List<ArticleDTO> result = new ArrayList<>();
        for (Article article : articles.getContent()) {
            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
            articleDTO.setCommentStatus(null);
            articleDTO.setPingStatus(null);
            articleDTO.setPinged(null);
            articleDTO.setToPing(null);
            List<TagDTO> tagDTOS = findTagsByArticle(article.getID());
            articleDTO.setTags(tagDTOS);
            result.add(articleDTO);
        }

        return result;
    }

    @Override
    public String getArticleUrlByID(Long postID) {
        Article article = articleRepository.findByID(postID);
        return article.getLinkName();
    }

    @Override
    public List<ArticleDTO> findRecommendedArticle() {
        List<Article> articles = articleRepository.findAllByIsRecommendedOrderByUpdateTimeDesc(1);
        List<ArticleDTO> result = new ArrayList<>();

        for (Article article : articles)
            if (article.getDeleteTime() == null) {
                ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);
                dto.setArticleAbstract(null);
                dto.setCreateTime(null);
                dto.setUpdateTime(null);
                dto.setCommentCount(null);
                dto.setShareCount(null);
                dto.setThumbCount(null);
                dto.setViewCount(null);
                List<TagDTO> tagDTOS = findTagsByArticle(article.getID());
                dto.setTags(tagDTOS);
                result.add(dto);
            }

        return result;
    }

    @Override
    public ArticleDTO getArticleByID(Long postID) {
        Article article = articleRepository.findByID(postID);
        ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
        List<TagDTO> tags = findTagsByArticle(postID);
        Content content = contentRepository.findByID(postID);

        articleDTO.setTags(tags);
        articleDTO.setContent(content.getContent());
        articleDTO.setHtml(content.getHtml());

        return articleDTO;
    }

    @Override
    public Optional<ArticleDTO> getArticleDetailByLinkName(String linkName) {
        Article article = articleRepository.findByLinkName(linkName);
        // 不存在或者已被删除
        if (article == null || article.getDeleteTime() != null) return Optional.empty();
        ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);

        List<TagDTO> tags = findTagsByArticle(article.getID());
        Content content = contentRepository.findByID(article.getID());
        articleDTO.setTags(tags);
        if (content != null) {
            articleDTO.setContent(content.getContent());
            articleDTO.setHtml(content.getHtml());
        } else {
            articleDTO.setContent("");
            articleDTO.setHtml("");
        }

        return Optional.of(articleDTO);
    }

    @Override
    @NonNull
    public Optional<RelatedArticleDTO> getPreviousAndNextArticles(String linkName) {
        Article article = articleRepository.findByLinkName(linkName);
        if (article == null || article.getDeleteTime() != null) return Optional.empty();

        Optional<RelatedArticleDTO> rel = Optional.of(new RelatedArticleDTO());
        Date opTime = article.getUpdateTime() != null ? article.getUpdateTime() : article.getCreateTime();
        Optional<Article> prevArticle = articleRepository.findTopByUpdateTimeLessThanOrderByUpdateTimeDesc(opTime);
        Optional<Article> nextArticle = articleRepository.findTopByUpdateTimeGreaterThanOrderByUpdateTimeAsc(opTime);

        prevArticle.ifPresent((prevArticleModel) -> rel.get().setPrev(modelMapper.map(prevArticleModel, ArticleDTO.class)));
        nextArticle.ifPresent((nextArticleModel) -> rel.get().setNext(modelMapper.map(nextArticleModel, ArticleDTO.class)));
        return rel;
    }

    @Override
    public ArticleDTO getArticleByLinkName(String linkName) {
        Article article = articleRepository.findByLinkName(linkName);

        if (article == null) return null;

        return modelMapper.map(article, ArticleDTO.class);
    }

    @Override
    public List<ArticleDTO> getArticlesByOneTag(Long tagId) {
        List<ArticleTagRelation> relations = articleTagRepository.findAllByArticleTagKey_TagID(tagId);
        List<ArticleDTO> articles = new ArrayList<>();

        for (ArticleTagRelation relation : relations) {
            Article article = articleRepository.findByID(relation.getArticleTagKey().getArticleID());
            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);

            articleDTO.setCommentStatus(null);
            articleDTO.setPingStatus(null);
            articleDTO.setPinged(null);
            articleDTO.setToPing(null);

            List<TagDTO> tagDTOS = findTagsByArticle(article.getID());
            articleDTO.setTags(tagDTOS);

            articles.add(articleDTO);
        }

        return articles;
    }

    @Override
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> result = new ArrayList<>();

        for (Article article : articles) {
            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);

            articleDTO.setCommentStatus(null);
            articleDTO.setPingStatus(null);
            articleDTO.setPinged(null);
            articleDTO.setToPing(null);

            List<TagDTO> tagDTOS = findTagsByArticle(article.getID());
            articleDTO.setTags(tagDTOS);

            result.add(articleDTO);
        }

        return result;
    }

    @Override
    public void setCommentStatus(Long id, Integer commentStatus) {
        Article article = articleRepository.findByID(id);
        article.setCommentStatus(commentStatus);

        articleRepository.save(article);
    }

    @Override
    public void setPinnedStatus(Long id, Integer pinnedStatus) {
        Article article = articleRepository.findByID(id);
        article.setIsPinned(pinnedStatus);

        articleRepository.save(article);
    }

    @Override
    public void setRecommendStatus(Long id, Integer recommendStatus) {
        Article article = articleRepository.findByID(id);
        article.setIsRecommended(recommendStatus);

        articleRepository.save(article);
    }
}
