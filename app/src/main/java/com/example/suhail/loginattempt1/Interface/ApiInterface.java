package com.example.suhail.loginattempt1.Interface;

/**
 * Created by Suhail on 8/7/2017.
 */


import com.example.suhail.loginattempt1.model.LoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("attempt/login")

    //in case of maps use this
    Call<LoginResponse> doLogin(@FieldMap(encoded = false) Map<String, String> params);

     /* to pass every param seprately use this ->
        Call<LoginResponse> doLogin(@Field("contact") String contact,
                                    @Field("password") String password);
    */
}

