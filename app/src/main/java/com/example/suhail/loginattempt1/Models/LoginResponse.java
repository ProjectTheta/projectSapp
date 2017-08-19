package com.example.suhail.loginattempt1.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Suhail on 8/7/2017.
 */

public class LoginResponse {

    //class to handle responses from api call for login

    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}