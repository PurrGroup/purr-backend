package group.purr.purrbackend.controller;


import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.dto.ArticleDTO;
import group.purr.purrbackend.service.ArticleService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/article")
@Slf4j
public class ArticleController {

    final
    ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/recent")
    public ResultVO getRecentArticle(@RequestParam(value = "page") Integer pageNum,
                                     @RequestParam(value = "num") Integer pageSize){

        Long total = articleService.getTotalExceptDeleted();
        int maxNum = Math.toIntExact(total / pageSize);
        if((pageNum-1)>maxNum){
            pageNum = maxNum;
        }
        else if(pageNum-1<0){
            pageNum = 0;
        }
        else{
            pageNum = pageNum - 1;
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<ArticleDTO> recentArticles = articleService.findRecentArticle(pageable);

        return ResultVOUtil.success(recentArticles);

    }
}
