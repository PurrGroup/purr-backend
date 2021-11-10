package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.LinkDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LinkService {
    Boolean createBy(LinkDTO linkDTO);

    void deleteAll();

    List<LinkDTO> getRecentLinks(Pageable pageable);

    Long getTotalCount();
}
