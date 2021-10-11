package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
    Long countByApproved(Integer approved);
}
