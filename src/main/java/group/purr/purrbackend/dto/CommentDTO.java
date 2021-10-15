package group.purr.purrbackend.dto;


import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {

    private Long ID;

    private Long postID;

    private String postUrl;

    private Integer postCategory;

    private String authorName;

    private Integer isAnonymous;

    private String authorQQ;

    private String authorUrl;

    private String authorEmail;

    private String authorIP;

    private Date date;

    private String content;

    private Integer approved;

    private String userAgent;

    private Long parentID;

    private String parentAuthorName;

}
