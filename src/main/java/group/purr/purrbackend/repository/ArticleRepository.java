package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Article;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, String> {
    @Query(value = "select SUM(thumb_count) FROM article", nativeQuery = true)
    Long sumAllByThumb();

    @Query(value = "select SUM(view_count) FROM article", nativeQuery = true)
    Long sumByView();

//    @Query(value = "select view_count FROM article where (create_time between ?1 AND ?2) OR (update_time between ?1 AND ?2)", nativeQuery = true)
//    Long viewCountPerDay(String beginTime, String endTime);
}
