package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleDTO {

    public Long id;

    public String name;

    public Date createTime;

    public Date updateTime;

    public String linkName;

    public String target;

    public String backgroundUrl;

    public String author;

    public Integer commentCount;

    public Integer thumbCount;

    public Integer shareCount;

    public Integer viewCount;

    public Integer status;

    public Integer commentStatus;

    public String pingStatus;

    public String toPing;

    public String pinged;

    public Integer isOriginal;

    public Integer isPinned;

    public Integer isRecommended;

    public String articleAbstract;

    public Date deleteTime;

    public String content;

    public String html;

    public List<TagDTO> tags;

    private Integer copyright;

    private Integer contract;

    private String copyrightInfo;

}
