package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MomentRepository extends JpaRepository<Moment, String> {
    @Query(value = "select SUM(thumb_count) FROM moment", nativeQuery = true)
    Long sumByThumb();
}
