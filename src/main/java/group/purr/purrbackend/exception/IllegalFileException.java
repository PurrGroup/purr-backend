package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.BadRequestException;

public class IllegalFileException  extends BadRequestException {
    private static final ResultEnum result = ResultEnum.ILLEGAL_FILE;

    public IllegalFileException() {
        super(result);
    }
}
