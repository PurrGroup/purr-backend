package group.purr.purrbackend.dto;

import group.purr.purrbackend.entity.Article;

import java.util.Date;

public class ArticleDTO {
    public Long ID;

    public String name;

    public Date createTime;

    public Date updateTime;

    public String linkName;

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

    public String articleAbstract;
    
    public Date deleteTime;

    public String content;

    // TODO
    public Article convertTo(){
        Article article = null;
        return article;
    }
}
