package group.purr.purrbackend.dto;

import group.purr.purrbackend.entity.Media;
import lombok.Data;
import java.util.List;

@Data
public class PageableMedia {
    private Integer pageNum;
    private Integer pageSize;
    private Integer currentPage;
    private List<MediaDTO> data;
}
