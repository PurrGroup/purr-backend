package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.BlogMetaDTO;

public interface MetaService {
    Boolean queryInstalled();

    /**
     * 下载时创建网站元信息
     *
     * @param blogTitle
     * @param domain
     * @param favicon
     * @return true/false 是否创建成功
     */
    Boolean createBy(String blogTitle, String domain, String favicon);

    Boolean updateApiUrl(String apiUrl);

    void deleteAll();

    BlogMetaDTO getProfile();
}

