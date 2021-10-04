package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, String> {
    @Query(value = "select * FROM visit where visit_date between ?1 AND ?2", nativeQuery = true)
    List<Visit> selectByTime(String beginTime, String endTime);
}
