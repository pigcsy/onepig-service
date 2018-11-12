package com.anniu.ordercall.core.enums;

/**
 * 员工状态枚举
 * Created by csy on 2018/1/24.
 */
public enum UserStatusEnum {

    OK(1, "正常"), FREEZED(2, "冻结"), LEAVE(3, "离职"), DELETED(4, "删除");


    public int code;
    public String value;

    UserStatusEnum(int code, String value) {
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
        for (UserStatusEnum item : values()) {
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
        for (UserStatusEnum item : values()) {
            if (value.equals(item.getValue())) {
                return item.getCode();
            }
        }
        return null;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
