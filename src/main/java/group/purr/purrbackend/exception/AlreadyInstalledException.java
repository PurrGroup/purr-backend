package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.BadRequestException;

public class AlreadyInstalledException extends BadRequestException {
    private static final ResultEnum result = ResultEnum.ALREADY_INSTALLED;

    public AlreadyInstalledException() {
        super(result);
    }
}
