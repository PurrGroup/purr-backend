package group.purr.purrbackend.service;

import com.alibaba.fastjson.JSONArray;
import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.dto.PageableMedia;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface MediaService {
    MediaDTO saveMedia(String url, String category, String type, Integer host, String originalFilename, String size, Integer height, Integer width, String thumbNailUrl);

    MediaDTO upload(MultipartFile file) throws IOException;

    Long getTotalExceptDeleted();

    /**
     * 检查该媒体id是否存在,存在则返回详细信息
     * @param id
     * @return
     */
    MediaDTO checkIDAndGetDetail(String id);

    List<String> findAllCategory();

    List<MediaDTO> findAllMediaByPage(Integer pageNum, Integer pageSize);


    List<MediaDTO> findAllMedia();

//    PageableMedia findAllMediaByCondition(Integer curPage, Integer pageSize, JSONArray categorys, Integer status);
}
