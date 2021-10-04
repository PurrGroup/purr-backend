package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BlogMetaDTO {

    private String blogTitle;

    private String domain;

    private String apiUrl;

    private Date createTime;

    private String favicon;
}
