package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.LockdownIP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockdownIPRepository extends JpaRepository<LockdownIP, String> {
    LockdownIP findByLockdownIP(String lockdownIP);
}
