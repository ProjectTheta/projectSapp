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
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Models.LoginResponse;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Test Commit
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    Context c = LoginActivity.this;
    TextView registerStudent;
    EditText user_name;
    EditText password;
    String[] para=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: In on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


      /*
       ----------------------------------------------------------------------------------
       */

        user_name = (EditText) findViewById(R.id.login_user_name);
        password = (EditText) findViewById(R.id.login_password);
        para = getsignin_para(user_name, password);//to change edittext to string


        ClickListner(para[0], para[1]);            //LoginActivity button click listner

      /*
        ------------------------------------------------------------------------------
         */





      /*
      --------------------------------------------------------------------------------------
      new user? sing up first click listner
       */
        newUserClickListner();
       /*
       -------------------------------------------------------------------------------------
        */

    }


    public void newUserClickListner() {
        registerStudent = (TextView) findViewById(R.id.register_student);
        registerStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating to new Activity");
                startActivity(new Intent(c, RegisterActivity.class));
                finish();
            }
        });


    }


    //method to get username and password in string format
    public String[] getsignin_para(EditText username, EditText password) {
        Log.d(TAG, "getsignin_para: Fetching Parameters");
        String muser_name;
        muser_name = username.getText().toString();
        String mpassword;
        mpassword = password.getText().toString();

        String[] returnstring = {muser_name, mpassword};
        return returnstring;
    }


    public void ClickListner(final String contact, final String password) {
        Button Login_button = (Button) findViewById(R.id.sign_in_button);
        //LoginActivity button click
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

        Log.d(TAG, "LoginAttempt: Attempting login");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        /*
        put parameters in map to be passed to api call here
         */
        Map<String, String> map = new HashMap<String, String>();

        /*
        para_1 contact
         */
        map.put("contact", contact);
        /*
        para_2 password
         */

        map.put("password", password);

        /*
        call to api
         */

        Call<LoginResponse> call = apiInterface.doLogin(map); //call

        call.enqueue(new Callback<LoginResponse>() {          //putting in q

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                String message=response.message();
                int StatusCode = response.code();

                Log.d("Login_Response_Code", String.valueOf(StatusCode));
                Log.d("Login_Response_Message",message);


                LoginResponse login_results = response.body();




               /*
               ------------------------------------------------------------------------------
               to handle null point exception
                */
                if (login_results == null) {
                    Log.d("Login_Response : ", "Nothing received");
                    Toast.makeText(LoginActivity.this, "Something went wrong, try again later", Toast.LENGTH_LONG).show();

               /*
                    -------------------------------------------------------------------------------
                     */

                } else

                {
                    Toast.makeText(LoginActivity.this, StatusCode + login_results.getStatus() + login_results.getCode() + login_results.getMessage(), Toast.LENGTH_SHORT).show();


                    handleresponse(login_results.getMessage(), login_results.getStatus(), login_results.getCode());

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.d("Login_Failure_Code",t.toString());
                Toast.makeText(LoginActivity.this, "Something went wrong, try again later/n" + t.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void handleresponse(String message_rec, String status_rec, int code_rec) {
        Log.d(TAG, "handleresponse: Handling the Response");
        String message = message_rec;
        String status = status_rec;
        int code = code_rec;

        switch (code) {
             /*
                0 for suceesful LoginActivity
                2 for user_does_not_exist
                1 for user exist but incorrect password
             */


            case 0:

                /*
                  pass intent to main activity here  //successfull LoginActivity
                 */
                break;

            case 2:

                /*user_doesnot_exist
                  put code here
                */
                break;

            case 1:
                /*    password_incorrect
                      put code here
                 */
                break;
        }

    }

}