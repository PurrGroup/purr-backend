package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.MenuDTO;
import group.purr.purrbackend.dto.MenuItemDTO;
import group.purr.purrbackend.dto.SubMenuItemDTO;
import group.purr.purrbackend.entity.*;
import group.purr.purrbackend.repository.*;
import group.purr.purrbackend.service.MenuService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

    @Override
    public MenuDTO createMenu(MenuDTO menuDTO) {
        Menu menu = modelMapper.map(menuDTO, Menu.class);
        Menu saveMenu = menuRepository.saveAndFlush(menu);
        MenuDTO result = modelMapper.map(saveMenu, MenuDTO.class);
        return result;
    }

    @Override
    public Long createMenuItem(MenuItemDTO menuItemDTO, Long parentID) {
        MenuItem menuItem = modelMapper.map(menuItemDTO, MenuItem.class);
        MenuItem result = menuItemRepository.saveAndFlush(menuItem);

        MenuContainRelationKey menuContainRelationKey = new MenuContainRelationKey();
        menuContainRelationKey.setMenuID(parentID);
        menuContainRelationKey.setMenuItemID(result.getID());

        MenuContainRelation menuContainRelation = new MenuContainRelation();
        menuContainRelation.setMenuContainRelationKey(menuContainRelationKey);

        // 关联表插入对应record
        menuContainRelationRepository.save(menuContainRelation);

        return result.getID();
    }

    @Override
    public Boolean createSubMenuItem(SubMenuItemDTO subMenuItemDTO, Long parentID) {
        SubMenuItem subMenuItem = modelMapper.map(subMenuItemDTO, SubMenuItem.class);
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
