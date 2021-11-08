package group.purr.purrbackend.controller.handler;

import group.purr.purrbackend.dto.MediaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileHandler {
    MediaDTO uploadFile(MultipartFile file, String rootPath) throws IOException;
}
