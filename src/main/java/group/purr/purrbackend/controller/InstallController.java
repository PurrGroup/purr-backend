package group.purr.purrbackend.controller;

import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.constant.MagicConstants;
import group.purr.purrbackend.dto.*;
import group.purr.purrbackend.enumerate.PostCategoryEnum;
import group.purr.purrbackend.exception.AlreadyInstalledException;
import group.purr.purrbackend.exception.AlreadyUninstalledException;
import group.purr.purrbackend.service.*;
import group.purr.purrbackend.utils.PurrUtils;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class InstallController {

    final
    MetaService metaService;

    final
    AuthorService authorService;

    final
    MenuService menuService;

    final
    ArticleService articleService;

    final
    TagService tagService;

    final
    CommentService commentService;

    final
    LinkService linkService;

    public InstallController(MetaService metaService,
                             AuthorService authorService,
                             MenuService menuService,
                             ArticleService articleService,
                             TagService tagService,
                             CommentService commentService,
                             LinkService linkService) {
        this.metaService = metaService;
        this.authorService = authorService;
        this.menuService = menuService;
        this.articleService = articleService;
        this.tagService = tagService;
        this.commentService = commentService;
        this.linkService = linkService;
    }


    @PostMapping("/install")
    public ResultVO install(@RequestBody JSONObject installJSON) {

        Boolean isInstalled = metaService.queryInstalled();
        if (isInstalled) {
            throw new AlreadyInstalledException();
        }

        // get initial information
        String userName = installJSON.getString("username");
        String email = installJSON.getString("email");
        String psd = installJSON.getString("password");
        String hostname = installJSON.getString("hostname");
        String blogName = installJSON.getString("blogName");

        // initialize meta information
        initializeMeta(blogName, hostname);

        // initialize user information
        createAdmin(userName, psd, email);

        // initialize article information
        ArticleDTO defaultArticle = createArticle(userName);
        Long articleID = defaultArticle.getID();

        // initialize tag information
        Long tagID = createTag();

        //band tag to article
        bandTag(articleID, tagID);

        // initialize comment information
        createComment(articleID);

        // initialize menu information
        MenuDTO defaultMenu = createMenu();
        Long menuID = defaultMenu.getID();

        // initialize menuItem information
        Long aboutID = createMenuItem(menuID);

        // initialize subMenuItem information
        createSubMenu(aboutID);

        return ResultVOUtil.success(true);
    }

    @GetMapping("/uninstall")
    public ResultVO uninstall() {
        Boolean isInstalled = metaService.queryInstalled();
        if (!isInstalled) {
            throw new AlreadyUninstalledException();
        }

        metaService.deleteAll();
        authorService.deleteAll();
        articleService.deleteAll();
        tagService.deleteAll();
        commentService.deleteAll();
        menuService.deleteAll();
        linkService.deleteAll();
        return ResultVOUtil.success("true");
    }

    private void initializeMeta(String blogName, String hostname) {
        String favicon = MagicConstants.DEFAULT_FAVICON;
        metaService.createBy(blogName, hostname, favicon);
    }

    private void createAdmin(String userName, String psd, String email) {
        authorService.createBy(userName, psd, email);
    }

    private ArticleDTO createArticle(String userName) {
        ArticleDTO defaultArticle = new ArticleDTO();

        defaultArticle.setName(MagicConstants.DEFAULT_ARTICLE_NAME);
        defaultArticle.setLinkName(MagicConstants.DEFAULT_ARTICLE_LINK_NAME);
        defaultArticle.setBackgroundUrl(MagicConstants.DEFAULT_ARTICLE_BACKGROUND_URL);
        defaultArticle.setAuthor(userName);
        defaultArticle.setCommentCount(MagicConstants.DEFAULT_ARTICLE_COMMENT_COUNT);
        defaultArticle.setThumbCount(MagicConstants.DEFAULT_ARTICLE_THUMB_COUNT);
        defaultArticle.setShareCount(MagicConstants.DEFAULT_ARTICLE_SHARE_COUNT);
        defaultArticle.setViewCount(MagicConstants.DEFAULT_ARTICLE_VIEW_COUNT);
        defaultArticle.setStatus(MagicConstants.DEFAULT_ARTICLE_STATUS);
        defaultArticle.setCommentStatus(MagicConstants.DEFAULT_ARTICLE_COMMENT_STATUS);
        defaultArticle.setIsOriginal(MagicConstants.DEFAULT_ARTICLE_ORIGINAL);
        defaultArticle.setArticleAbstract(MagicConstants.DEFAULT_ARTICLE_ABSTRACT);
        defaultArticle.setContent(MagicConstants.DEFAULT_ARTICLE_CONTENT);
        defaultArticle.setTarget(MagicConstants.DEFAULT_ARTICLE_TARGET);
        defaultArticle.setIsPinned(MagicConstants.DEFAULT_IS_PINNED);
        Long articleID = articleService.createArticle(defaultArticle);
        defaultArticle.setID(articleID);

        LinkDTO articleLink = new LinkDTO();
        articleLink.setName(defaultArticle.getName());
        articleLink.setLinkName(defaultArticle.getLinkName());
        articleLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        articleLink.setTarget(defaultArticle.getTarget());
        articleLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        articleLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        articleLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(articleLink);

        return defaultArticle;
    }

    private Long createTag() {
        TagDTO defaultTag = new TagDTO();

        defaultTag.setName(MagicConstants.DEFAULT_TAG_NAME);
        defaultTag.setLinkName(MagicConstants.DEFAULT_TAG_LINK_NAME);
        defaultTag.setBackgroundUrl(MagicConstants.DEFAULT_TAG_BACKGROUND_URL);
        defaultTag.setTarget(MagicConstants.DEFAULT_TAG_TARGET);
        defaultTag.setDescription(MagicConstants.DEFAULT_TAG_DESCRIPTION);
        defaultTag.setVisitCount(MagicConstants.DEFAULT_TAG_VISIT_COUNT);
        defaultTag.setCiteCount(MagicConstants.DEFAULT_TAG_CITE_COUNT);
        defaultTag.setColor(MagicConstants.DEFAULT_TAG_COLOR);
        Long tagID = tagService.createBy(defaultTag);

        LinkDTO tagLink = new LinkDTO();
        tagLink.setName(defaultTag.getName());
        tagLink.setLinkName(defaultTag.getLinkName());
        tagLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        tagLink.setTarget(defaultTag.getTarget());
        tagLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        tagLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        tagLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(tagLink);

        return tagID;
    }

    private void createComment(Long articleID) {
        CommentDTO defaultComment = new CommentDTO();
        defaultComment.setPostID(articleID);
        defaultComment.setPostCategory(PostCategoryEnum.ARTICLE.getPostCategory());
        defaultComment.setAuthorName(MagicConstants.DEFAULT_COMMENT_AUTHOR);
        defaultComment.setIsAnonymous(MagicConstants.DEFAULT_COMMENT_ANONYMOUS);
        defaultComment.setAuthorIP(MagicConstants.DEFAULT_COMMENT_IP);
        defaultComment.setContent(MagicConstants.DEFAULT_COMMENT_CONTENT);
        defaultComment.setApproved(MagicConstants.DEFAULT_COMMENT_APPROVED);
        defaultComment.setUserAgent(MagicConstants.DEFAULT_COMMENT_AGENT);
        commentService.createComment(defaultComment);
    }

    private MenuDTO createMenu() {
        MenuDTO defaultMenu = new MenuDTO();
        defaultMenu.setName(MagicConstants.DEFAULT_MENU_NAME);
        defaultMenu.setIsDefault(MagicConstants.DEFAULT_MENU);
        MenuDTO saveMenu = menuService.createMenu(defaultMenu);
        Long menuID = saveMenu.getID();
        defaultMenu.setID(menuID);
        return defaultMenu;
    }

    private Long createMenuItem(Long menuID) {
        //create homepage
        MenuItemDTO homePage = new MenuItemDTO();
        homePage.setName(MagicConstants.DEFAULT_MENUITEM_HOMEPAGE_NAME);
        homePage.setUrl(MagicConstants.DEFAULT_MENUITEM_HOMEPAGE_URL);
        homePage.setIcon(MagicConstants.DEFAULT_MENUITEM_ICON);
        homePage.setTarget(MagicConstants.DEFAULT_MENUITEM_TARGET);
        homePage.setIsParent(MagicConstants.DEFAULT_MENUITEM_PARENT);
        menuService.createMenuItem(homePage, menuID);

        LinkDTO homepageLink = new LinkDTO();
        homepageLink.setName(homePage.getName());
        homepageLink.setLinkName(homePage.getUrl());
        homepageLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        homepageLink.setTarget(homePage.getTarget());
        homepageLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        homepageLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        homepageLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(homepageLink);

        //create archives
        MenuItemDTO archives = new MenuItemDTO();
        archives.setName(MagicConstants.DEFAULT_MENUITEM_ARCHIVES_NAME);
        archives.setUrl(MagicConstants.DEFAULT_MENUITEM_ARCHIVES_URL);
        archives.setIcon(MagicConstants.DEFAULT_MENUITEM_ICON);
        archives.setTarget(MagicConstants.DEFAULT_MENUITEM_TARGET);
        archives.setIsParent(MagicConstants.DEFAULT_MENUITEM_PARENT);
        menuService.createMenuItem(archives, menuID);

        LinkDTO archivesLink = new LinkDTO();
        archivesLink.setName(archives.getName());
        archivesLink.setLinkName(archives.getUrl());
        archivesLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        archivesLink.setTarget(archives.getTarget());
        archivesLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        archivesLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        archivesLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(archivesLink);

        //create moment
        MenuItemDTO moment = new MenuItemDTO();
        moment.setName(MagicConstants.DEFAULT_MENUITEM_MOMENT_NAME);
        moment.setUrl(MagicConstants.DEFAULT_MENUITEM_MOMENT_URL);
        moment.setIcon(MagicConstants.DEFAULT_MENUITEM_ICON);
        moment.setTarget(MagicConstants.DEFAULT_MENUITEM_TARGET);
        moment.setIsParent(MagicConstants.DEFAULT_MENUITEM_PARENT);
        menuService.createMenuItem(moment, menuID);

        LinkDTO momentLink = new LinkDTO();
        momentLink.setName(moment.getName());
        momentLink.setLinkName(moment.getUrl());
        momentLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        momentLink.setTarget(moment.getTarget());
        momentLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        momentLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        momentLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(momentLink);

        //create comment
        MenuItemDTO comment = new MenuItemDTO();
        comment.setName(MagicConstants.DEFAULT_MENUITEM_COMMENT_NAME);
        comment.setUrl(MagicConstants.DEFAULT_MENUITEM_COMMENT_URL);
        comment.setIcon(MagicConstants.DEFAULT_MENUITEM_ICON);
        comment.setTarget(MagicConstants.DEFAULT_MENUITEM_TARGET);
        comment.setIsParent(MagicConstants.DEFAULT_MENUITEM_PARENT);
        menuService.createMenuItem(comment, menuID);

        LinkDTO commentLink = new LinkDTO();
        commentLink.setName(comment.getName());
        commentLink.setLinkName(comment.getUrl());
        commentLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        commentLink.setTarget(comment.getTarget());
        commentLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        commentLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        commentLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(commentLink);

        //create friends
        MenuItemDTO friends = new MenuItemDTO();
        friends.setName(MagicConstants.DEFAULT_MENUITEM_FRIENDS_NAME);
        friends.setUrl(MagicConstants.DEFAULT_MENUITEM_FRIENDS_URL);
        friends.setIcon(MagicConstants.DEFAULT_MENUITEM_ICON);
        friends.setTarget(MagicConstants.DEFAULT_MENUITEM_TARGET);
        friends.setIsParent(MagicConstants.DEFAULT_MENUITEM_PARENT);
        menuService.createMenuItem(friends, menuID);

        LinkDTO friendsLink = new LinkDTO();
        friendsLink.setName(friends.getName());
        friendsLink.setLinkName(friends.getUrl());
        friendsLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        friendsLink.setTarget(friends.getTarget());
        friendsLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        friendsLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        friendsLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(friendsLink);

        //create about
        MenuItemDTO about = new MenuItemDTO();
        about.setName(MagicConstants.DEFAULT_MENUITEM_ABOUT_NAME);
        about.setUrl(MagicConstants.DEFAULT_MENUITEM_ABOUT_URL);
        about.setIcon(MagicConstants.DEFAULT_MENUITEM_ICON);
        about.setTarget(MagicConstants.DEFAULT_MENUITEM_TARGET);
        about.setIsParent(1);
        Long aboutID = menuService.createMenuItem(about, menuID);

        LinkDTO aboutLink = new LinkDTO();
        aboutLink.setName(about.getName());
        aboutLink.setLinkName(about.getUrl());
        aboutLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        aboutLink.setTarget(about.getTarget());
        aboutLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        aboutLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        aboutLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(aboutLink);

        return aboutID;
    }

    private void createSubMenu(Long aboutID) {
        //create me
        SubMenuItemDTO me = new SubMenuItemDTO();
        me.setName(MagicConstants.DEFAULT_SUBMENU_ME_NAME);
        me.setUrl(MagicConstants.DEFAULT_SUBMENU_ME_URL);
        me.setIcon(MagicConstants.DEFAULT_SUBMENU_ICON);
        me.setTarget(MagicConstants.DEFAULT_SUBMENU_TARGET);
        me.setParentID(aboutID);
        menuService.createSubMenuItem(me, aboutID);

        LinkDTO meLink = new LinkDTO();
        meLink.setName(me.getName());
        meLink.setLinkName(me.getUrl());
        meLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        meLink.setTarget(me.getTarget());
        meLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        meLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        meLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(meLink);

        //create sitemap
        SubMenuItemDTO siteMap = new SubMenuItemDTO();
        siteMap.setName(MagicConstants.DEFAULT_SUBMENU_SITEMAP_NAME);
        siteMap.setUrl(MagicConstants.DEFAULT_SUBMENU_SITEMAP_URL);
        siteMap.setIcon(MagicConstants.DEFAULT_SUBMENU_ICON);
        siteMap.setTarget(MagicConstants.DEFAULT_SUBMENU_TARGET);
        siteMap.setParentID(aboutID);
        menuService.createSubMenuItem(siteMap, aboutID);

        LinkDTO sitemapLink = new LinkDTO();
        sitemapLink.setName(siteMap.getName());
        sitemapLink.setLinkName(siteMap.getUrl());
        sitemapLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        sitemapLink.setTarget(siteMap.getTarget());
        sitemapLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        sitemapLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        sitemapLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(sitemapLink);

        //create rss
        SubMenuItemDTO rss = new SubMenuItemDTO();
        rss.setName(MagicConstants.DEFAULT_SUBMENU_RSS_NAME);
        rss.setUrl(MagicConstants.DEFAULT_SUBMENU_RSS_URL);
        rss.setIcon(MagicConstants.DEFAULT_SUBMENU_ICON);
        rss.setTarget(MagicConstants.DEFAULT_SUBMENU_TARGET);
        rss.setParentID(aboutID);
        menuService.createSubMenuItem(rss, aboutID);

        LinkDTO rssLink = new LinkDTO();
        rssLink.setName(rss.getName());
        rssLink.setLinkName(rss.getUrl());
        rssLink.setImageUrl(MagicConstants.DEFAULT_LINK_IMAGE_URL);
        rssLink.setTarget(rss.getTarget());
        rssLink.setDescription(MagicConstants.DEFAULT_LINK_DESCRIPTION);
        rssLink.setVisitCount(MagicConstants.DEFAULT_LINK_VISIT_COUNT);
        rssLink.setCiteCount(MagicConstants.DEFAULT_LINK_CITE_COUNT);
        linkService.createBy(rssLink);

    }

    private void bandTag(Long articleID, Long tagID) {
        articleService.addTag(articleID, tagID);
    }
}
