package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.MenuItemContainRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemContainRelationRepository extends JpaRepository<MenuItemContainRelation, String> {
}
