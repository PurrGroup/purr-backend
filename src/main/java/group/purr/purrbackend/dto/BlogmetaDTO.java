package group.purr.purrbackend.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BlogmetaDTO {

    private String blogTitle;

    private String domain;

    private Date createTime;

    private String favicon;
}
