package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.BadRequestException;

public class IllegalCategoryException extends BadRequestException {
    private static final ResultEnum result = ResultEnum.ILLEGAL_CATEGORY;

    public IllegalCategoryException() {
        super(result);
    }
}
