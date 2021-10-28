package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.dto.MenuDTO;
import group.purr.purrbackend.dto.MenuItemDTO;
import group.purr.purrbackend.dto.SubMenuItemDTO;
import group.purr.purrbackend.entity.*;
import group.purr.purrbackend.repository.*;
import group.purr.purrbackend.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    final
    MenuRepository menuRepository;

    final
    MenuItemRepository menuItemRepository;

    final
    SubMenuItemRepository subMenuItemRepository;

    final
    MenuContainRelationRepository menuContainRelationRepository;

    final
    MenuItemContainRelationRepository menuItemContainRelationRepository;

    final
    ModelMapper modelMapper;

    public MenuServiceImpl(MenuRepository menuRepository, MenuItemRepository menuItemRepository, SubMenuItemRepository subMenuItemRepository, MenuContainRelationRepository menuContainRelationRepository, MenuItemContainRelationRepository menuItemContainRelationRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
        this.subMenuItemRepository = subMenuItemRepository;
        this.menuContainRelationRepository = menuContainRelationRepository;
        this.menuItemContainRelationRepository = menuItemContainRelationRepository;
        this.modelMapper = modelMapper;
    }

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

    @Override
    public void deleteAll() {
        menuRepository.deleteAll();
        menuItemRepository.deleteAll();
        menuContainRelationRepository.deleteAll();

        subMenuItemRepository.deleteAll();
        menuItemContainRelationRepository.deleteAll();
    }

    @Override
    public MenuDTO getDefaultMenu() {
        Menu menu = menuRepository.getByIsDefault(1);

        return modelMapper.map(menu, MenuDTO.class);
    }

    @Override
    public List<MenuItemDTO> getMenuItemsByParent(Long menuId) {
        List<MenuContainRelation> relations = menuContainRelationRepository.findAllByMenuContainRelationKey_MenuID(menuId);
        List<MenuItemDTO> menuItems = new ArrayList<>();

        for (MenuContainRelation relation : relations){
            MenuItem menuItem = menuItemRepository.findByID(relation.getMenuContainRelationKey().getMenuItemID());
            menuItems.add(modelMapper.map(menuItem, MenuItemDTO.class));
        }

        return menuItems;
    }

    @Override
    public List<SubMenuItemDTO> getSubMenuItemsByParent(Long menuItemId) {
        List<MenuItemContainRelation> relations = menuItemContainRelationRepository.findAllByMenuItemContainRelationKey_MenuItemID(menuItemId);
        List<SubMenuItemDTO> subMenuItems = new ArrayList<>();

        for (MenuItemContainRelation relation : relations){
            SubMenuItem subMenuItem = subMenuItemRepository.findByID(relation.getMenuItemContainRelationKey().getSubMenuItemID());
            subMenuItems.add(modelMapper.map(subMenuItem, SubMenuItemDTO.class));
        }

        return subMenuItems;
    }
}
