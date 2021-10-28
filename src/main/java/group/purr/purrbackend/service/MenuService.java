package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.MenuDTO;
import group.purr.purrbackend.dto.MenuItemDTO;
import group.purr.purrbackend.dto.SubMenuItemDTO;

import java.util.List;

public interface MenuService {
    MenuDTO createMenu(MenuDTO menuDTO);

    Long createMenuItem(MenuItemDTO menuItemDTO, Long parentID);

    Boolean createSubMenuItem(SubMenuItemDTO subMenuItemDTO, Long parentID);

    void deleteAll();

    MenuDTO getDefaultMenu();

    List<MenuItemDTO> getMenuItemsByParent(Long menuId);

    List<SubMenuItemDTO> getSubMenuItemsByParent(Long menuItemId);
}
