package group.purr.purrbackend.controller;


import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.dto.ArticleDTO;
import group.purr.purrbackend.dto.TagDTO;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.service.ArticleService;
import group.purr.purrbackend.service.MetaService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/article")
@Slf4j
public class ArticleController {

    final
    ArticleService articleService;

    final MetaService metaService;

    public ArticleController(ArticleService articleService, MetaService metaService) {
        this.articleService = articleService;
        this.metaService = metaService;
    }

    /**
     * TO DO
     * 在这里没有排除被删除的文章
     * 初步设想抽取出一张表DeletedArticle用于存放被删除的文章，Article只用于存放可展示的文章
     * 因此分页查询只需查询Article即可。
     */
    @GetMapping("/recent")
    public ResultVO getRecentArticle(@RequestParam(value = "page") Integer pageNum,
                                     @RequestParam(value = "num") Integer pageSize){

        Long total = articleService.getTotalExceptDeleted();
        int maxNum = Math.toIntExact(total / pageSize);
        if((pageNum-1)>maxNum){
            pageNum = maxNum;
        }
        else pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<ArticleDTO> recentArticles = articleService.findRecentArticle(pageable);

        return ResultVOUtil.success(recentArticles);

    }

    /**
     * 最多返回十篇文章（按照修改时间排序）
     * @return 被推荐的文章列表
     */
    @GetMapping("/recommend")
    public ResultVO getRecommendedArticle(){
        List<ArticleDTO> articles = articleService.findRecommendedArticle();

        return ResultVOUtil.success(articles.subList(0, Math.min(10, articles.size())));
    }

    @GetMapping("/details")
    public ResultVO getArticleDetail(@RequestParam(value = "id")Long id){
        return ResultVOUtil.success(articleService.getArticleByID(id));
    }

    @PostMapping("/validate")
    public ResultVO validate(@RequestBody JSONObject json){
        String name = json.getString("name");
        String linkName =  json.getString("linkName");
        String prefix = metaService.getArticleLinkNamePrefix();

        if(linkName.equals("")){
            linkName = prefix + name;
            log.info("name->linkName: " + linkName);
            if(linkName.getBytes(StandardCharsets.UTF_8).length > 255)
                return ResultVOUtil.error(ResultEnum.NAME_LENGTH_EXCEED);
            else if(articleService.getArticleByLinkName(linkName) != null)
                return ResultVOUtil.error(ResultEnum.NAME_REPEATED);
        }
        else{
            linkName = prefix + linkName;
            log.info("linkName->linkName: " + linkName);
            if(linkName.getBytes(StandardCharsets.UTF_8).length > 255)
                return ResultVOUtil.error(ResultEnum.LINK_NAME_LENGTH_EXCEED);
            else if(articleService.getArticleByLinkName(linkName) != null)
                return ResultVOUtil.error(ResultEnum.LINK_NAME_REPEATED);
        }

        return ResultVOUtil.success(true);
    }

    @PutMapping("/new")
    public ResultVO createArticle(@RequestBody JSONObject json){
        Long id = json.getLongValue("id");
        List<Integer> tags = json.getObject("tags", List.class);
        Long createTime = json.getLongValue("createTime");

        json.fluentRemove("tags");
        json.fluentRemove("createTime");

        ArticleDTO article = JSONObject.toJavaObject(json, ArticleDTO.class);

        if(article.getTarget().equals("0"))
            article.setTarget("_self");
        else
            article.setTarget("_blank");
        article.setCreateTime(new Date(createTime));

        if(id != -1) {
            articleService.deleteArticleTags(id);
        }

        Long articleId = articleService.createArticle(article);

        for (Integer tagId : tags) {
            articleService.addTag(articleId, tagId.longValue());
        }

        return ResultVOUtil.success(true);
    }

    @PutMapping("/draft")
    public ResultVO createDraft(@RequestBody JSONObject json){
        Long id = json.getLongValue("id");
        List<Integer> tags = json.getObject("tags", List.class);

        json.fluentRemove("tags");

        ArticleDTO article = JSONObject.toJavaObject(json, ArticleDTO.class);

        if(id == -1){ // 新草稿
            article.setStatus(0); //草稿
            article.setCommentStatus(0);
            article.setIsOriginal(1);
            article.setCommentStatus(1);
            article.setTarget("_self");

            Long articleId = articleService.createArticle(article);

            for (Integer tagId : tags){
                articleService.addTag(articleId, tagId.longValue());
            }
        }
        else{ // 对草稿or文章进行修改
            ArticleDTO originArticle = articleService.getArticleByID(id);
            originArticle.setName(article.getName());
            originArticle.setAuthor(article.getAuthor());
            originArticle.setContent(article.getContent());
            originArticle.setTags(null);

            articleService.deleteArticleTags(id);

            articleService.updateArticle(originArticle);
            log.info("finish update article");

            log.info("finish delete tags");
            for (Integer tagId : tags){
                articleService.addTag(id, tagId.longValue());
            }
        }

        return ResultVOUtil.success(true);
    }

    @GetMapping("/delete")
    public ResultVO deleteArticle(@RequestParam(value = "id") Long id){
        articleService.deleteArticleById(id);
        return ResultVOUtil.success(true);
    }
}
