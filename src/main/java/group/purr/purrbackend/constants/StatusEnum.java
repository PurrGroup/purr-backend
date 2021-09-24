package group.purr.purrbackend.constants;

import group.purr.purrbackend.utils.ResultVOUtil;
import lombok.Getter;

@Getter
public enum StatusEnum {

    OK("200"),
    INTERNAL_SERVER_ERROR("500"),
    NOT_FOUND("404");





    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

}
