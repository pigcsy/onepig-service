package com.anniu.ordercall.core.enums;

/**
 * 业务是否成功的日志记录
 *
 * @author csy
 */
public enum LogSucceedEnum {

    SUCCESS("成功"),
    FAIL("失败");

    String message;


    LogSucceedEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
