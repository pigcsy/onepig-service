package com.one.pig.core.enums;

/**
 * 是否呼通
 *
 * @author csy
 */
public enum CallResultEnum {

    CONNECT(1, "已呼通"),

    NOCONNECT(2, "未呼通");

    Integer code;

    String value;

    CallResultEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据code码返回详细信息
     *
     * @param code
     * @return
     */
    public static String getValueByCode(int code) {
        for (CallResultEnum item : values()) {
            if (item.getCode() == code) {
                return item.getValue();
            }
        }
        return null;
    }

    /**
     * 根据详细信息返回code码
     *
     * @param value
     * @return
     */
    public static Integer getCodeByValue(String value) {
        for (CallResultEnum item : values()) {
            if (value.equals(item.getValue())) {
                return item.getCode();
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
