package group.purr.purrbackend.dto;

import lombok.Data;
import java.util.Date;

@Data
public class TagDTO {

    private Long ID;

    private String name;

    private String linkName;

    private String backgroundUrl;

    private String target;

    private String description;

    private Integer visitCount;

    private Integer citeCount;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String linkRel;

    private String linkRss;

    private String color;
}
