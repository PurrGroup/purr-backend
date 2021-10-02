package group.purr.purrbackend.exception.base;

import group.purr.purrbackend.vo.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

public class PurrHttpException extends PurrBaseException {
    protected HttpStatus httpStatus;
    protected MultiValueMap<String, String> headers;
    protected ResultVO body;

    public PurrHttpException(HttpStatus httpStatus, MultiValueMap<String, String> headers, ResultVO body) {
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.body = body;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public MultiValueMap<String, String> getHeaders() {
        return this.headers;
    }

    public ResultVO getBody() {
        return this.body;
    }
}
