package com.example.suhail.loginattempt1.Models;

import java.util.List;

/**
 * Created by vidur on 8/10/2017.
 */

public class RegisterStudent {
    private String name;
    private String email;
    private String address;
    private String phone;
    private String sClass;
    private Boolean isSuccess;
    private String sid;
    private List<String> optionals;

    public RegisterStudent(String name, String email, String address, String phone, String sClass, List<String> optionals) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.sClass = sClass;
        this.optionals = optionals;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getSid() {
        return sid;
    }
}
