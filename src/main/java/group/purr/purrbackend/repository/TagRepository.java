package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {
    Tag findByID(Long id);

    Long countByDeleteTimeIsNull();

    Page<Tag> findAllByDeleteTimeIsNull(Pageable pageable);

    List<Tag> findAllByNameLikeAndDeleteTimeIsNull(String name);
}
