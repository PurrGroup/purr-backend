package group.purr.purrbackend.controller;

import group.purr.purrbackend.exception.base.PurrHttpException;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理 BusinessException 异常
     *
     * @param httpServletRequest httpServletRequest
     * @param e                  异常
     * @return
     */
    @ExceptionHandler(value = PurrHttpException.class)
    public ResponseEntity<ResultVO> purrHttpExceptionHandler(HttpServletRequest httpServletRequest, PurrHttpException e) {
        logger.warn("Response failed: Http status code: " + e.getHttpStatus() + ", response data: " + e.getBody());
        return new ResponseEntity<ResultVO>(e.getBody(), e.getHeaders(), e.getHttpStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResultVO> purrBaseExceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        logger.error("Response failed: Http exception message: " + e.getMessage() + ", exception stack trace: " + Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<ResultVO>(ResultVOUtil.error("B0000", e.getMessage(), "服务器内部出现异常，请查看系统日志"), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}