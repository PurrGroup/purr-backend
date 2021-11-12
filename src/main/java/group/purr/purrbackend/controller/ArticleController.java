package group.purr.purrbackend.controller;


import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.dto.ArticleDTO;
import group.purr.purrbackend.dto.PageableArticle;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.DenialOfServiceException;
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
import java.util.*;

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
    @GetMapping("/admin/recent")
    public ResultVO getRecentArticleExceptDeleted(@RequestParam(value = "curPage") Integer pageNum,
                                                  @RequestParam(value = "pageSize") Integer pageSize) {


        if (pageSize <= 0) throw new DenialOfServiceException();

        Long total = articleService.getTotalExceptDeleted();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if (pageNum > maxNum) {
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<ArticleDTO> recentArticles = articleService.findRecentArticleExceptDeleted(pageable);

        PageableArticle result = new PageableArticle();
        result.setData(recentArticles);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum + 1);
        ;

        return ResultVOUtil.success(result);

    }

    @GetMapping("/recent")
    public ResultVO getRecentArticlePublic(@RequestParam(value = "curPage") Integer pageNum,
                                           @RequestParam(value = "pageSize") Integer pageSize) {

        if (pageSize <= 0) throw new DenialOfServiceException();

        Long total = articleService.getTotalPublic();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if (pageNum > maxNum) {
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<ArticleDTO> recentArticles = articleService.findRecentArticlePublic(pageable);

        PageableArticle result = new PageableArticle();
        result.setData(recentArticles);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum + 1);

        return ResultVOUtil.success(result);
    }

    /**
     * 最多返回十篇文章（按照修改时间排序）
     *
     * @return 被推荐的文章列表
     */
    @GetMapping("/recommend")
    public ResultVO getRecommendedArticle() {
        List<ArticleDTO> articles = articleService.findRecommendedArticle();

        return ResultVOUtil.success(articles.subList(0, Math.min(10, articles.size())));
    }

    @GetMapping("/details")
    public ResultVO getArticleDetail(@RequestParam(value = "id") Long id) {
        return ResultVOUtil.success(articleService.getArticleByID(id));
    }

    @PostMapping("/validate")
    public ResultVO validate(@RequestBody JSONObject json) {
        String name = json.getString("name");
        String linkName = json.getString("linkName");
        String prefix = metaService.getArticleLinkNamePrefix();

        if (linkName.equals("")) {
            linkName = prefix + name;
            log.info("name->linkName: " + linkName);
            if (linkName.getBytes(StandardCharsets.UTF_8).length > 255)
                return ResultVOUtil.error(ResultEnum.NAME_LENGTH_EXCEED);
            else if (articleService.getArticleByLinkName(linkName) != null)
                return ResultVOUtil.error(ResultEnum.NAME_REPEATED);
        } else {
            linkName = prefix + linkName;
            log.info("linkName->linkName: " + linkName);
            if (linkName.getBytes(StandardCharsets.UTF_8).length > 255)
                return ResultVOUtil.error(ResultEnum.LINK_NAME_LENGTH_EXCEED);
            else if (articleService.getArticleByLinkName(linkName) != null)
                return ResultVOUtil.error(ResultEnum.LINK_NAME_REPEATED);
        }

        return ResultVOUtil.success(true);
    }

    @PutMapping("/new")
    public ResultVO createArticle(@RequestBody JSONObject json) {
        Long id = json.getLongValue("id");
        List<Integer> tags = json.getObject("tags", List.class);
        Long createTime = json.getLongValue("createTime");

        json.fluentRemove("tags");
        json.fluentRemove("createTime");

        ArticleDTO article = JSONObject.toJavaObject(json, ArticleDTO.class);

        if (article.getTarget().equals("0"))
            article.setTarget("_self");
        else
            article.setTarget("_blank");
        article.setCreateTime(new Date(createTime));

        if (id != -1) {
            articleService.deleteArticleTags(id);
        }

        Long articleId = articleService.createArticle(article);

        for (Integer tagId : tags) {
            articleService.addTag(articleId, tagId.longValue());
        }

        return ResultVOUtil.success(true);
    }

    @PutMapping("/draft")
    public ResultVO createDraft(@RequestBody JSONObject json) {
        Long id = json.getLongValue("id");
        List<Integer> tags = json.getObject("tags", List.class);

        json.fluentRemove("tags");

        ArticleDTO article = JSONObject.toJavaObject(json, ArticleDTO.class);

        if (id == -1) { // 新草稿
            article.setStatus(0); //草稿
            article.setCommentStatus(0);
            article.setIsOriginal(1);
            article.setCommentStatus(1);
            article.setTarget("_self");

            Long articleId = articleService.createArticle(article);

            for (Integer tagId : tags) {
                articleService.addTag(articleId, tagId.longValue());
            }
        } else { // 对草稿or文章进行修改
            ArticleDTO originArticle = articleService.getArticleByID(id);
            originArticle.setName(article.getName());
            originArticle.setAuthor(article.getAuthor());
            originArticle.setContent(article.getContent());
            originArticle.setTags(null);
            originArticle.setStatus(0);

            articleService.deleteArticleTags(id);

            articleService.updateArticle(originArticle);
            log.info("finish update article");

            log.info("finish delete tags");
            for (Integer tagId : tags) {
                articleService.addTag(id, tagId.longValue());
            }
        }

        return ResultVOUtil.success(true);
    }

    @GetMapping("/delete")
    public ResultVO deleteArticle(@RequestParam(value = "id") Long id) {
        articleService.deleteArticleById(id);
        return ResultVOUtil.success(true);
    }

    @GetMapping("/admin/recentWithCondition")
    public ResultVO getArticlesByStatusAndTags(@RequestParam(value = "curPage") Integer pageNum,
                                               @RequestParam(value = "pageSize") Integer pageSize,
                                               @RequestParam(value = "status") Integer status,
                                               @RequestParam(value = "tagList") List<Integer> tagList) {

        if (pageSize <= 0) throw new DenialOfServiceException();

        Set<ArticleDTO> articles = new TreeSet<>((o1, o2) -> o1.updateTime.before(o2.updateTime) ? -1 : 1);

        if (tagList.size() == 0) {
            List<ArticleDTO> articleDTOS = articleService.getAllArticles();
            for (ArticleDTO article : articleDTOS) {
                if (status == 0 || (status == 3 && article.getDeleteTime() != null) || (article.getDeleteTime() == null && (article.getStatus() ^ 1) == status - 1))
                    articles.add(article);
            }
        } else {
            for (Integer tagId : tagList) {
                List<ArticleDTO> articleDTOS = articleService.getArticlesByOneTag(tagId.longValue());

                for (ArticleDTO article : articleDTOS) {
                    if (status == 0 || (status == 3 && article.getDeleteTime() != null) || (article.getDeleteTime() == null && (article.getStatus() ^ 1) == status - 1))
                        articles.add(article);
                }
            }
        }

        Integer totalNum = articles.size();
        Integer maxNum = (int) Math.ceil((double) totalNum / pageSize);

        if (pageNum > maxNum) {
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        List<ArticleDTO> data = new ArrayList(articles);

        PageableArticle result = new PageableArticle();
        result.setData(data.subList(pageNum * pageSize, Math.min((pageNum + 1) * pageSize, totalNum)));
        result.setCurrentPage(pageNum + 1);
        result.setPageSize(pageSize);
        result.setPageNum(maxNum);

        return ResultVOUtil.success(result);
    }
}
