package com.jess.arms.http;

/**
 * Created by czw on 2017/11/30.
 */
public class HttpException extends Exception {
    private int code;

    public HttpException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
