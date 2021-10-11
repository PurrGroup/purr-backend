package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.ArticleDTO;
import group.purr.purrbackend.dto.CommentDTO;
import group.purr.purrbackend.entity.Article;
import group.purr.purrbackend.entity.Comment;
import group.purr.purrbackend.repository.CommentRepository;
import group.purr.purrbackend.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }

    @Override
    public Long getTotal() {
        return commentRepository.count();
    }

    @Override
    public List<CommentDTO> findRecentComment(Pageable pageable) {

        Page<Comment> comments = commentRepository.findAll(pageable);

        List<CommentDTO> result = new ArrayList<>();
        for (Comment comment : comments.getContent()) {
            CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
            result.add(commentDTO);
        }

        return result;
    }

    @Override
    public Long getUnapproved() {
        return commentRepository.countByApproved(0);
    }
}
