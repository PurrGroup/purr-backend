package group.purr.purrbackend.vo;

import group.purr.purrbackend.utils.ResultVOUtil;
import lombok.Data;

/**
 * http请求返回的最外层对象
 * @author 许启辰
 * @date 2021/9/24
 */
@Data
public class ResultVO {
    /** http请求码 */
    private String status;
    /** 错误码 */
    private String errorCode;
    /** 错误信息 */
    private String errorMsg;
    /** 用户提示 */
    private String tip;
    /** 返回数据 */
    private Object data;

}
