package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.MenuDTO;
import group.purr.purrbackend.dto.MenuItemDTO;
import group.purr.purrbackend.dto.SubMenuItemDTO;
import group.purr.purrbackend.entity.*;
import group.purr.purrbackend.repository.*;
import group.purr.purrbackend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    SubMenuItemRepository subMenuItemRepository;

    @Autowired
    MenuContainRelationRepository menuContainRelationRepository;

    @Autowired
    MenuItemContainRelationRepository menuItemContainRelationRepository;

    @Override
    public Menu createMenu(MenuDTO menuDTO) {
        Menu menu = menuDTO.convertTo();
        Menu result = menuRepository.saveAndFlush(menu);

        return result;
    }

    @Override
    public boolean createMenuItem(MenuItemDTO menuItemDTO, Long parentID) {
        MenuItem menuItem = menuItemDTO.convertTo();
        MenuItem result = menuItemRepository.saveAndFlush(menuItem);

        MenuContainRelationKey menuContainRelationKey = new MenuContainRelationKey();
        menuContainRelationKey.setMenuID(parentID);
        menuContainRelationKey.setMenuItemID(result.getID());

        MenuContainRelation menuContainRelation = new MenuContainRelation();
        menuContainRelation.setMenuContainRelationKey(menuContainRelationKey);

        // 关联表插入对应record
        menuContainRelationRepository.save(menuContainRelation);

        return true;
    }

    @Override
    public boolean createSubMenuItem(SubMenuItemDTO subMenuItemDTO, Long parentID) {
        SubMenuItem subMenuItem = subMenuItemDTO.convertTo();
        SubMenuItem result = subMenuItemRepository.saveAndFlush(subMenuItem);

        MenuItemContainRelationKey menuItemContainRelationKey = new MenuItemContainRelationKey();
        menuItemContainRelationKey.setMenuItemID(parentID);
        menuItemContainRelationKey.setSubMenuItemID(result.getID());

        MenuItemContainRelation menuItemContainRelation = new MenuItemContainRelation();
        menuItemContainRelation.setMenuItemContainRelationKey(menuItemContainRelationKey);

        menuItemContainRelationRepository.save(menuItemContainRelation);

        return true;
    }
}
