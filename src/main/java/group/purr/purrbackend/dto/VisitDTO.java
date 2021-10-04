package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class VisitDTO {
    private Date visitDate;

    private Date visitTime;

    private Integer visitCount;
}
