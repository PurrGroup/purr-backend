package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
    Long countByDeleteTimeNotNull();
}
