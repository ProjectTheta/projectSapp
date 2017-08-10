package com.example.suhail.loginattempt1.Activity;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interface.ApiInterface;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.model.LoginResponse;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText user_name = (EditText) findViewById(R.id.user_name);
        EditText password = (EditText) findViewById(R.id.password);
        String[] para = getsignin_para(user_name, password);//to change edittext to string
        ClickListner(para[0], para[1]);//login button click listner
    }


    public String[] getsignin_para(EditText username, EditText password) {
        String muser_name;
        muser_name = username.getText().toString();
        String mpassword;
        mpassword = password.getText().toString();

        String[] returnstring = {muser_name, mpassword};
        return returnstring;
    }

    public void ClickListner(final String contact, final String password) {
        Button Login_button = (Button) findViewById(R.id.sign_in_button);
        //login button click
        Login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginAttempt(contact, password);
                    }
                }
        );


    }

    public void LoginAttempt(final String contact, String password) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        //put parameters in map to be passed to api call here
        Map<String, String> map = new HashMap<String, String>();

        //para_1 contact
        map.put("contact", contact);
        //para_2 password
        map.put("password", password);

        //call to api
        Call<LoginResponse> call = apiInterface.doLogin(map); //call
        call.enqueue(new Callback<LoginResponse>() {          //putting in q
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int StatusCode = response.code();
                LoginResponse login_results = response.body();
                 //to show recieved response in toast
                Toast.makeText(login.this, StatusCode + login_results.getStatus() + login_results.getCode() + login_results.getMessage(), Toast.LENGTH_SHORT).show();

              handleresponse(login_results.getMessage(),login_results.getStatus() ,login_results.getCode());

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(login.this, "Failed" + t, Toast.LENGTH_LONG).show();
            }
        });

    }

     public  void handleresponse(String message_rec, String status_rec, int code_rec){
         String message=message_rec;
         String status=status_rec;
         int code=code_rec;

         switch (code){
             /* 0 for suceesful login
                2 for user_does_not_exist
                1 for user exist but incorrect password
             */


             case 0 : //pass intent to main activity here  //successfull login
                     break;
             case 2: //user_doesnot_exist
                     //put code here
                     break;
             case 1: //password_incorrect
                     //put code here
                      break;
         }

     }

}