package com.example.suhail.loginattempt1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Suhail on 8/7/2017.
 */

public class LoginResponse {

    //class to handle responses from api call for login

    @SerializedName("Status")
    private String Status;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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