package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.TagDTO;
import group.purr.purrbackend.entity.Tag;
import group.purr.purrbackend.repository.TagRepository;
import group.purr.purrbackend.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TagServiceImpl implements TagService {

    final
    ModelMapper modelMapper;

    final
    TagRepository tagRepository;

    public TagServiceImpl(ModelMapper modelMapper, TagRepository tagRepository) {
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
    }

    @Override
    public Long createBy(TagDTO tagDTO){
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        Date currentTime = new Date();
        tag.setCreateTime(currentTime);
        Tag result = tagRepository.saveAndFlush(tag);
        return result.getID();
    }

    @Override
    public void deleteAll() {
        tagRepository.deleteAll();
    }

}
