package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PageRepository extends JpaRepository<Page, String> {
    @Query(value = "select SUM(thumb_count) FROM page", nativeQuery = true)
    Long sumByThumb();

    @Query(value = "select SUM(view_count) FROM page", nativeQuery = true)
    Long sumByView();

    Page findByID(Long id);
}
