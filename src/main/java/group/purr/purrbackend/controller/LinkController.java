package group.purr.purrbackend.controller;

import group.purr.purrbackend.service.LinkService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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
    public ResultVO getRecentList(@RequestParam(value = "pageNum")Integer pageNum,
                                  @RequestParam(value = "pageSize")Integer pageSize){
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        return ResultVOUtil.success(linkService.getRecentLinks(pageable));
    }
}
