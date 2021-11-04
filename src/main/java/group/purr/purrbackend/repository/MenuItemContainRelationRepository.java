package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.MenuItemContainRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemContainRelationRepository extends JpaRepository<MenuItemContainRelation, String> {
    List<MenuItemContainRelation> findAllByMenuItemContainRelationKey_MenuItemID(Long menuItemId);
}
