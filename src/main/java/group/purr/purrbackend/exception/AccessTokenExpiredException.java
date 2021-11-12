package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.UnAuthorizedException;

public class AccessTokenExpiredException extends UnAuthorizedException {
    private static final ResultEnum result = ResultEnum.ACCESS_TOKEN_EXPIRED;

    public AccessTokenExpiredException() {
        super(result);
    }
}
