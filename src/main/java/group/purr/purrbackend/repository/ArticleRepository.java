package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Article;
import group.purr.purrbackend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, String> {
}
