package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
    @Query(value = "select SUM(thumb_count) FROM article", nativeQuery = true)
    Long sumAllByThumb();

    @Query(value = "select SUM(view_count) FROM article", nativeQuery = true)
    Long sumByView();

    Long countByDeleteTimeNotNull();

}
