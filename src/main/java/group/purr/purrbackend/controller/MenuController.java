package group.purr.purrbackend.controller;

import group.purr.purrbackend.dto.MenuDTO;
import group.purr.purrbackend.dto.MenuItemDTO;
import group.purr.purrbackend.dto.SubMenuItemDTO;
import group.purr.purrbackend.service.MenuService;
import group.purr.purrbackend.utils.ResultVOUtil;
import group.purr.purrbackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Slf4j
public class MenuController {

    final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/default")
    public ResultVO getUsedMenu() {
        MenuDTO defaultMenu = menuService.getDefaultMenu();

        List<MenuItemDTO> menuItems = menuService.getMenuItemsByParent(defaultMenu.getID());

        for (MenuItemDTO menuItem : menuItems) {
            if (menuItem.getIsParent() == 0) continue;
            List<SubMenuItemDTO> subMenuItems = menuService.getSubMenuItemsByParent(menuItem.getID());
            menuItem.setSubMenuItems(subMenuItems);
        }

        return ResultVOUtil.success(menuItems);
    }
}
