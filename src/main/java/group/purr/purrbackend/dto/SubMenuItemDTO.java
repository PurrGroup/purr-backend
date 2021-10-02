package group.purr.purrbackend.dto;

import lombok.Data;

@Data
public class SubMenuItemDTO {

    private Long ID;

    private String name;

    private String url;

    private String icon;

    private String target;

    private Long parentID;

}
