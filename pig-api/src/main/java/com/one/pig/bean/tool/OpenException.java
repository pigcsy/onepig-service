package com.one.pig.bean.tool;

import com.one.pig.bean.enums.ResultEnum;
import com.one.pig.core.exception.ApiException;

/**
 * 扩展处理子异常继承的基类
 *
 * @author 花花
 * @ClassName: OpenException
 * @DetaTime 2018-03-13 09:48:32
 */
public class OpenException extends ApiException {

    private static final long serialVersionUID = 1L;

    private ResultEnum retEnum;

    public OpenException(ResultEnum retCode) {
        super(retCode.getCode(), retCode.getResult());
        this.retEnum = retCode;
    }

    public OpenException(String message, ResultEnum retCode) {
        super(retCode.getCode(), message);
        this.retEnum = retCode;
    }

    public OpenException(String message) {
        super(ResultEnum.UnknownError.getCode(), message);
        this.retEnum = ResultEnum.UnknownError;
    }

    public ResultEnum getRetCode() {
        return retEnum;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [retCode=" + retEnum.getCode() + ", message=" + getMessage() + "]";
    }

}
