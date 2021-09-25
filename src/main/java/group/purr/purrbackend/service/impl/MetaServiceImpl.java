package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.repository.BlogmetaRepository;
import group.purr.purrbackend.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetaServiceImpl implements MetaService {

    @Autowired
    BlogmetaRepository blogmetaRepository;

    /**
     * 判断是否已安装博客
     * @return 返回一个布尔值，若为true则已安装
     */
    @Override
    public boolean isInstalled() {
        Long count = blogmetaRepository.countAllByOptionName("blog_title");
        return count != 0;
    }
}
