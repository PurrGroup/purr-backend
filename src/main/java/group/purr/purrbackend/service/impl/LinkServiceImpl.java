package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.LinkDTO;
import group.purr.purrbackend.entity.Link;
import group.purr.purrbackend.repository.LinkRepository;
import group.purr.purrbackend.service.LinkService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        link.setUpdateTime(currentTime);
        linkRepository.saveAndFlush(link);
        return true;
    }

    @Override
    public void deleteAll() {
        linkRepository.deleteAll();
    }

    @Override
    public List<LinkDTO> getRecentLinks(Pageable pageable) {
        Page<Link> links = linkRepository.findAll(pageable);

        List<LinkDTO> result = new ArrayList<>();

        for (Link link : links.getContent()) {
            LinkDTO linkDTO = modelMapper.map(link, LinkDTO.class);
            linkDTO.setLinkRss(null);
            linkDTO.setLinkRel(null);
            result.add(linkDTO);
        }

        return result;
    }

    @Override
    public Long getTotalCount() {
        return linkRepository.count();
    }

    @Override
    public List<LinkDTO> getFocus() {
        List<Link> links = linkRepository.findAllByCategoryOrderByUpdateTimeDesc("聚焦");
        Integer count = 0;
        List<LinkDTO> result = new ArrayList<>();

        for (Link link : links) {
            if (link.getDeleteTime() != null) continue;
            count++;
            if (count > 3) break;

            LinkDTO linkDTO = modelMapper.map(link, LinkDTO.class);
            linkDTO.setLinkRss(null);
            linkDTO.setLinkRel(null);
            result.add(linkDTO);
        }

        return result;
    }

}
