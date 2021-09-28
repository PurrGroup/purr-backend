package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.MenuDTO;
import group.purr.purrbackend.dto.MenuItemDTO;
import group.purr.purrbackend.dto.SubMenuItemDTO;
import group.purr.purrbackend.entity.Menu;

public interface MenuService {
    Menu createMenu(MenuDTO menuDTO);
    boolean createMenuItem(MenuItemDTO menuItemDTO, Long parentID);
    boolean createSubMenuItem(SubMenuItemDTO subMenuItemDTO, Long parentID);
}
