package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.BlogMetaConstants;
import group.purr.purrbackend.entity.BlogMeta;
import group.purr.purrbackend.repository.BlogMetaRepository;
import group.purr.purrbackend.service.MetaService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

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
//        Long count = blogmetaRepository.countAllByOptionKey("blog_title");
        long count = blogmetaRepository.count();
        return count != 0;
    }

    @Override
    public Boolean createBy(String blogTitle, String domain, String favicon) {
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

        BlogMeta timeMeta = new BlogMeta();
        timeMeta.setOptionKey(BlogMetaConstants.CREATE_TIME);
        Date currentTime = new Date();
        timeMeta.setOptionValue(currentTime.toString());
        blogmetaRepository.save(timeMeta);

        BlogMeta faviconMeta = new BlogMeta();
        faviconMeta.setOptionKey(BlogMetaConstants.FAVICON);
        faviconMeta.setOptionValue(favicon);
        blogmetaRepository.save(faviconMeta);

        return true;
    }

    @Override
    public void deleteAll() {
        blogmetaRepository.deleteAll();
    }
}
