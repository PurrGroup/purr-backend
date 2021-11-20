package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, String> {
    List<Link> findAllByCategoryOrderByUpdateTimeDesc(String category);
}
