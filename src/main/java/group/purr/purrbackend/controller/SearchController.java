package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.PageableSearch;
import group.purr.purrbackend.dto.SearchDTO;
import group.purr.purrbackend.exception.DenialOfServiceException;
import group.purr.purrbackend.service.SearchService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/search")
@Slf4j
public class SearchController {
    final private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/searchByKeyword")
    public ResultVO searchByKeyword(@RequestParam(value = "curPage") Integer pageNum,
                                    @RequestParam(value = "pageSize") Integer pageSize,
                                    @RequestParam(value = "keyword") String keyword){

        if(pageSize <= 0) throw new DenialOfServiceException();

        pageNum = Math.max(pageNum - 1, 0);

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        List<SearchDTO> searchDTOS = searchService.searchPublicByKeyword(keyword, pageable);

        Long total = searchService.getTotalPublic();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        PageableSearch result = new PageableSearch();
        result.setData(searchDTOS);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum + 1);

        return ResultVOUtil.success(result);
    }

    @GetMapping("/admin/searchByKeyword")
    public ResultVO searchAuthorized(@RequestParam(value = "curPage") Integer pageNum,
                                     @RequestParam(value = "pageSize") Integer pageSize,
                                     @RequestParam(value = "keyword") String keyword){

        if(pageSize <= 0) throw new DenialOfServiceException();

        pageNum = Math.max(pageNum - 1, 0);

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        List<SearchDTO> searchDTOS = searchService.searchNotDeletedByKeyword(keyword, pageable);

        Long total = searchService.getTotalNotDeleted();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        PageableSearch result = new PageableSearch();
        result.setData(searchDTOS);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum + 1);

        return ResultVOUtil.success(result);

    }
}
