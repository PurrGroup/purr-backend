package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.IAMATeapotException;

/**
 * @author whalien
 * @since 2021-10-03 19:46
 */
public class DenialOfServiceException extends IAMATeapotException {
    private static final ResultEnum result = ResultEnum.DENIAL_OF_SERVICE;

    public DenialOfServiceException() {
        super(result);
    }
}
