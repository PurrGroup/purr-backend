package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.MediaDTO;

public interface MediaService {
    MediaDTO saveMedia(String url, Integer category, Integer host, String originalFilename);

}
