//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.one.pig.core.exception;


public interface ApiErrorMessage {
    default int getCode() {
        return ResponseCode.SERVER_ERROR.getCode();
    }

    String getMessage();
}
