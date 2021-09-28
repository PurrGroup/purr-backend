package group.purr.purrbackend.service;

public interface MetaService {
    boolean isInstalled();

    /**
     * 下载时创建网站元信息
     * @param blogTitle
     * @param domain
     * @param createTime
     * @param favicon
     * @return true/false 是否创建成功
     */
    boolean createBy(String blogTitle, String domain, String createTime, String favicon);
}

