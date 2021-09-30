package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.PageDTO;
import group.purr.purrbackend.entity.Page;
import group.purr.purrbackend.repository.PageRepository;
import group.purr.purrbackend.service.PageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    PageRepository pageRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public Long createPage(PageDTO pageDTO) {
        Page page = modelMapper.map(pageDTO, Page.class);
        Date currentTime = new Date();
        page.setCreateTime(currentTime);
        Page result = pageRepository.save(page);
        return result.getID();
    }
}
