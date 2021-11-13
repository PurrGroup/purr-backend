package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.LinkDTO;
import group.purr.purrbackend.dto.PageableLink;
import group.purr.purrbackend.exception.DenialOfServiceException;
import group.purr.purrbackend.service.LinkService;
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
@RequestMapping("/api/link")
@Slf4j
public class LinkController {

    final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/recent")
    public ResultVO getRecentList(@RequestParam(value = "curPage") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize) {

        if (pageSize <= 0) throw new DenialOfServiceException();

        Long total = linkService.getTotalCount();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if (pageNum > maxNum) {
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<LinkDTO> recentLinks = linkService.getRecentLinks(pageable);

        PageableLink result = new PageableLink();
        result.setData(recentLinks);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum + 1);

        return ResultVOUtil.success(result);
    }

    @GetMapping("/admin/recent")
    public ResultVO getRecentListAuthorized(@RequestParam(value = "curPage") Integer pageNum,
                                            @RequestParam(value = "pageSize") Integer pageSize) {

        if (pageSize <= 0) throw new DenialOfServiceException();

        Long total = linkService.getTotalCount();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if (pageNum > maxNum) {
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<LinkDTO> recentLinks = linkService.getRecentLinks(pageable);

        PageableLink result = new PageableLink();
        result.setData(recentLinks);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum + 1);

        return ResultVOUtil.success(result);
    }

    @GetMapping("/focus")
    public ResultVO getFocusLinks(){
        return ResultVOUtil.success(linkService.getFocus());
    }
}
