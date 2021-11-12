package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.UnAuthorizedException;

public class RefreshTokenExpiredException extends UnAuthorizedException {
    private static final ResultEnum result = ResultEnum.REFRESH_TOKEN_EXPIRED;

    public RefreshTokenExpiredException() {
        super(result);
    }
}
