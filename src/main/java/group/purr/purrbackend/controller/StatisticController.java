package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.StatisticDTO;
import group.purr.purrbackend.service.StatisticService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ResultVO getCommit(@RequestParam(value = "count")Integer count){
        return ResultVOUtil.success(statisticService.getLatestCommitCount(count));
    }
}
