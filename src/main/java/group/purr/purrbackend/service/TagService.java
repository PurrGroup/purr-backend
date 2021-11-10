package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.TagDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Long createBy(TagDTO tagDTO);

    void deleteAll();

    Long getTotalCount();

    List<TagDTO> getRecentTags(Pageable pageable);
}
