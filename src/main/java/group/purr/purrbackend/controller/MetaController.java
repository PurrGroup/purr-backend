package group.purr.purrbackend.controller;

import group.purr.purrbackend.service.MetaService;
import group.purr.purrbackend.service.impl.MetaServiceImpl;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/meta/")
@Slf4j
public class MetaController {

    final
    MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    /**
     * 判断系统是否已安装
     * @return 返回给前端一个对象，data字段表示是否已安装
     */
    @GetMapping("/isInstalled")
    public ResultVO isInstalled(){
        Boolean isInstalled = metaService.queryInstalled();
        return ResultVOUtil.success(isInstalled);
    }
}
