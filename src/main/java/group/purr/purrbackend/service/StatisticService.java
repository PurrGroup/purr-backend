package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.CommitDTO;

import java.util.Date;
import java.util.List;

public interface StatisticService {
    Long getCommentCount();

    Long getArticleCount();

    Long getThumbCount();

    Long getViewCount();

    List<Long> getLatestViewCount();

    List<CommitDTO> getLatestCommitCount(Date beginDate, Date endDate);
}
