package group.purr.purrbackend.exception.http;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.base.PurrHttpException;
import group.purr.purrbackend.utils.ResultVOUtil;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

public class InternalServerErrorException extends PurrHttpException {
    private static final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final MultiValueMap<String, String> headers = null;

    public InternalServerErrorException(ResultEnum result) {
        super(httpStatus, headers, ResultVOUtil.error(result));
    }
}
