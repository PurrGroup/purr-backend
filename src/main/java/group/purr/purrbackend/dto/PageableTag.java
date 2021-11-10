package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageableTag {
    private Integer pageNum;
    private Integer pageSize;
    private Integer currentPage;
    private List<TagDTO> data;
}
