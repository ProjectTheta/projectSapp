package com.example.suhail.loginattempt1.Models;

/**
 * Created by vidur on 8/19/2017.
 */

public class ResponseForRegistrattion {

    /**
     * status : 0
     * message : Nothing Normal
     * code : 1
     */

    private int status;
    private String message;
    private int code;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
