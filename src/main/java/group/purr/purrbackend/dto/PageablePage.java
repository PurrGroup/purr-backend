package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageablePage {
    private Integer pageNum;
    private Integer pageSize;
    private Integer currentPage;
    private List<PageDTO> data;
}
