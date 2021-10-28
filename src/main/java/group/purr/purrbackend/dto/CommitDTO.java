package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommitDTO {
    private Date commitDate;

    private Date commitTime;

    private Integer commitCount;
}
