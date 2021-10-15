package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.MagicConstants;
import group.purr.purrbackend.dto.CommentDTO;
import group.purr.purrbackend.entity.Comment;
import group.purr.purrbackend.repository.ArticleRepository;
import group.purr.purrbackend.repository.CommentRepository;
import group.purr.purrbackend.repository.PageRepository;
import group.purr.purrbackend.service.ArticleService;
import group.purr.purrbackend.service.CommentService;
import group.purr.purrbackend.service.PageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    final
    CommentRepository commentRepository;

    final
    ArticleRepository articleRepository;

    final
    PageRepository pageRepository;

    final
    ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, ArticleRepository articleRepository, PageRepository pageRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.articleRepository = articleRepository;
        this.pageRepository = pageRepository;
    }

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
            commentDTO.setPostUrl(getPostUrl(comment.getPostID(), comment.getPostCategory()));
            result.add(commentDTO);
        }

        return result;
    }

    @Override
    public Long getUnapproved() {
        return commentRepository.countByApproved(0);
    }


    private String getPostUrl(Long postID, Integer postCategory){
        String postUrl;

        if(postCategory==0){
            postUrl = articleRepository.findByID(postID).getLinkName();
        }
        else if(postCategory==1){
            postUrl = pageRepository.findByID(postID).getUrlName();
        }
        else if(postCategory==2){
            postUrl = MagicConstants.DEFAULT_MENUITEM_COMMENT_URL;
        }
        else{
            return "";
        }

        return postUrl;

    }
}
