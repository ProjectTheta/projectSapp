package com.example.suhail.loginattempt1.Models;

import android.util.Log;

import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interfaces.ApiInterface;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vidur on 8/10/2017.
 */

public class RegisterStudent {

    //New Commit

    private String name;
    private String email;
    private String contact;

    @SerializedName("class")
    private String sClass;
    private List<String> optionals;
    private String password;



    public RegisterStudent(String name, String email,String phone, String sClass, List<String> optionals, String password) {

        this.name = name;
        this.email = email;
        this.contact = phone;
        this.sClass = sClass;
        this.optionals = optionals;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public List<String> getOptionals() {
        return optionals;
    }

    public void setOptionals(List<String> optionals) {
        this.optionals = optionals;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
