package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.CommentDTO;
import group.purr.purrbackend.entity.Comment;
import group.purr.purrbackend.repository.CommentRepository;
import group.purr.purrbackend.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Boolean createComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Date currentTime = new Date();
        comment.setDate(currentTime);
        commentRepository.save(comment);
        return true;
    }
}
