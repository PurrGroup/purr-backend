package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.VisitDTO;

import java.util.List;

public interface StatisticService {
    Long getCommentCount();
    Long getArticleCount();
    Long getThumbCount();
    Long getViewCount();
    List<Long> getLatestViewCount();
    List<Long> getLatestCommitCount(Integer days);
}
