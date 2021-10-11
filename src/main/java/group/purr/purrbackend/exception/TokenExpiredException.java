package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.UnAuthorizedException;

public class TokenExpiredException extends UnAuthorizedException {
    private static final ResultEnum result = ResultEnum.TOKEN_EXPIRED;
    public TokenExpiredException() {
        super(result);
    }
}
