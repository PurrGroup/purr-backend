package group.purr.purrbackend.vo;

import group.purr.purrbackend.utils.ResultVOUtil;
import lombok.Data;

/**
 * http请求返回的VO对象
 * @author 许启辰，何华
 * @since 2021/9/24
 */
@Data
public class ResultVO {

    /** 请求是否成功的标识量
     * 如果为true，表示请求成功，此时errorCode为"00000", errorMsg为"一切正常",
     * 见{@link group.purr.purrbackend.constants.ResultEnum}。tip为""，data为必选域。
     * 如果为false，表示请求失败，此时需要填充errorCode,  errorMsg和tip，data变为可选域
     * */
    private Boolean success;

    /** 错误码
     * @see group.purr.purrbackend.constants.ResultEnum
     * */
    private String errorCode;

    /** 错误信息
     * @see group.purr.purrbackend.constants.ResultEnum
     * */
    private String errorMsg;

    /** 用户提示
     * 注意，当{@code success}为false时，tip为必填项。
     * @see group.purr.purrbackend.constants.ResultEnum
     * */
    private String tip;

    /** 请求返回的数据 */
    private Object data;

}
