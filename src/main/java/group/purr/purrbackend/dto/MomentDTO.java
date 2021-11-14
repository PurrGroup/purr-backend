package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MomentDTO {
    private Long ID;

    private Date createTime;

    private Date updateTime;

    private String content;

    private Integer thumbCount;

    private String backgroundColor;

    private String annotation;

    private Integer visible;
}
