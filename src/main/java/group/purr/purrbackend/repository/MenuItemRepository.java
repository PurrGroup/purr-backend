package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, String> {

}
