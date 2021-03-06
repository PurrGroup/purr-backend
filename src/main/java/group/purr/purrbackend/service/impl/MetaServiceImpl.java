package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.BlogMetaConstants;
import group.purr.purrbackend.dto.BlogMetaDTO;
import group.purr.purrbackend.entity.BlogMeta;
import group.purr.purrbackend.repository.BlogMetaRepository;
import group.purr.purrbackend.service.MetaService;
import group.purr.purrbackend.utils.PurrUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;

@Service
public class MetaServiceImpl implements MetaService {
    final
    BlogMetaRepository blogmetaRepository;

    public MetaServiceImpl(BlogMetaRepository blogmetaRepository) {
        this.blogmetaRepository = blogmetaRepository;
    }

    /**
     * 判断是否已安装博客
     *
     * @return 返回一个布尔值，若为true则已安装
     */
    @Override
    public Boolean queryInstalled() {
        long count = blogmetaRepository.count();
        return count != 0;
    }

    @Override
    public Boolean createBy(String blogTitle, String domain, String favicon, String host) {
        Assert.notNull(blogTitle, "BlogTitle must not be null.");
        Assert.notNull(domain, "Domain must not be null.");
        Assert.notNull(favicon, "Favicon must not be null.");

        BlogMeta titleMeta = new BlogMeta();
        titleMeta.setOptionKey(BlogMetaConstants.BLOG_TITLE);
        titleMeta.setOptionValue(blogTitle);
        blogmetaRepository.save(titleMeta);

        BlogMeta domainMeta = new BlogMeta();
        domainMeta.setOptionKey(BlogMetaConstants.DOMAIN);
        domainMeta.setOptionValue(domain);
        blogmetaRepository.save(domainMeta);

        BlogMeta apiUrlMeta = new BlogMeta();
        apiUrlMeta.setOptionKey(BlogMetaConstants.API_URL);
        apiUrlMeta.setOptionValue(domain);
        blogmetaRepository.save(apiUrlMeta);

        BlogMeta timeMeta = new BlogMeta();
        timeMeta.setOptionKey(BlogMetaConstants.CREATE_TIME);
        Date currentTime = new Date();
        timeMeta.setOptionValue(currentTime.toString());
        blogmetaRepository.save(timeMeta);

        BlogMeta faviconMeta = new BlogMeta();
        faviconMeta.setOptionKey(BlogMetaConstants.FAVICON);
        faviconMeta.setOptionValue(favicon);
        blogmetaRepository.save(faviconMeta);

        BlogMeta hostMeta = new BlogMeta();
        hostMeta.setOptionKey(BlogMetaConstants.RESOURCES_HOST);
        hostMeta.setOptionValue(host);
        blogmetaRepository.save(hostMeta);

        BlogMeta articlePrefixMeta = new BlogMeta();
        articlePrefixMeta.setOptionKey(BlogMetaConstants.ARTICLE_PREFIX_OPTION);
        articlePrefixMeta.setOptionValue("0");
        blogmetaRepository.save(articlePrefixMeta);

        BlogMeta pagePrefixMeta = new BlogMeta();
        pagePrefixMeta.setOptionKey(BlogMetaConstants.PAGE_PREFIX_OPTION);
        pagePrefixMeta.setOptionValue("0");
        blogmetaRepository.save(pagePrefixMeta);

        return true;
    }

    @Override
    public Boolean updateApiUrl(String apiUrl) {
        BlogMeta apiUrlMeta = new BlogMeta();
        apiUrlMeta.setOptionKey(BlogMetaConstants.API_URL);
        apiUrlMeta.setOptionValue(apiUrl);
        blogmetaRepository.save(apiUrlMeta);
        return true;
    }

    @Override
    public void deleteAll() {
        blogmetaRepository.deleteAll();
    }

    @Override
    public BlogMetaDTO getProfile() {
        BlogMeta blogTitle = blogmetaRepository.findBlogMetaByOptionKey(BlogMetaConstants.BLOG_TITLE).orElse(new BlogMeta());
        BlogMeta favicon = blogmetaRepository.findBlogMetaByOptionKey(BlogMetaConstants.FAVICON).orElse(new BlogMeta());

        BlogMetaDTO blogMetaDTO = new BlogMetaDTO();
        blogMetaDTO.setBlogTitle(blogTitle.getOptionValue());
        blogMetaDTO.setFavicon(favicon.getOptionValue());

        return blogMetaDTO;
    }

    @Override
    public String getArticleLinkNamePrefix() {
        Optional<BlogMeta> option = blogmetaRepository.findBlogMetaByOptionKey(BlogMetaConstants.ARTICLE_PREFIX_OPTION);
        String optionValue = option.map(BlogMeta::getOptionValue).orElseGet(() -> "0");
        return PurrUtils.getArticleLinkNamePrefix(optionValue);
    }

    @Override
    public String getPageLinkNamePrefix() {
        Optional<BlogMeta> option = blogmetaRepository.findBlogMetaByOptionKey(BlogMetaConstants.PAGE_PREFIX_OPTION);
        String optionValue = option.map(BlogMeta::getOptionValue).orElseGet(() -> "0");
        return PurrUtils.getPageLinkNamePrefix(optionValue);
    }
}
