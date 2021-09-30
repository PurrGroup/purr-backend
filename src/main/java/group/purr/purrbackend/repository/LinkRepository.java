package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, String> {
}
