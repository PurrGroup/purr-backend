package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
    Tag findByID(Long id);
}
