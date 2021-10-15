package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.PageDTO;

public interface PageService {
    /**
     * 创建新页面
     *
     * @param pageDTO
     * @return 返回页面ID
     */
    Long createPage(PageDTO pageDTO);

    String getPageUrlByID(Long postID);
}
