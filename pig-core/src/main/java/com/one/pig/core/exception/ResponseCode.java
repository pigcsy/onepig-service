//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.one.pig.core.exception;

public enum ResponseCode {
    OK(0, ""),
    SERVER_ERROR(500, "服务器繁忙，请稍候重试");

    private int code;
    private String message;

    private ResponseCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
