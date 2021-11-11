package group.purr.purrbackend.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonAlias;
import group.purr.purrbackend.constant.BlogMetaConstants;
import group.purr.purrbackend.controller.handler.FileHandler;
import group.purr.purrbackend.controller.handler.file.LocalFileHandler;
import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.dto.PageableMedia;
import group.purr.purrbackend.entity.BlogMeta;
import group.purr.purrbackend.entity.Media;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.InternalServerErrorException;
import group.purr.purrbackend.repository.BlogMetaRepository;
import group.purr.purrbackend.repository.MediaRepository;
import group.purr.purrbackend.service.MediaService;
import group.purr.purrbackend.utils.PurrUtils;
import group.purr.purrbackend.utils.ResultVOUtil;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.hibernate.dialect.MariaDBDialect;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    BlogMetaRepository blogMetaRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public MediaDTO saveMedia(String url, String category, String type, Integer host, String originalFilename, String size, Integer height, Integer width, String thumbNailUrl) {
        Media media = new Media();
        media.setFileCategory(category);
        media.setFileType(type);
        media.setHost(host);
        media.setName(originalFilename);
        media.setUrl(url);
        media.setSize(size);
        media.setImageHeight(height);
        media.setImageWidth(width);
        media.setThumbnailPath(thumbNailUrl);

        Date current = new Date();
        media.setCreateTime(current);

        mediaRepository.save(media);

        return modelMapper.map(media, MediaDTO.class);
    }


    @Override
    public MediaDTO upload(MultipartFile file) throws IOException {
        String host = blogMetaRepository.findBlogMetaByOptionKey(BlogMetaConstants.RESOURCES_HOST)
                .orElse(BlogMeta.builder()
                        .optionKey(BlogMetaConstants.RESOURCES_HOST)
                        .optionValue("0")
                        .build())
                .getOptionValue();

        if(host.equals("0")){
            FileHandler lfh = new LocalFileHandler();
            MediaDTO result = lfh.uploadFile(file);

            log.debug("file type: [{}], upload path: [{}]", result.getFileType(), result.getUrl());

            Date currentTime = new Date();
            result.setCreateTime(currentTime);

            // Save media
            Media media = modelMapper.map(result, Media.class);
            mediaRepository.save(media);

            return result;
        }

        throw new InternalServerErrorException(ResultEnum.NO_PROPER_FILE_HANDLER);
    }

    @Override
    public Long getTotalExceptDeleted() {
        return mediaRepository.countByDeleteTimeNull();
    }

    @Override
    public MediaDTO checkIDAndGetDetail(String id) {

        Media result = mediaRepository.findByID(Long.valueOf(id));
        if(result == null){
            return null;
        }
        MediaDTO dtoResult = modelMapper.map(result, MediaDTO.class);
        return dtoResult;
    }

    @Override
    public List<String> findAllCategory(){

        List<String> result = mediaRepository.findAllCategory();
        return result;

    }

    @Override
    public List<MediaDTO> findAllMediaByPage(Integer pageNum, Integer pageSize){
        Integer offset = pageNum * pageSize;
        List<Media> findResult = mediaRepository.findAllMediaExceptDeletedByPage(offset, pageSize);

        List<MediaDTO> result = new ArrayList<>();
        for(Media media : findResult){
            MediaDTO mediaDTO = modelMapper.map(media, MediaDTO.class);
            result.add(mediaDTO);
        }

        return result;
    }

    @Override
    public List<MediaDTO> findAllMedia(){
        List<Media> mediaList = mediaRepository.findAll();
        List<MediaDTO> result = new ArrayList<>();
        for(Media media: mediaList){
            result.add(modelMapper.map(media, MediaDTO.class));
        }
        return result;
    }
