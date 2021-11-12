package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.TagDTO;
import group.purr.purrbackend.entity.Tag;
import group.purr.purrbackend.repository.TagRepository;
import group.purr.purrbackend.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Long createBy(TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        Date currentTime = new Date();
        tag.setCreateTime(currentTime);
        tag.setUpdateTime(currentTime);
        Tag result = tagRepository.saveAndFlush(tag);
        return result.getID();
    }

    @Override
    public void deleteAll() {
        tagRepository.deleteAll();
    }

    @Override
    public Long getTotalCount() {
        return tagRepository.countByDeleteTimeIsNull();
    }

    @Override
    public List<TagDTO> getRecentTags(Pageable pageable) {
        Page<Tag> tags = tagRepository.findAllByDeleteTimeIsNull(pageable);

        List<TagDTO> result = new ArrayList<>();
        for (Tag tag : tags.getContent()) {
            TagDTO dto = modelMapper.map(tag, TagDTO.class);
            result.add(dto);
        }

        return result;
    }

}
