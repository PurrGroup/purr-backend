package group.purr.purrbackend.exception;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.BadRequestException;

public class AlreadyUninstalledException extends BadRequestException {
    private static final ResultEnum result = ResultEnum.ALREADY_UNINSTALLED;

    public AlreadyUninstalledException() {
        super(result);
    }
}
