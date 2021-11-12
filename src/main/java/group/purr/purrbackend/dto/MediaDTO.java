package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MediaDTO {

    private Long ID;

    private String url;

    private String fileCategory;

    private String fileType;

    private Integer host;

    private Date createTime;

    private Date deleteTime;

    private String name;

    /**
     * 以b为单位
     */
    private String size;

    private Integer imageHeight;

    private Integer imageWidth;

    private String thumbnailPath;


}
