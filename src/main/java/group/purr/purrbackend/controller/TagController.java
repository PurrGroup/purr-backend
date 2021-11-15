package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.PageableTag;
import group.purr.purrbackend.dto.TagDTO;
import group.purr.purrbackend.exception.DenialOfServiceException;
import group.purr.purrbackend.service.TagService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@Slf4j
public class TagController {
    final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/admin/recent")
    public ResultVO getRecentTagAuthorized(@RequestParam(value = "curPage") Integer pageNum,
                                           @RequestParam(value = "pageSize") Integer pageSize) {

        if (pageSize <= 0) throw new DenialOfServiceException();

        Long total = tagService.getTotalCount();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if (pageNum > maxNum) {
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = org.springframework.data.domain.Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<TagDTO> tags = tagService.getRecentTags(pageable);

        PageableTag result = new PageableTag();
        result.setData(tags);
        result.setPageNum(maxNum);
        result.setCurrentPage(pageNum + 1);

        return ResultVOUtil.success(result);
    }

    @GetMapping("/searchByKeyword")
    public ResultVO getTagsByKeyword(@RequestParam(value = "keyword") String keyword) {

        List<TagDTO> tags = tagService.getTagsByKeyword(keyword);

        return ResultVOUtil.success(tags);
    }
}
