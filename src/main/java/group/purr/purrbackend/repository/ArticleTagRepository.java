package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.ArticleTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTagRelation, String> {
}
