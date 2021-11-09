package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.BlogMetaConstants;
import group.purr.purrbackend.controller.handler.FileHandler;
import group.purr.purrbackend.controller.handler.file.LocalFileHandler;
import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.entity.BlogMeta;
import group.purr.purrbackend.entity.Media;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.InternalServerErrorException;
import group.purr.purrbackend.repository.BlogMetaRepository;
import group.purr.purrbackend.repository.MediaRepository;
import group.purr.purrbackend.service.MediaService;
import group.purr.purrbackend.utils.PurrUtils;
import group.purr.purrbackend.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import java.util.Date;

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


}
