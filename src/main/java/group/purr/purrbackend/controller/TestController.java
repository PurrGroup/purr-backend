package group.purr.purrbackend.controller;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.InternalServerErrorException;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    @PostMapping("/testExp")
    public ResultVO test(Integer i) throws IOException {
        if(i == 1){
            throw new InternalServerErrorException(ResultEnum.ACCESS_TOKEN_EXPIRED);
        }
        if(i == 2){
            throw new IOException();
        }
        return null;
    }

}
