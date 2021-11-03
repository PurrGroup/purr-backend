package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.IllegalFileException;
import group.purr.purrbackend.service.MediaService;
import group.purr.purrbackend.utils.PurrUtils;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
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
    public ResultVO uploadMedia(@RequestPart("file") MultipartFile file) {

        if(file == null){
            throw new IllegalFileException();
        }

        String rootPath = env.getProperty("purr.media.path");
        Calendar cal = Calendar.getInstance();
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String folderPath = rootPath + "/" + year + "/" + month;
        if(!PurrUtils.checkAndCreate(folderPath)){
            return ResultVOUtil.error(ResultEnum.NO_PERMISSION);
        }


        String mimeType = null;
        try{
            mimeType = PurrUtils.getFileType(file);
        }
        catch (IOException ioException){
            ResultVOUtil.error("A0706", ioException.getMessage(), ioException.getMessage());
        }
        if(mimeType == null){
            ResultVOUtil.error("A0706", "解析文件类型失败", "解析文件类型失败，请重试");
        }
        String category = mimeType.split("/")[0];
        String type = mimeType.split("/")[1];

        String fileName = PurrUtils.getUniqueKey();
        String url = folderPath + "/" + fileName + "." + type;
        if(!PurrUtils.checkWorkDir(url)){
            ResultVOUtil.error(ResultEnum.NO_PERMISSION);
        }

        Integer host = 0;

        //在服务器上新建
        try{
            PurrUtils.createFile(url);
        }
        catch (IOException ioException){
            ResultVOUtil.error("A0706", ioException.getMessage(), ioException.getMessage());
        }
        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(url);
            Files.write(path,bytes);
        }
        catch (IOException ioException){
            ResultVOUtil.error("A0706", ioException.getMessage(), "新建文件失败，请重试");
        }

        //写数据库
        double kbs = file.getSize() / 1024.0;
        String size = String .format("%.2f", kbs);
        Integer height = null;
        Integer width = null;
        String thumbNailUrl = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream()); // 通过MultipartFile得到InputStream，从而得到BufferedImage
            if (bufferedImage != null) {
                width = bufferedImage.getWidth();
                height = bufferedImage.getHeight();
            }
            thumbNailUrl = folderPath + "/" + fileName + "_thumbnail." + type;
            try {
                String absoluteUrl = FileSystems.getDefault().getPath(thumbNailUrl).normalize().toAbsolutePath().toString();
                boolean thumbnailSuccess = mediaService.generateThumbNail(absoluteUrl, type, file.getInputStream());
                if(!thumbnailSuccess){
                    ResultVOUtil.error("A0706", "生成缩略图失败", "生成缩略图失败");
                }
            }
            catch (IOException ioException){
                ResultVOUtil.error("A0706", "生成缩略图失败", "生成缩略图失败");
            }
        }
        catch (IOException ioException){
            return ResultVOUtil.error("A0706", ioException.getMessage(), ioException.getMessage());
        }

        MediaDTO mediaDTO = mediaService.saveMedia(url.substring(url.indexOf(".")+1), category, type, host, file.getOriginalFilename(), size, height, width, thumbNailUrl.substring(thumbNailUrl.indexOf(".")+1));

        return ResultVOUtil.success(mediaDTO);
    }

}
