package group.purr.purrbackend.controller;

import group.purr.purrbackend.exception.base.PurrHttpException;
import group.purr.purrbackend.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

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
        logger.info("Response failed: Http status code: " + e.getHttpStatus() + ", response data: " + e.getBody());
        return new ResponseEntity<ResultVO>(e.getBody(), (MultiValueMap<String, String>) e.getHeaders(), e.getHttpStatus());
    }
}