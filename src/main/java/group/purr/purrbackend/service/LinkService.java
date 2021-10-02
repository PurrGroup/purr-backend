package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.LinkDTO;

public interface LinkService {
    Boolean createBy(LinkDTO linkDTO);

    void deleteAll();
}