//    @Override
//    public PageableMedia findAllMediaByCondition(Integer curPage, Integer pageSize, JSONArray categorys, Integer status) {
//
//        String categoryCondition;
//        if(categorys.size()==0){
//            categoryCondition = "";
//        }
//        else{
//            categoryCondition = "";
//            for(Object obj : categorys){
//                categoryCondition = categoryCondition + "\"" + obj.toString() + "\",";
//            }
//            categoryCondition = categoryCondition.substring(0, categoryCondition.lastIndexOf(","));
//        }
//
//
//        Integer maxNum;
//        List<Media> medias;
////        Long total = 0l;
//        Long total = mediaRepository.countByOnlyCategory(categoryCondition);
//        if(categoryCondition.equals("")){
//            if(status == -1){
//                total = mediaRepository.countAll();
//
//                maxNum = ((int) Math.ceil( (double)total / pageSize)) - 1;
//
//                if ((curPage - 1) > maxNum) {
//                    curPage = maxNum;
//                }
//                else curPage = Math.max(curPage - 1, 0);
//                Integer offset = curPage * pageSize;
//
//                medias = mediaRepository.findAll(offset, pageSize);
//            }
//            else if(status == 0){
//                total = mediaRepository.countByDeleteTimeNotNull();
//
//                maxNum = ((int) Math.ceil( (double)total / pageSize)) - 1;
//
//                if ((curPage - 1) > maxNum) {
//                    curPage = maxNum;
//                }
//                else curPage = Math.max(curPage - 1, 0);
//                Integer offset = curPage * pageSize;
//
//                medias = mediaRepository.findAllMediaByDeleteTimeNotNull(offset, pageSize);
//            }
//            else{
//                total = mediaRepository.countByDeleteTimeNull();
//
//                maxNum = ((int) Math.ceil( (double)total / pageSize)) - 1;
//
//                if ((curPage - 1) > maxNum) {
//                    curPage = maxNum;
//                }
//                else curPage = Math.max(curPage - 1, 0);
//                Integer offset = curPage * pageSize;
//
//                medias = mediaRepository.findAllMediaByDeleteTimeNull(offset, pageSize);
//            }
//        }
//        else{
//            if(status == -1){
//                total = mediaRepository.countByOnlyCategory(categoryCondition);
//
//                maxNum = ((int) Math.ceil( (double)total / pageSize)) - 1;
//
//                if ((curPage - 1) > maxNum) {
//                    curPage = maxNum;
//                }
//                else curPage = Math.max(curPage - 1, 0);
//                Integer offset = curPage * pageSize;
//
//                medias = mediaRepository.findAllMediaByOnlyCategory(categoryCondition, offset, pageSize);
//            }
//            else if(status == 0){
//                total = mediaRepository.countByCategoryAndDeleteTimeNotNull(categoryCondition);
//
//                maxNum = ((int) Math.ceil( (double)total / pageSize)) - 1;
//
//                if ((curPage - 1) > maxNum) {
//                    curPage = maxNum;
//                }
//                else curPage = Math.max(curPage - 1, 0);
//                Integer offset = curPage * pageSize;
//
//                medias = mediaRepository.findAllMediaByCategoryAndDeleteTimeNotNull(categoryCondition, offset, pageSize);
//            }
//            else{
//                total = mediaRepository.countByCategoryAndDeleteTimeNull(categoryCondition);
//
//                maxNum = ((int) Math.ceil( (double)total / pageSize)) - 1;
//
//                if ((curPage - 1) > maxNum) {
//                    curPage = maxNum;
//                }
//                else curPage = Math.max(curPage - 1, 0);
//                Integer offset = curPage * pageSize;
//
//                medias = mediaRepository.findAllMediaByCategoryAndDeleteTimeNull(categoryCondition, offset, pageSize);
//            }
//        }
//
//        List<MediaDTO> mediaDTOS = new ArrayList<>();
//        for(Media media : medias){
//            MediaDTO mediaDTO = modelMapper.map(media, MediaDTO.class);
//            mediaDTOS.add(mediaDTO);
//        }
//
//        PageableMedia result = new PageableMedia();
//        result.setCurrentPage(curPage+1);
//        result.setPageNum(maxNum+1);
//        result.setData(mediaDTOS);
//        result.setPageSize(pageSize);
//        return result;
//    }
}
