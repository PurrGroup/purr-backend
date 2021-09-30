package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.MenuDTO;
import group.purr.purrbackend.dto.TagDTO;
import group.purr.purrbackend.entity.Menu;
import group.purr.purrbackend.entity.Tag;
import group.purr.purrbackend.repository.TagRepository;
import group.purr.purrbackend.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TagRepository tagRepository;

    @Override
    public Boolean createBy(TagDTO tagDTO){
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        Date currentTime = new Date();
        tag.setCreateTime(currentTime);
        tagRepository.saveAndFlush(tag);
        return true;
    }

}
