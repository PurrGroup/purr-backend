package group.purr.purrbackend.utils;

import group.purr.purrbackend.constants.ResultEnum;
import group.purr.purrbackend.constants.StatusEnum;
import group.purr.purrbackend.vo.ResultVO;

/**
 * @author 许启辰
 * @date 2021/9/24
 * 用于生成返回给前端的对象
 */
public class ResultVOUtil {

    /**
     * 请求成功
     * @param object 返回的数据
     * @return 返回给前端的对象
     */
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setStatus(StatusEnum.OK.getStatus());
        resultVO.setErrorCode(ResultEnum.SUCCESS.getErrorCode());
        resultVO.setErrorMsg(ResultEnum.SUCCESS.getErrorMsg());
        return resultVO;
    }

    /**
     * 请求出错
     * @param status http状态码枚举量
     * @param error 错误码枚举量
     * @param tip 返回给用户的错误提示
     * @return 返回给前端的对象
     */
    public static ResultVO error(StatusEnum status, ResultEnum error, String tip){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(status.getStatus());
        resultVO.setErrorCode(error.getErrorCode());
        resultVO.setErrorMsg(error.getErrorMsg());
        resultVO.setTip(tip);
        return resultVO;
    }

    /**
     * 请求出错，tip默认为空串
     * @param status http状态码枚举量
     * @param error 错误码枚举量
     * @return 返回给前端的对象
     */
    public static ResultVO error(StatusEnum status, ResultEnum error){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(status.getStatus());
        resultVO.setErrorCode(error.getErrorCode());
        resultVO.setErrorMsg(error.getErrorMsg());
        resultVO.setTip("");
        return resultVO;
    }
}
