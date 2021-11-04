package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, String> {
    Content findByID(Long id);
}
