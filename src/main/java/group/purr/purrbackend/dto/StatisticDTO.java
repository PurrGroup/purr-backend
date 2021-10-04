package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatisticDTO {

    private Long commentCount;

    private Long articleCount;

    private Long thumbCount;

    private Long viewCount;

    private List<Long> latestViewCount;
}
