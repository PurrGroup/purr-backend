package group.purr.purrbackend.dto;

import lombok.Data;
import java.util.Date;

@Data
public class MediaDTO {

    private String url;

    private Integer category;

    private Integer host;

    private Date createTime;

    private String name;
}
