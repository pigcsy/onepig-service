//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.one.pig.core.exception;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class FreeworkException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 2685287081293576410L;
    private String message;
    private boolean runtime;

    public FreeworkException() {
    }

    public FreeworkException(String message, Object... args) {
        this.message = message;
        this.resolveMessage(args);
        this.runtime = false;
    }

    public FreeworkException(Throwable e) {
        super(e);
        this.message = StringUtils.join(new String[]{e.getClass().getName(), " - ", e.getMessage()});
        this.runtime = true;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public boolean isRuntime() {
        return this.runtime;
    }

    private void resolveMessage(Object[] args) {
        if (!StringUtils.isBlank(this.message) && args != null && args.length >= 1) {
            this.message = String.format(this.message, args);
        }
    }
}
