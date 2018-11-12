package com.anniu.ordercall.core.enums;

/**
 * 业务日志类型
 *
 * @author csy
 */
public enum BizLogTypeEnum {

    ALL(0, null),//全部日志
    BUSSINESS(1, "业务日志"),
    EXCEPTION(2, "异常日志");

    Integer code;

    String message;

    BizLogTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (BizLogTypeEnum bizLogTypeEnum : BizLogTypeEnum.values()) {
                if (bizLogTypeEnum.getCode().equals(value)) {
                    return bizLogTypeEnum.getMessage();
                }
            }
            return null;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
