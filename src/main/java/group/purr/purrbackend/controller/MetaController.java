package group.purr.purrbackend.controller;

import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.dto.AuthorDTO;
import group.purr.purrbackend.dto.BlogMetaDTO;
import group.purr.purrbackend.exception.IllegalCategoryException;
import group.purr.purrbackend.service.ArticleService;
import group.purr.purrbackend.service.AuthorService;
import group.purr.purrbackend.service.MetaService;
import group.purr.purrbackend.service.PageService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meta")
@Slf4j
public class MetaController {

    final
    MetaService metaService;

    final ArticleService articleService;

    final PageService pageService;

    final
    AuthorService authorService;

    public MetaController(MetaService metaService, ArticleService articleService, PageService pageService, AuthorService authorService) {
        this.metaService = metaService;
        this.articleService = articleService;
        this.pageService = pageService;
        this.authorService = authorService;
    }

    /**
     * 判断系统是否已安装
     *
     * @return 返回给前端一个对象，data字段表示是否已安装
     */
    @GetMapping("/isInstalled")
    public ResultVO isInstalled() {
        Boolean isInstalled = metaService.queryInstalled();
        return ResultVOUtil.success(isInstalled);
    }

    @PutMapping("/apiUrl")
    public ResultVO updateApiUrl(@RequestBody JSONObject jsonObject) {
        String apiUrl = jsonObject.getString("apiUrl");
        metaService.updateApiUrl(apiUrl);
        return ResultVOUtil.success(true);
    }

    @GetMapping("/profile")
    public ResultVO getProfile() {
        AuthorDTO authorDTO = authorService.getProfile();
        BlogMetaDTO blogMetaDTO = metaService.getProfile();

        authorDTO.setBlogTitle(blogMetaDTO.getBlogTitle());
        authorDTO.setFavicon(blogMetaDTO.getFavicon());
        return ResultVOUtil.success(authorDTO);
    }

    @PutMapping("/comment")
    public ResultVO setCommentStatus(@RequestBody JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String category = jsonObject.getString("category");
        String commentStatus = jsonObject.getString("commentStatus");

        //article
        if (category.equals("0")) {
            articleService.setCommentStatus(Long.valueOf(id), Integer.valueOf(commentStatus));
            return ResultVOUtil.success(true);
        }
        //page
        if (category.equals("1")) {
            pageService.setCommentStatus(Long.valueOf(id), Integer.valueOf(commentStatus));
            return ResultVOUtil.success(true);
        }

        throw new IllegalCategoryException();

    }

    @PutMapping("/pinned")
    public ResultVO setPinnedStatus(@RequestBody JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String category = jsonObject.getString("category");
        String pinnedStatus = jsonObject.getString("pinnedStatus");

        //article
        if (category.equals("0")) {
            articleService.setPinnedStatus(Long.valueOf(id), Integer.valueOf(pinnedStatus));
            return ResultVOUtil.success(true);
        }

        throw new IllegalCategoryException();
    }

    @PutMapping("/recommend")
    public ResultVO setRecommend(@RequestBody JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String category = jsonObject.getString("category");
        String recommendStatus = jsonObject.getString("recommendStatus");

        //article
        if (category.equals("0")) {
            articleService.setRecommendStatus(Long.valueOf(id), Integer.valueOf(recommendStatus));
            return ResultVOUtil.success(true);
        }

        throw new IllegalCategoryException();
    }

}
