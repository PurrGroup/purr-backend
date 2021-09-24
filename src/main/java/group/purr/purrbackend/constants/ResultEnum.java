package group.purr.purrbackend.constants;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS("00000","一切正常"),
    USER_AUTH_FAILED("A0120","密码校验失败");












    private final String errorCode;

    private final String errorMsg;

    ResultEnum(String code, String message) {
        this.errorCode = code;
        this.errorMsg = message;
    }
}
