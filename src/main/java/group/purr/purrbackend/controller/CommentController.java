package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.CommentDTO;
import group.purr.purrbackend.service.CommentService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {

    final
    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/recent")
    public ResultVO getRecentComment(@RequestParam(value = "curPage") Integer pageNum,
                                     @RequestParam(value = "pageSize") Integer pageSize){

        Long total = commentService.getTotal();
        int maxNum = Math.toIntExact(total / pageSize);
        if((pageNum-1)>maxNum){
            pageNum = maxNum;
        }
        else pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<CommentDTO> recentComments = commentService.findRecentComment(pageable);

        return ResultVOUtil.success(recentComments);

    }

    @GetMapping("/unapproved")
    public ResultVO getUnapprovedCommentNumber(){
        Long unapproved = commentService.getUnapproved();
        return ResultVOUtil.success(unapproved);
    }
}
