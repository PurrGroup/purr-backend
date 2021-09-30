package group.purr.purrbackend.dto;


import lombok.Data;
import java.util.Date;

@Data
public class PageDTO {

    private Long ID;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String name;

    private String urlName;

    private String description;

    private Integer commentStatus;

    private String backgroundUrl;

    private Integer commentCount;

    private Integer thumbCount;

    private Integer shareCount;

    private Integer viewCount;

    private Integer status;

    private String pingStatus;

    private String toPing;

    private String pinged;

    private String content;
}
