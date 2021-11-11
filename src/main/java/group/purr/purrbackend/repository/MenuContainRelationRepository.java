package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.MenuContainRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuContainRelationRepository extends JpaRepository<MenuContainRelation, String> {
    List<MenuContainRelation> findAllByMenuContainRelationKey_MenuID(Long menuId);
}
