package com.example.suhail.loginattempt1.Interfaces;

/**
 * Created by Suhail on 8/7/2017.
 */


import com.example.suhail.loginattempt1.Models.LoginResponse;
import com.example.suhail.loginattempt1.Models.LoginStudent;
import com.example.suhail.loginattempt1.Models.RegisterStudent;
import com.example.suhail.loginattempt1.Models.ResponseForRegistration;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {

    /**
     *Login Client
     */


    @POST("attempt/login")
    Call<LoginResponse> doLogin(@Body LoginStudent loginStudent);

    @POST("create/student")
    Call<ResponseForRegistration> createStudent(@Body RegisterStudent registerStudent);

}

