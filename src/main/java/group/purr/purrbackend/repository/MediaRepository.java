package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, String> {
}
