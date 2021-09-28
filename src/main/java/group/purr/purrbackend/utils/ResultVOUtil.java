package group.purr.purrbackend.utils;

import group.purr.purrbackend.enumerate.ResultEnum;
import group.purr.purrbackend.vo.ResultVO;

/**
 * @author 许启辰，何华
 * @since 2021/9/24
 * 工具类，用于封装请求成功和请求失败的操作
 */
public class ResultVOUtil {

    /**
     * 请求成功
     * @param data 成功返回的数据
     * @return 返回的响应体
     */
    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(data);
        resultVO.setSuccess(true);
        resultVO.setErrorCode(ResultEnum.SUCCESS.getErrorCode());
        resultVO.setErrorMsg(ResultEnum.SUCCESS.getErrorMsg());
        resultVO.setTip(ResultEnum.SUCCESS.getTip());
        return resultVO;
    }

    /**
     * 响应出错
     * @param error 错误码枚举量
     * @param data 可能返回的数据。当调用这个方法时，data不能为{@code null}，否则请使用 {@link ResultVOUtil#error(ResultEnum)}
     * @return 返回的响应体
     * @see ResultEnum
     */
    public static ResultVO error(ResultEnum error, Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccess(false);
        resultVO.setErrorCode(error.getErrorCode());
        resultVO.setErrorMsg(error.getErrorMsg());
        resultVO.setTip(error.getTip());
        resultVO.setData(data);
        return resultVO;
    }

    /**
     * 响应出错，不返回数据
     * @param error 错误码枚举量
     * @return 返回给前端的对象
     */
    public static ResultVO error(ResultEnum error){
        return ResultVOUtil.error(error, null);
    }
}
