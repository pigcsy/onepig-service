package com.anniu.ordercall.core.enums;

/**
 * 处理结果
 *
 * @author csy
 */
public enum DealResultEnum {

    NO_PEOPLE(1, "无人接听"),
    SHUTDOWN(2, "已关机"),
    OUT_OF_SERVICE(3, "已停机"),
    EMPTY_NUMBER(4, "空号"),
    BUSY(5, "占线"),
    REJECT(6, "拒接"),
    NO_COMMUNICATION(7, "未完成有效沟通"),
    COMMUNICATION(8, "已沟通，无付费意愿"),
    COMMUNICATIONED(9, "已沟通，有付费意愿");

    Integer code;

    String value;

    DealResultEnum(Integer code, String value) {
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
        for (DealResultEnum item : values()) {
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
        for (DealResultEnum item : values()) {
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
