package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.CommitDTO;
import group.purr.purrbackend.entity.Commit;
import group.purr.purrbackend.entity.Visit;
import group.purr.purrbackend.repository.*;
import group.purr.purrbackend.service.StatisticService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticServiceImpl implements StatisticService {
    final CommentRepository commentRepository;

    final ArticleRepository articleRepository;

    final PageRepository pageRepository;

    final MomentRepository momentRepository;

    final VisitRepository visitRepository;

    final CommitRepository commitRepository;

    final ModelMapper modelMapper;

    public StatisticServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository, PageRepository pageRepository, MomentRepository momentRepository, VisitRepository visitRepository, CommitRepository commitRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.pageRepository = pageRepository;
        this.momentRepository = momentRepository;
        this.visitRepository = visitRepository;
        this.commitRepository = commitRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long getCommentCount() {
        return commentRepository.count();
    }

    @Override
    public Long getArticleCount() {
        return articleRepository.count();
    }

    @Override
    public Long getThumbCount() {
        Long articleThumbCount;
        Long pageThumbCount;
        Long momentThumbCount;
        Long totalThumbCount;


        articleThumbCount = articleRepository.sumAllByThumb();
        pageThumbCount = pageRepository.sumByThumb();
        momentThumbCount = momentRepository.sumByThumb();

        if(articleThumbCount == null) articleThumbCount = new Long(0L);
        if(pageThumbCount == null) pageThumbCount = new Long(0L);
        if(momentThumbCount == null) momentThumbCount = new Long(0L);

        totalThumbCount = articleThumbCount + pageThumbCount + momentThumbCount;

        return totalThumbCount;
    }

    @Override
    public Long getViewCount() {
        Long articleViewCount = articleRepository.sumByView();
        Long pageViewCount = pageRepository.sumByView();

        if(articleViewCount == null) articleViewCount = new Long(0L);
        if(pageViewCount == null) pageViewCount = new Long(0L);

        Long totalViewCount = articleViewCount + pageViewCount;

        return totalViewCount;
    }

    @Override
    public List<Long> getLatestViewCount() {
        List<Long> latestViewCount = new LinkedList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date currentDate = new Date();
        String beginTime;
        String endTime;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        date = calendar.getTime();
        beginTime = simpleDateFormat.format(date);

        endTime = simpleDateFormat.format(currentDate);

        List<Visit> latestVisitCount = visitRepository.selectByTime(beginTime, endTime);
        int length = latestVisitCount.size();

        for(; length < 7; length++)
            latestViewCount.add(new Long(0L));

        for(Visit visit : latestVisitCount){
            latestViewCount.add(new Long((long)visit.getVisitCount()));
        }

        return latestViewCount;
    }

    @Override
    public List<CommitDTO> getLatestCommitCount(Date beginDate, Date endDate) {
//        List<Long> latestCommitCount = new LinkedList<>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        Date currentDate = new Date();
//        String beginTime;
//        String endTime;
//        Calendar calendar = Calendar.getInstance();
//
//        calendar.setTime(currentDate);
//        calendar.add(Calendar.DAY_OF_MONTH, -days + 1);
//        date = calendar.getTime();
//        beginTime = simpleDateFormat.format(date);
//
//        endTime = simpleDateFormat.format(currentDate);

        List<Commit> latestCommit = commitRepository.selectByTime(beginDate, endDate);
        List<CommitDTO> commits = new ArrayList<>();

        for (Commit commit : latestCommit){
            commits.add(modelMapper.map(commit, CommitDTO.class));
        }

        return commits;
    }
}
