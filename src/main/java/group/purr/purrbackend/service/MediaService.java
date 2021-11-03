package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.MediaDTO;

import java.io.IOException;
import java.io.InputStream;

public interface MediaService {
    MediaDTO saveMedia(String url, String category, String type, Integer host, String originalFilename, String size, Integer height, Integer width, String thumbNailUrl);

    boolean generateThumbNail(String thumbNailPath, String type, InputStream originalFile) throws IOException;

}
