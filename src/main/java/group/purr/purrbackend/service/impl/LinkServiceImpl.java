package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.LinkDTO;
import group.purr.purrbackend.entity.Link;
import group.purr.purrbackend.entity.Tag;
import group.purr.purrbackend.repository.LinkRepository;
import group.purr.purrbackend.service.LinkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LinkRepository linkRepository;

    @Override
    public Boolean createBy(LinkDTO linkDTO){
        Link link = modelMapper.map(linkDTO, Link.class);
        Date currentTime = new Date();
        link.setCreateTime(currentTime);
        linkRepository.saveAndFlush(link);
        return true;
    }

}
