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
    private String sid;
    private String contact;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
