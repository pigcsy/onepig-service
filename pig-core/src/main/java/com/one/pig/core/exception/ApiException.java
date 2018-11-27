//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.one.pig.core.exception;


public class ApiException extends FreeworkException {
    private int code;

    public ApiException(int code, String message) {
        super(message, new Object[0]);
        this.code = code;
    }

    public ApiException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage.getMessage(), new Object[0]);
        this.code = apiErrorMessage.getCode();
    }

    public ApiException(ApiErrorMessage apiErrorMessage, Object... resolveMessageArgs) {
        super(apiErrorMessage.getMessage(), resolveMessageArgs);
        this.code = apiErrorMessage.getCode();
    }

    public int getCode() {
        return this.code;
    }
}
