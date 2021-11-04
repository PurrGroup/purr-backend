package group.purr.purrbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuItemDTO {

    private Long ID;

    private String name;

    private String url;

    private String icon;

    private String target;

    private Integer isParent;

    private List<SubMenuItemDTO> subMenuItems;
}
