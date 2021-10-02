package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.LinkDTO;
import group.purr.purrbackend.entity.Link;
import group.purr.purrbackend.repository.LinkRepository;
import group.purr.purrbackend.service.LinkService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LinkServiceImpl implements LinkService {

    final
    ModelMapper modelMapper;

    final
    LinkRepository linkRepository;

    public LinkServiceImpl(ModelMapper modelMapper, LinkRepository linkRepository) {
        this.modelMapper = modelMapper;
        this.linkRepository = linkRepository;
    }

    @Override
    public Boolean createBy(LinkDTO linkDTO) {
        Link link = modelMapper.map(linkDTO, Link.class);
        Date currentTime = new Date();
        link.setCreateTime(currentTime);
        linkRepository.saveAndFlush(link);
        return true;
    }

    @Override
    public void deleteAll() {
        linkRepository.deleteAll();
    }

}
