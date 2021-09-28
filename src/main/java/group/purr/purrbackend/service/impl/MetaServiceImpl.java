package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.BlogMetaConstants;
import group.purr.purrbackend.entity.BlogMeta;
import group.purr.purrbackend.repository.BlogMetaRepository;
import group.purr.purrbackend.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MetaServiceImpl implements MetaService {
    @Autowired
    BlogMetaRepository blogmetaRepository;

    /**
     * 判断是否已安装博客
     * @return 返回一个布尔值，若为true则已安装
     */
    @Override
    public boolean isInstalled() {
        Long count = blogmetaRepository.countAllByOptionKey("blog_title");
        return count != 0;
    }

    @Override
    public boolean createBy(String blogTitle, String domain, String createTime, String favicon) {
        Assert.notNull(blogTitle, "BlogTitle cannot be null.");
        Assert.notNull(domain, "Domain cannot be null.");
        Assert.notNull(createTime, "CreateTime cannot be null.");
        Assert.notNull(favicon, "Favicon cannot be null.");

        BlogMeta titleMeta = new BlogMeta();
        titleMeta.setOptionKey(BlogMetaConstants.blogTitle);
        titleMeta.setOptionValue(blogTitle);
        blogmetaRepository.save(titleMeta);

        BlogMeta domainMeta = new BlogMeta();
        domainMeta.setOptionKey(BlogMetaConstants.domain);
        domainMeta.setOptionValue(domain);
        blogmetaRepository.save(domainMeta);

        BlogMeta timeMeta = new BlogMeta();
        timeMeta.setOptionKey(BlogMetaConstants.createTime);
        timeMeta.setOptionValue(createTime);
        blogmetaRepository.save(timeMeta);

        BlogMeta faviconMeta = new BlogMeta();
        faviconMeta.setOptionKey(BlogMetaConstants.favicon);
        faviconMeta.setOptionValue(favicon);
        blogmetaRepository.save(faviconMeta);

        return true;
    }
}
