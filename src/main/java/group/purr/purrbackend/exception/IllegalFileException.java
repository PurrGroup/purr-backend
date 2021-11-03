package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.BadRequestException;

public class IllegalFileException  extends BadRequestException {
    private static final ResultEnum result = ResultEnum.UPLOAD_FILE_ERROR;

    public IllegalFileException() {
        super(result);
    }
}
