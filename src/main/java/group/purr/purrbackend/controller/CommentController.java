package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.CommentDTO;
import group.purr.purrbackend.dto.PageableComment;
import group.purr.purrbackend.exception.DenialOfServiceException;
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

        if(pageSize <= 0) throw new DenialOfServiceException();

        Long total = commentService.getTotal();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if(pageNum > maxNum){
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<CommentDTO> recentComments = commentService.findRecentComment(pageable);

        PageableComment result = new PageableComment();
        result.setData(recentComments);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum + 1);

        return ResultVOUtil.success(result);

    }

    @GetMapping("/admin/recent")
    public ResultVO getRecentCommentAuthorized(@RequestParam(value = "curPage") Integer pageNum,
                                               @RequestParam(value = "pageSize") Integer pageSize){

        if(pageSize <= 0) throw new DenialOfServiceException();

        Long total = commentService.getTotal();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if(pageNum > maxNum){
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<CommentDTO> recentComments = commentService.findRecentComment(pageable);

        PageableComment result = new PageableComment();
        result.setData(recentComments);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum + 1);

        return ResultVOUtil.success(result);
    }

    @GetMapping("/unapproved")
    public ResultVO getUnapprovedCommentNumber(){
        Long unapproved = commentService.getUnapproved();
        return ResultVOUtil.success(unapproved);
    }
}
