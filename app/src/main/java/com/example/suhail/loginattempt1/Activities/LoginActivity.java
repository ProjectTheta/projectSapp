package com.example.suhail.loginattempt1.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interfaces.ApiInterface;
import com.example.suhail.loginattempt1.Models.LoginStudent;
import com.example.suhail.loginattempt1.Models.ResponseForRegistrattion;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Models.LoginResponse;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//Test Commit
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    Context c = LoginActivity.this;
    TextView registerStudent;
    EditText contact;
    EditText password;
    Button bt_signin;
    String[] para=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: In on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contact = (EditText) findViewById(R.id.login_contact);
        password = (EditText) findViewById(R.id.login_password);
        registerStudent = (TextView) findViewById(R.id.register_student);
        bt_signin = (Button) findViewById(R.id.sign_in_button);

        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stud_contact = contact.getText().toString();
                String stud_password = password.getText().toString();
                LoginAttempt(stud_contact, stud_password);
            }
        });




    }






    public void LoginAttempt(String contact, String password) {

        Log.d(TAG, "LoginAttempt: Attempting login");
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        LoginStudent student = new LoginStudent(contact, password);
        Call<ResponseForRegistrattion> call = apiInterface.doLogin(student);
        call.enqueue(new Callback<ResponseForRegistrattion>() {

            @Override
            public void onResponse(Call<ResponseForRegistrattion> call, Response<ResponseForRegistrattion> response) {
                Log.d(TAG, "onResponse: Response parsing");

                ResponseForRegistrattion login_results = response.body();

                if (login_results == null) {
                    Toast.makeText(c, "Server Error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onResponse: Got the response: " + login_results.getMessage());
                    if (login_results.getStatus() == 1) {
                        //handleresponse(login_results.getContact(), login_results.getSid());
                        Toast.makeText(c, "Login done", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Some error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseForRegistrattion> call, Throwable t) {

                Log.d(TAG, "onFailure: Something went wrong: " + t.toString());

            }
        });

    }

    public void handleresponse(String contact, String sid) {

        Log.d(TAG, "handleresponse: Handling the Response");

    }

}