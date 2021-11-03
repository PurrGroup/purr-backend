package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.ArticleTagKey;
import group.purr.purrbackend.entity.ArticleTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTagRelation, String> {
    List<ArticleTagRelation> findAllByArticleTagKey_ArticleID(Long id);
    void deleteByArticleTagKey(ArticleTagKey key);
}
