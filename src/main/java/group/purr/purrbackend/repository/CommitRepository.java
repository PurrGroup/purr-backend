package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommitRepository extends JpaRepository<Commit, String> {
    @Query(value = "select * FROM commit where commit_date between ?1 AND ?2", nativeQuery = true)
    List<Commit> selectByTime(String beginTime, String endTime);
}
