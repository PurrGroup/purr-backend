package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SearchDTO {
    public Long id;

    public Long mysqlId;

    public String category;

    public String title;

    public String content;

    public String tags;

    public String url;

    public String createTime;

    public Integer status;
}
