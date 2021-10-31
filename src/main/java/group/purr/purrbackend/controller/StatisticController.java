package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.CommitDTO;
import group.purr.purrbackend.dto.StatisticDTO;
import group.purr.purrbackend.service.StatisticService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class StatisticController {
    final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/statistics")
    public ResultVO getStatistics(){
        StatisticDTO statisticDTO = new StatisticDTO();

        statisticDTO.setCommentCount(statisticService.getCommentCount());
        statisticDTO.setArticleCount(statisticService.getArticleCount());
        statisticDTO.setThumbCount(statisticService.getThumbCount());
        statisticDTO.setViewCount(statisticService.getViewCount());
        statisticDTO.setLatestViewCount(statisticService.getLatestViewCount());

        return ResultVOUtil.success(statisticDTO);
    }

    @GetMapping("/article/commit")
    public ResultVO getCommit(@RequestParam(value = "beginDate")String beginDate,
                              @RequestParam(value = "endDate")String endDate) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition position = new ParsePosition(8);
        Date beginTime = simpleDateFormat.parse(beginDate);
        Date endTime = simpleDateFormat.parse(endDate);

        List<CommitDTO> commits = statisticService.getLatestCommitCount(beginTime, endTime);
        for (CommitDTO commit : commits){
            commit.setCommitTime(null);
            String dateString = simpleDateFormat.format(commit.getCommitDate());
            commit.setCommitDate(null);
            commit.setDate(dateString);
        }

        return ResultVOUtil.success(commits);
    }
}
