package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.SearchDTO;
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

    @GetMapping("/init")
    public ResultVO saveRecord() {
//        Blog defaultBlog1 = new Blog();
//        defaultBlog1.setBlogId("1");
//        defaultBlog1.setBlogTitle("spring boot + elasticsearch");
//        defaultBlog1.setContent("Spring Data Elasticsearch是Spring Data项目的子项目，提供了Elasticsearch与Spring的集成。实现了Spring Data Repository风格的Elasticsearch文档交互风格，让你轻松进行Elasticsearch客户端开发。");
//
//        Blog defaultBlog2 = new Blog();
//        defaultBlog2.setBlogId("2");
//        defaultBlog2.setBlogTitle("spring boot + spring security");
//        defaultBlog2.setContent("Spring Security致力于为Java应用提供认证和授权管理。它是一个强大的，高度自定义的认证和访问控制框架");
//
//        Blog defaultBlog3 = new Blog();
//        defaultBlog2.setBlogId("3");
//        defaultBlog2.setBlogTitle("a record");
//        defaultBlog2.setContent("test");
//
//        esService.save(defaultBlog1);
//        esService.save(defaultBlog2);
//        esService.save(defaultBlog3);

        return ResultVOUtil.success(true);
    }

    @GetMapping("/searchByKeyword")
    public ResultVO searchByKeyword(@RequestParam(value = "curPage") Integer pageNum,
                                    @RequestParam(value = "pageSize") Integer pageSize,
                                    @RequestParam(value = "keyword") String keyword) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        List<SearchDTO> searchDTOS = searchService.searchByKeyword(keyword, pageable);

        return ResultVOUtil.success(searchDTOS);
    }
}
