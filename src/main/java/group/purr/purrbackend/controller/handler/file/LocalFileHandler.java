package group.purr.purrbackend.controller.handler.file;

import group.purr.purrbackend.controller.handler.FileHandler;
import group.purr.purrbackend.dto.MediaDTO;
import group.purr.purrbackend.entity.Media;
import group.purr.purrbackend.entity.Page;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.InternalServerErrorException;
import group.purr.purrbackend.utils.PurrUtils;
import group.purr.purrbackend.utils.ResultVOUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;

@Slf4j
public class LocalFileHandler implements FileHandler {

    private static final String SEP = File.separator;

    @Override
    public MediaDTO uploadFile(MultipartFile file, String rootPath) throws IOException {

        Assert.notNull(file, "Multipart file must not be null");

        // create upload folder
        String uploadFolderPath = tryCreateUploadPath(rootPath);

        // get file attributes
        MediaDTO fileAttributes = getFileAttributes(file);

        log.debug("File name: [{}], extension: [{}], file path: [{}]", fileAttributes.getName(),
                fileAttributes.getFileType(), uploadFolderPath);

        String url = uploadFolderPath + SEP + fileAttributes.getName() + "." + fileAttributes.getFileType();
        Path uploadFilePath = Paths.get(url);

        // Create directory
        Files.createFile(uploadFilePath);

        // Upload this file
        file.transferTo(uploadFilePath);

        // Generate thumbnail
        String thumbNailUrl = uploadFolderPath + SEP + fileAttributes.getName() + "_thumbnail." + fileAttributes.getFileType();
        Boolean thumbNailResult = generateThumbnail(thumbNailUrl, file.getInputStream());

        if(!thumbNailResult){
            log.error("生成缩略图出错");
            thumbNailUrl = "";
        }

        log.info("Uploaded file: [{}] to directory: [{}] successfully",
                fileAttributes.getName(), uploadFolderPath);

        fileAttributes.setUrl(url);
        fileAttributes.setThumbnailPath(thumbNailUrl);

        return fileAttributes;
    }

    @NonNull
    private String tryCreateUploadPath(String rootPath) {

        assert rootPath != null;
        String workDir = FileSystems.getDefault().getPath(rootPath).normalize().toAbsolutePath() + SEP;
        Calendar cal = Calendar.getInstance();
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String folderPath = workDir + year + SEP + month;

        // try to create
        Integer createFolder = PurrUtils.checkAndCreateFolder(folderPath);
        if (createFolder == -1) {
            throw new InternalServerErrorException(ResultEnum.CREATE_FOLDER_FAILED);
        }
        else if(createFolder == -2){
            throw new InternalServerErrorException(ResultEnum.NO_PERMISSION);
        }
        else {
            return folderPath;
        }
    }

    private MediaDTO getFileAttributes(MultipartFile file) throws IOException {

        String mimeType = null;
        try {
            mimeType = PurrUtils.getFileType(file);
        } catch (IOException ioException) {
            log.error("获取文件类型失败，file: [{}], msg: [{}]", file, ioException.getMessage());
            throw ioException;
        }
        if (mimeType == null) {
            throw new InternalServerErrorException(ResultEnum.PARSE_FILE_TYPE_FAILED);
        }
        String category = mimeType.split("/")[0];
        String type = "";
        if(mimeType.length()>1){
            type = mimeType.split("/")[1];
        }

        String fileName = PurrUtils.getUniqueKey();

        Long bytes = file.getSize();
        String size = String.valueOf(bytes);

        Integer height = 0;
        Integer width = 0;

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage != null) {
                width = bufferedImage.getWidth();
                height = bufferedImage.getHeight();
            }
        }
        catch (IOException ioException){
            log.error("获取图片长宽失败， file： [{}]", fileName);
        }

        MediaDTO result = new MediaDTO();
        result.setFileType(type);
        result.setFileCategory(category);
        result.setHost(0);
        result.setName(fileName);
        result.setSize(size);
        result.setImageHeight(height);
        result.setImageWidth(width);
        return result;
    }

    private Boolean generateThumbnail(String thumbNailUrl, InputStream fileInputStream) throws IOException {

        String absoluteUrl = FileSystems.getDefault().getPath(thumbNailUrl).normalize().toAbsolutePath().toString();

        try {
            boolean thumbnailSuccess = PurrUtils.generateThumbNail(absoluteUrl, fileInputStream);
            if (!thumbnailSuccess) {
                log.error("生成缩略图异常，请查看系统日志");
                return false;
            }
        } catch (IOException ioException) {
            log.error("生成缩略图异常 [{}]", ioException.getMessage());
            return false;
        }

        return true;
    }
}
