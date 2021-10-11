package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.LoginFailed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginFailedRepository extends JpaRepository<LoginFailed, String> {
    Integer countByLoginAttemptDateBetween(String beginDate, String endDate);
}
