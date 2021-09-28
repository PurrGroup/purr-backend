package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.CommentDTO;
import group.purr.purrbackend.entity.Comment;
import group.purr.purrbackend.repository.CommentRepository;
import group.purr.purrbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public boolean createComment(CommentDTO commentDTO) {
        Comment comment = commentDTO.convertTo();
        commentRepository.save(comment);

        return true;
    }
}
