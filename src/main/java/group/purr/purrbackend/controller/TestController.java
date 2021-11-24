package group.purr.purrbackend.controller;

import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.exception.http.InternalServerErrorException;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    @PostMapping("/gpa")
    public ResultVO test(@RequestBody JSONObject json) throws IOException {
        String grades = json.getString("grades");
        return ResultVOUtil.success(grades);
    }

}
