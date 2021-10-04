package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.BadRequestException;

/**
 * @author whalien
 * @since 2021-10-03 20:10
 */
public class DenialOfAuthentication extends BadRequestException {
    private static final ResultEnum result = ResultEnum.DENIAL_OF_AUTHENTICATION;

    public DenialOfAuthentication() {
        super(result);
    }
}
