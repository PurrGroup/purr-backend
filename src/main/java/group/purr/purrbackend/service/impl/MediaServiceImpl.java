package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.entity.Media;
import group.purr.purrbackend.repository.MediaRepository;
import group.purr.purrbackend.service.MediaService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    /**
     * Thumbnail width.
     */
    private static final int THUMB_WIDTH = 256;

    /**
     * Thumbnail height.
     */
    private static final int THUMB_HEIGHT = 256;

    @Autowired
    MediaRepository mediaRepository;

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
    public boolean generateThumbNail(String thumbUrl, String type, InputStream originalFile) throws IOException {
        // TODO refactor this: if image is ico ext. extension
        BufferedImage thumbImage = ImageIO.read(originalFile);
        Assert.notNull(thumbImage, "Image must not be null");
        Assert.notNull(thumbUrl, "Thumb path must not be null");

        Path thumbPath = Paths.get(thumbUrl);

        boolean result = false;
        // Create the thumbnail
        try {
            Files.createFile(thumbPath);
            // Convert to thumbnail and copy the thumbnail
            log.debug("Trying to generate thumbnail: [{}]", thumbPath);
            Thumbnails.of(thumbImage).size(THUMB_WIDTH, THUMB_HEIGHT).keepAspectRatio(true)
                    .toFile(thumbPath.toFile());
            log.info("Generated thumbnail image, and wrote the thumbnail to [{}]",
                    thumbPath);
            result = true;
        } catch (Throwable t) {
            // Ignore the error
            log.warn("Failed to generate thumbnail: " + thumbPath, t);
        } finally {
            // Disposes of this graphics context and releases any system resources that it is using.
            thumbImage.getGraphics().dispose();
        }
        return result;


    }
}
