package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.PageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PageService {
    /**
     * 创建新页面
     *
     * @param pageDTO
     * @return 返回页面ID
     */
    Long createPage(PageDTO pageDTO);

    String getPageUrlByID(Long postID);

    Long getTotal();

    List<PageDTO> getRecentPages(Pageable pageable);

    Long getTotalExceptDeleted();

    List<PageDTO> getRecentPagesExceptDeleted(Pageable pageable);
}
