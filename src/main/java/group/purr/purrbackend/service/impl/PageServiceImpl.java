package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.PageDTO;
import group.purr.purrbackend.entity.Article;
import group.purr.purrbackend.entity.Page;
import group.purr.purrbackend.repository.PageRepository;
import group.purr.purrbackend.service.PageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PageServiceImpl implements PageService {
    final
    PageRepository pageRepository;

    final
    ModelMapper modelMapper;

    public PageServiceImpl(PageRepository pageRepository, ModelMapper modelMapper) {
        this.pageRepository = pageRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Long createPage(PageDTO pageDTO) {
        Page page = modelMapper.map(pageDTO, Page.class);
        Date currentTime = new Date();
        page.setCreateTime(currentTime);
        page.setUpdateTime(currentTime);
        Page result = pageRepository.save(page);
        return result.getID();
    }

    @Override
    public String getPageUrlByID(Long postID) {
        Page page = pageRepository.findByID(postID);
        return page.getUrlName();
    }

    @Override
    public void setCommentStatus(Long id, Integer commentStatus) {
        Page page = pageRepository.findByID(id);
        page.setCommentStatus(commentStatus);

        pageRepository.save(page);
    }
}
