package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.IllegalFileException;
import group.purr.purrbackend.exception.WrongPasswordException;
import group.purr.purrbackend.service.MediaService;
import group.purr.purrbackend.utils.PurrUtils;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/media")
@Slf4j
public class MediaController {

    @Autowired
    private Environment env;

    @Autowired
    private MediaService mediaService;


    @PostMapping("/upload")
    public ResultVO uploadMedia(MultipartFile file) {

        if(file == null){
            throw new IllegalFileException();
        }

        String rootPath = env.getProperty("purr.media.path");
        Calendar cal = Calendar.getInstance();
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String folderPath = rootPath + "/" + year + "/" + month;
        PurrUtils.checkAndCreate(folderPath);

        String fileName = PurrUtils.getUniqueKey();
        String url = folderPath + "/" + fileName + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        Integer category = PurrUtils.getFileType(file.getOriginalFilename());

        Integer host = 0;

        //在服务器上新建
        PurrUtils.createFile(url);
        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(url);
            Files.write(path,bytes);
        }
        catch (IOException ignored){

        }

        //写数据库
        MediaDTO mediaDTO = mediaService.saveMedia(url, category, host, file.getOriginalFilename());

        return ResultVOUtil.success(true);
    }

}
