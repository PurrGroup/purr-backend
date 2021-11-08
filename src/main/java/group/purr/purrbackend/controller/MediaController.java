package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.IllegalFileException;
import group.purr.purrbackend.exception.http.InternalServerErrorException;
import group.purr.purrbackend.service.MediaService;
import group.purr.purrbackend.utils.PurrUtils;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@RequestMapping("/api/media")
@Slf4j
public class MediaController {

    @Autowired
    private MediaService mediaService;


    @PostMapping("/upload")
    public ResultVO uploadMedia(@RequestPart("file") MultipartFile file) throws IOException {

        if (file == null) {
            throw new IllegalFileException();
        }

        MediaDTO uploadResult;
        try {
            uploadResult = mediaService.upload(file);
        }
        catch (IOException ioException){
            throw ioException;
        }

        return ResultVOUtil.success(uploadResult);
    }

}
