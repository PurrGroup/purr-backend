package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.CommentDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    /**
     * 创建新评论
     *
     * @param commentDTO
     * @return 是否创建成功
     */
    Boolean createComment(CommentDTO commentDTO);

    void deleteAll();

    Long getTotal();

    List<CommentDTO> findRecentComment(Pageable pageable);

    Long getUnapproved();
}
