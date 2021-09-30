package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.CommentDTO;

public interface CommentService {
    /**
     * 创建新评论
     * @param commentDTO
     * @return 是否创建成功
     */
    Boolean createComment(CommentDTO commentDTO);
}
