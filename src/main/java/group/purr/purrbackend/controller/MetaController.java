package group.purr.purrbackend.controller;

import com.alibaba.fastjson.JSONObject;
import group.purr.purrbackend.dto.AuthorDTO;
import group.purr.purrbackend.service.AuthorService;
import group.purr.purrbackend.service.MetaService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/meta")
@Slf4j
public class MetaController {

    final
    MetaService metaService;

    final
    AuthorService authorService;

    public MetaController(MetaService metaService, AuthorService authorService) {
        this.metaService = metaService;
        this.authorService = authorService;
    }

    /**
     * 判断系统是否已安装
     *
     * @return 返回给前端一个对象，data字段表示是否已安装
     */
    @GetMapping("/isInstalled")
    public ResultVO isInstalled() {
        Boolean isInstalled = metaService.queryInstalled();
        return ResultVOUtil.success(isInstalled);
    }

    @PutMapping("/apiUrl")
    public ResultVO updateApiUrl(@RequestBody JSONObject jsonObject){
        String apiUrl = jsonObject.getString("apiUrl");
        metaService.updateApiUrl(apiUrl);
        return ResultVOUtil.success(true);
    }

    @GetMapping("/profile")
    public ResultVO getProfile(){
        AuthorDTO authorDTO = authorService.getProfile();
        return ResultVOUtil.success(authorDTO);
    }
}
