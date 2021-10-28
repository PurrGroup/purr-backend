package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.entity.Media;
import group.purr.purrbackend.repository.MediaRepository;
import group.purr.purrbackend.service.MediaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public MediaDTO saveMedia(String url, Integer category, Integer host, String originalFilename) {
        Media media = new Media();
        media.setCategory(category);
        media.setHost(host);
        media.setName(originalFilename);
        media.setUrl(url);

        Date current = new Date();
        media.setCreateTime(current);

        mediaRepository.save(media);

        return modelMapper.map(media, MediaDTO.class);
    }
}
