package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.IAMATeapotException;

public class WrongPasswordException extends IAMATeapotException {
    private static final ResultEnum result = ResultEnum.WRONG_PASSWORD;
    public WrongPasswordException() {
        super(result);
    }
}
