package com.anniu.ordercall.core.enums;

/**
 * 是否是菜单的枚举
 *
 * @author csy
 */
public enum IsMenuEnum {

    YES(1, "是"),
    NO(0, "不是");//不是菜单的是按钮

    int code;
    String message;

    IsMenuEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return null;
        } else {
            for (IsMenuEnum s : IsMenuEnum.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return null;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
