package group.purr.purrbackend.enumerate;

import lombok.Getter;

@Getter
public enum ResultEnum {

    /**
     * 请求成功
     */
    SUCCESS("00000", "一切正常", ""),

    /**
     * 用户密码校验失败
     */
    USER_AUTH_FAILED("A0120", "密码校验失败", "(oﾟvﾟ)ノ密码校验失败，请检查你的用户名和密码"),

    /**
     * 安装失败，系统已安装
     */
    ALREADY_INSTALLED("A1006", "系统已安装", "(・∀・(・∀・(・∀・*)？系统已经安装过啦"),

    /**
     * 卸载失败，系统已卸载
     */
    ALREADY_UNINSTALLED("A1007", "系统已卸载", "系统已经卸载好啦，期待你的下次使用"),

    DENIAL_OF_SERVICE("A1100", "请求不合法，服务器拒绝服务", "服务端出了点小问题，拒绝了你的请求"),

    DENIAL_OF_AUTHENTICATION("A1101", "请求频率过高，服务器拒绝服务", "请求频率过高，请稍后再试"),

    ACCESS_TOKEN_EXPIRED("A1102", "access-token过期", "登录凭证过期，请重新获取登录凭证"),

    REFRESH_TOKEN_EXPIRED("A1103", "登录凭证已过期", "登录凭证已经过期啦，请重新登录"),

    WRONG_PASSWORD("A1103", "密码错误", "密码输入错误，请重新输入密码"),

    UPLOAD_FILE_ERROR("A0700", "上传文件异常", "上传文件失败, 请重新上传"),

    ILLEGAL_CATEGORY("A0420", "类型参数不合法", "类型参数不符合要求，请重新请求"),

    /**
     *  code A0706 MediaController中动态构建一系列上传文件失败的错误
     */

    NO_PERMISSION("A0300", "没有权限进行操作", "请检查您的权限");


    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 与错误码对应的错误信息，开发人员用
     */
    private final String errorMsg;

    /**
     * 与错误码对应的用户提示，返回给用户
     */
    private final String tip;

    ResultEnum(String code, String message, String tip) {
        this.errorCode = code;
        this.errorMsg = message;
        this.tip = tip;
    }
}
