package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.MediaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface MediaService {
    MediaDTO saveMedia(String url, String category, String type, Integer host, String originalFilename, String size, Integer height, Integer width, String thumbNailUrl);

    MediaDTO upload(MultipartFile file) throws IOException;
}
