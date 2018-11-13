package com.one.pig.bean.enums;

/**
 * 结果枚举配置
 * -- 返回码和描述枚举
 * @ClassName: ResultEnum
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public enum ResultEnum {

    /*#################################返回码和描述的枚举(Start)#####################################*/

    UnknownError(00000, "未知错误"),
    SUCCESS(20000, "成功"),
    FAILURE(20001, "失败"),
    NotExist(20004, "不存在"),
    Existed(20005, "已存在"),
    ClientError(20400, "客户端错误"),
    ServerError(20500, "服务器错误");

    /*#################################返回码和描述的枚举(end)#######################################*/

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应结果
     */
    private String result;

    ResultEnum(Integer code, String result) {
        this.code = code;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer code() {
        return code;
    }

    public String result() {
        return result;
    }

}
