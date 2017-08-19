package com.example.suhail.loginattempt1.Models;

import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interfaces.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vidur on 8/10/2017.
 */

public class RegisterStudent {
    private String stream;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String sClass;
    private Boolean isSuccess;
    private String sid;
    private List<String> optionals;
    private String password;

    public RegisterStudent(String name, String email, String address, String phone, String sClass, List<String> optionals
            , String stream, String password) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.sClass = sClass;
        this.optionals = optionals;
        this.stream = stream;
        this.password = password;



    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getSid() {
        return sid;
    }
}
