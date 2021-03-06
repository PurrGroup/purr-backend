package group.purr.purrbackend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.dto.PageableMedia;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.IllegalFileException;
import group.purr.purrbackend.service.MediaService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/media")
@Slf4j
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload")
    public ResultVO uploadMedia(@RequestPart("file") MultipartFile file) throws IOException {

        if (file == null) {
            throw new IllegalFileException();
        }

        MediaDTO uploadResult = mediaService.upload(file);

        return ResultVOUtil.success(uploadResult);
    }

    /**
     * 获取所有媒体类型
     *
     * @return
     */
    @GetMapping("/allCategory")
    public ResultVO getAllMediaCategory() {
        List<String> result = mediaService.findAllCategory();
        return ResultVOUtil.success(result);
    }

    /**
     * 获取媒体详细信息
     *
     * @return
     */
    @GetMapping("/detail")
    public ResultVO getDetail(@RequestParam(value = "id") String mediaID) {

        MediaDTO result = mediaService.checkIDAndGetDetail(mediaID);
        if (result == null) {
            return ResultVOUtil.error(ResultEnum.ID_NOT_EXISTED);
        }

        return ResultVOUtil.success(result);
    }

    @GetMapping("/details")
    public ResultVO getMediaDetailByLinkName(@RequestParam(value="linkName")String linkName) {
        Optional<MediaDTO> mediaDTON = mediaService.findMediaByLinkName(linkName);
        return mediaDTON.map(ResultVOUtil::success).orElseGet(() -> ResultVOUtil.error(ResultEnum.CONTENT_NOT_EXIST));
    }

    /**
     * 分页获取所有媒体（不包括已删除的媒体或在回收站中的媒体）
     *
     * @param pageNum  第几页
     * @param pageSize 每页有几条记录
     * @return 当前页的记录信息
     */
    @GetMapping("/all")
    public ResultVO getAllMediaByPage(@RequestParam(value = "curPage") Integer pageNum,
                                      @RequestParam(value = "pageSize") Integer pageSize) {

        Long total = mediaService.getTotalExceptDeleted();

        if (pageSize == 0) {
            PageableMedia result = new PageableMedia();
            result.setPageSize(pageSize);
            result.setCurrentPage(pageNum);
            return ResultVOUtil.success(result);
        }

        Integer maxNum = ((int) Math.ceil((double) total / pageSize)) - 1;

        if ((pageNum - 1) > maxNum) {
            pageNum = maxNum;
        } else pageNum = Math.max(pageNum - 1, 0);

        List<MediaDTO> recentArticles = mediaService.findAllMediaByPage(pageNum, pageSize);

        PageableMedia result = new PageableMedia();
        result.setData(recentArticles);
        result.setCurrentPage(pageNum + 1);
        result.setPageSize(pageSize);
        result.setPageNum(maxNum + 1);
        return ResultVOUtil.success(result);

    }

    @PostMapping("/condition")
    public ResultVO getMediaByStatusAndCategoryByPage(@RequestBody JSONObject jsonObject) {
        Integer status = jsonObject.getInteger("status");  // -1表示不筛选，0表示选择回收站中的，1表示选择未被删除的
        JSONArray categories = jsonObject.getJSONArray("categories");
        Integer curPage = jsonObject.getInteger("curPage");
        Integer pageSize = jsonObject.getInteger("pageSize");

        if (pageSize == 0) {
            PageableMedia result = new PageableMedia();
            result.setPageSize(pageSize);
            result.setCurrentPage(curPage);
            return ResultVOUtil.success(result);
        }

        Set<String> categorySet = new HashSet<>();
        for (Object obj : categories) {
            categorySet.add(obj.toString());
        }

        List<MediaDTO> allMedia = mediaService.findAllMedia();
        List<MediaDTO> mediaDTOS = new ArrayList<>();
        for (MediaDTO mediaDTO : allMedia) {
            if (categorySet.size() == 0) {
                if ((status == -1) || (status == 0 && mediaDTO.getDeleteTime() != null) || (status == 1 && mediaDTO.getDeleteTime() == null)) {
                    mediaDTOS.add(mediaDTO);
                }
            } else {
                if ((status == -1 && categorySet.contains(mediaDTO.getFileCategory()))
                        || (status == 0 && mediaDTO.getDeleteTime() != null && categorySet.contains(mediaDTO.getFileCategory()))
                        || (status == 1 && mediaDTO.getDeleteTime() == null && categorySet.contains(mediaDTO.getFileCategory()))) {
                    mediaDTOS.add(mediaDTO);
                }
            }
        }

        Integer totalNum = mediaDTOS.size();
        Integer maxNum = (int) Math.ceil((double) totalNum / pageSize);

        if (curPage > maxNum) {
            curPage = maxNum;
        }
        curPage = Math.max(curPage - 1, 0);

        Collections.sort(mediaDTOS, (o1, o2) -> o1.getCreateTime().before(o2.getCreateTime()) ? 1 : -1);

//        List<MediaDTO> data = new ArrayList(mediaDTOS);

        PageableMedia result = new PageableMedia();
        result.setData(mediaDTOS.subList(curPage * pageSize, Math.min((curPage + 1) * pageSize, totalNum)));
        result.setCurrentPage(curPage + 1);
        result.setPageSize(pageSize);
        result.setPageNum(maxNum);

        return ResultVOUtil.success(result);

//        PageableMedia result = mediaService.findAllMediaByCondition(curPage, pageSize, categories, status);
//
//        return ResultVOUtil.success(result);
    }

}
