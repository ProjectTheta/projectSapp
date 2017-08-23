package com.example.suhail.loginattempt1.Interfaces;

/**
 * Created by Suhail on 8/7/2017.
 */


import com.example.suhail.loginattempt1.Models.LoginResponse;
import com.example.suhail.loginattempt1.Models.LoginStudent;
import com.example.suhail.loginattempt1.Models.RegisterStudent;
import com.example.suhail.loginattempt1.Models.ResponseForRegistrattion;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    /**
     *Login Client
     */


    @POST("attempt/login")
    Call<LoginResponse> doLogin(@Body LoginStudent loginStudent);

    @POST("create/student")
    Call<ResponseForRegistrattion> createStudent(@Body RegisterStudent registerStudent);

}

