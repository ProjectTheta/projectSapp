package com.example.suhail.loginattempt1.Models;

/**
 * Created by vidur on 8/19/2017.
 */

public class LoginStudent {

    public LoginStudent(String contact, String password) {
        this.contact = contact;
        this.password = password;
    }

    private String contact;
    private String password;

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public String getPassword() {
        return password;
    }
}
