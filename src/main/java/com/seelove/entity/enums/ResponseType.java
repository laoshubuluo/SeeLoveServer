package com.seelove.entity.enums;

/**
 * Created by liangjinzhu on 17/4/10.
 */
public enum ResponseType {
    SUCCESS(100, "success"),
    PARAM_ERROR(-2, "request data error"),
    ERROR(-1, "system error");

    private int code;
    private String message;

    ResponseType(int code, String message) {
        this.code = code;
        this.message = message;
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
