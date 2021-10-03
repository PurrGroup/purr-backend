package group.purr.purrbackend.exception.http;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.base.PurrHttpException;
import group.purr.purrbackend.utils.ResultVOUtil;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

/**
 * @author whalien
 * @since 2021-10-03 19:43
 */
public class IAMATeapotException extends PurrHttpException {
    private static final HttpStatus httpStatus = HttpStatus.I_AM_A_TEAPOT;
    private static final MultiValueMap<String, String> headers = null;

    public IAMATeapotException(ResultEnum result) {
        super(httpStatus, headers, ResultVOUtil.error(result));
    }
}
