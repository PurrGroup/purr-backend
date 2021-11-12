package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.PageDTO;
import group.purr.purrbackend.entity.Article;
import group.purr.purrbackend.entity.Page;
import group.purr.purrbackend.repository.PageRepository;
import group.purr.purrbackend.service.PageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Long getTotal() {
        return pageRepository.count();
    }

    @Override
    public List<PageDTO> getRecentPages(Pageable pageable) {
        org.springframework.data.domain.Page<Page> pages = pageRepository.findAll(pageable);
        List<PageDTO> result = new ArrayList<>();

        for (Page page : pages.getContent()){
            PageDTO pageDTO = modelMapper.map(page, PageDTO.class);
            pageDTO.setPingStatus(null);
            pageDTO.setPinged(null);
            pageDTO.setToPing(null);
            pageDTO.setCommentStatus(null);
            result.add(pageDTO);
        }

        return result;
    }

    @Override
    public Long getTotalExceptDeleted() {
        return pageRepository.countByDeleteTimeIsNull();
    }

    @Override
    public List<PageDTO> getRecentPagesExceptDeleted(Pageable pageable) {
        org.springframework.data.domain.Page<Page> pages = pageRepository.findAllByDeleteTimeIsNull(pageable);
        List<PageDTO> result = new ArrayList<>();

        for (Page page : pages.getContent()){
            PageDTO pageDTO = modelMapper.map(page, PageDTO.class);
            pageDTO.setCommentStatus(null);
            pageDTO.setPingStatus(null);
            pageDTO.setToPing(null);
            pageDTO.setPinged(null);
            result.add(pageDTO);
        }

        return result;
    }
}
