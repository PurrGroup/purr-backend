package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
    @Query(value = "select SUM(thumb_count) FROM article", nativeQuery = true)
    Long sumAllByThumb();

    @Query(value = "select SUM(view_count) FROM article", nativeQuery = true)
    Long sumByView();

    Long countByDeleteTimeIsNull();

    Article findByID(Long id);

    List<Article> findAllByIsRecommendedOrderByUpdateTimeDesc(Integer isRecommended);

    Article findByLinkName(String linkName);

    Long countByStatusAndDeleteTimeIsNull(Integer status);

    Page<Article> findAllByDeleteTimeIsNull(Pageable pageable);

    Page<Article> findAllByStatusAndDeleteTimeIsNull(Integer status, Pageable pageable);

}
