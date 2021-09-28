package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.PageDTO;
import group.purr.purrbackend.entity.Page;
import group.purr.purrbackend.repository.PageRepository;
import group.purr.purrbackend.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    PageRepository pageRepository;

    @Override
    public Long createPage(PageDTO pageDTO) {
        Page page = pageDTO.convertTo();
        Page result = pageRepository.save(page);

        return result.getID();
    }
}
