package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.MomentDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MomentService {
    Long createMoment(MomentDTO momentDTO);

    List<MomentDTO> getRecentMoments(Pageable pageable);

    Long getTotalCount();

    void editMoment(MomentDTO momentDTO);
}
