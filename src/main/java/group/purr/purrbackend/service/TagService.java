package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.TagDTO;

public interface TagService {
    Long createBy(TagDTO tagDTO);

    void deleteAll();
}
