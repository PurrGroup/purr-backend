package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.PageDTO;
import group.purr.purrbackend.dto.PageablePage;
import group.purr.purrbackend.exception.DenialOfServiceException;
import group.purr.purrbackend.service.PageService;
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
@RequestMapping("/api/page")
@Slf4j
public class PageController {
    final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/admin/recent")
    public ResultVO getRecentPages(@RequestParam(value = "curPage") Integer pageNum,
                                   @RequestParam(value = "pageSize") Integer pageSize){
        if(pageSize <= 0) throw new DenialOfServiceException();

        Long total = pageService.getTotal();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if(pageNum > maxNum){
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<PageDTO> recentPages = pageService.getRecentPages(pageable);

        PageablePage result = new PageablePage();
        result.setData(recentPages);
        result.setPageNum(maxNum);
        result.setCurrentPage(pageNum + 1);
        result.setPageSize(pageSize);

        return ResultVOUtil.success(result);
    }

}
