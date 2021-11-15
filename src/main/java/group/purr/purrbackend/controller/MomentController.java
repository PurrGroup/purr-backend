package group.purr.purrbackend.controller;

import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.dto.MomentDTO;
import group.purr.purrbackend.dto.PageableMoment;
import group.purr.purrbackend.exception.DenialOfServiceException;
import group.purr.purrbackend.service.MetaService;
import group.purr.purrbackend.service.MomentService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/moment")
@Slf4j
public class MomentController {
    final MomentService momentService;

    final ModelMapper modelMapper;

    final MetaService metaService;

    public MomentController(MomentService momentService, ModelMapper modelMapper, MetaService metaService) {
        this.momentService = momentService;
        this.modelMapper = modelMapper;
        this.metaService = metaService;
    }

    @GetMapping("/recent")
    public ResultVO getRecentMoments(@RequestParam(value = "curPage") Integer pageNum,
                                     @RequestParam(value = "pageSize") Integer pageSize) {

        if (pageSize <= 0) throw new DenialOfServiceException();

        Long total = momentService.getTotalCount();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if (pageNum > maxNum) {
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<MomentDTO> moments = momentService.getRecentMoments(pageable);

        PageableMoment result = new PageableMoment();
        result.setData(moments);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum - 1);

        return ResultVOUtil.success(result);
    }

    @GetMapping("/admin/recent")
    public ResultVO getRecentMomentsAuthorized(@RequestParam(value = "curPage") Integer pageNum,
                                               @RequestParam(value = "pageSize") Integer pageSize) {

        if (pageSize <= 0) throw new DenialOfServiceException();

        Long total = momentService.getTotalCount();
        int maxNum = (int) Math.ceil((double) total / pageSize);

        if (pageNum > maxNum) {
            pageNum = maxNum;
        }
        pageNum = Math.max(pageNum - 1, 0);

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        List<MomentDTO> moments = momentService.getRecentMoments(pageable);

        PageableMoment result = new PageableMoment();
        result.setData(moments);
        result.setPageNum(maxNum);
        result.setPageSize(pageSize);
        result.setCurrentPage(pageNum - 1);

        return ResultVOUtil.success(result);
    }

    @PostMapping("/edit")
    public ResultVO editMoment(@RequestBody JSONObject json) {
        MomentDTO momentDTO = JSONObject.toJavaObject(json, MomentDTO.class);

        if (momentDTO.getID() == -1) {
            momentDTO.setID(null);
            momentDTO.setThumbCount(0);
            if (momentDTO.getUpdateTime() == null)
                momentDTO.setUpdateTime(momentDTO.getCreateTime());

            if (momentDTO.getAnnotation() == null)
                momentDTO.setAnnotation(metaService.getProfile().getBlogTitle());
        } else {
            momentDTO.setUpdateTime(new Date());
        }

        momentService.editMoment(momentDTO);

        return ResultVOUtil.success(true);
    }
}
