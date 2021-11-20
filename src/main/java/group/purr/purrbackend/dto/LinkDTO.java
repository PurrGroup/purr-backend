package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LinkDTO {

    private Long ID;

    // refer to external web
    private String url;

    private String name;

    // short link
    private String linkName;

    private String imageUrl;

    private String target;

    private String description;

    private Integer visitCount;

    private Integer citeCount;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String linkRel;

    private String linkRss;

    private String category;
}
