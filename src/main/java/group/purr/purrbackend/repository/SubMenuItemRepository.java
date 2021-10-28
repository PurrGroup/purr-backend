package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.SubMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubMenuItemRepository extends JpaRepository<SubMenuItem, String> {
    SubMenuItem findByID(Long id);
}
