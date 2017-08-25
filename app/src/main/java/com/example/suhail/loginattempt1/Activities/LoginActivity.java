package com.example.suhail.loginattempt1.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interfaces.ApiInterface;
import com.example.suhail.loginattempt1.Models.LoginStudent;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Models.LoginResponse;
import com.example.suhail.loginattempt1.Utils.SessionHelper;


import java.net.InetAddress;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//Test Commit
public class LoginActivity extends AppCompatActivity {


    /*
    Declarations
     */
    private ProgressDialog mProgress;
    private static final String TAG = "LoginActivity";
    TextView incorrect_contact;
    TextView incorrect_password;
    Context c = LoginActivity.this;
    TextView registerStudent;
    EditText contact;
    EditText password;
    Button bt_signin;
    SessionHelper sessionHelper;
    int contactistrue = 0;
    int passwordistrue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: In on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /*
        progress bar for login
         */
        mProgress = new ProgressDialog(LoginActivity.this);
        mProgress.setTitle("Logging you in...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        /*
        Initializing session helper
         */
        sessionHelper = new SessionHelper(LoginActivity.this);



        /*
        Inflating Views
         */
        contact = (EditText) findViewById(R.id.login_contact);
        password = (EditText) findViewById(R.id.login_password);
        bt_signin = (Button) findViewById(R.id.sign_in_button);


       /*
       Checking for previous session
        */
        checksharedpreferences();




        /*
        click listeners
         */
        signInButtonListner();
        registerbuttonclicklistner();
        contactListner();


//------------------------------------------------------------------------------------
        bt_signin.setEnabled(false);
        bt_signin.setBackgroundColor(getResources().getColor(R.color.grey));
//------------------------------------------------------------------------------------


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

    }
    //-------------Oncreate Ends Her-------------------------------------------------------------------------




    /*
    check for connection
     */

    public boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

    /*
    --------------------------------------------------------------  listeners Start------------------------------------------------

     */
    private void signInButtonListner() {


        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress.show();
                String stud_contact = contact.getText().toString();
                String stud_password = password.getText().toString();

                if(isInternetAvailable()==true)
                {
                    LoginAttempt(stud_contact, stud_password);
                }else
                {

                    mProgress.dismiss();
                    Toast.makeText(c, "No Internet", Toast.LENGTH_SHORT).show();
                    Toast.makeText(c, "Please connect to a network", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerbuttonclicklistner() {

        registerStudent = (TextView) findViewById(R.id.register_student);
        registerStudent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

                    }
                }
        );

    }


    void contactListner() {


        contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int f1 = contact.length();
                if (f1 != 10) {
                    setUpTextForWrongField(3);
                    contactistrue = 0;
                } else {

                    setUpTextForWrongField(4);
                    contactistrue = 1;
                }

                if (passwordistrue == 1 && contactistrue == 1) {

                    bt_signin.setEnabled(true);
                    bt_signin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                } else if (passwordistrue == 0 || contactistrue == 0) {
                    bt_signin.setEnabled(false);
                    bt_signin.setBackgroundColor(getResources().getColor(R.color.grey));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });

       password.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               int f2 = password.getText().length();



               if (f2 < 5) {
                   passwordistrue = 0;
                   setUpTextForWrongField(5);
               } else {
                   passwordistrue = 1;
                   setUpTextForWrongField(6);
               }

               if (passwordistrue == 1 && contactistrue == 1) {

                   bt_signin.setEnabled(true);
                   bt_signin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

               } else if (passwordistrue == 0 || contactistrue == 0) {
                   bt_signin.setEnabled(false);
                   bt_signin.setBackgroundColor(getResources().getColor(R.color.grey));

               }

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });



    }
//------------------------------------------------------Listners End-----------------------------------------------------------






    private void checksharedpreferences() {
        Log.d(TAG, "checksharedpreferences: Chcking Shared Preferences");
        boolean isLoggedIn = sessionHelper.isLoggedIn();
        if (isLoggedIn) {
            Log.d("inside if", "launching activity");
            startActivity(new Intent(c, MainActivity.class));
            finish();
        }
    }



    //-----------------------------------------------API CALL-----------------------------------------------------------------


    public void LoginAttempt(String contact, String password) {

        Log.d(TAG, "LoginAttempt: Attempting login");
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        LoginStudent student = new LoginStudent(contact, password);
        Call<LoginResponse> call = apiInterface.doLogin(student);
        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "onResponse: Response parsing");

                LoginResponse login_results = response.body();

                if (login_results == null) {

                    /*
                    dismiss progress bar
                    */
                    mProgress.dismiss();

                    Toast.makeText(c, "Server Error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onResponse: Got the response: " + login_results.getStatus());
                    if (login_results.getStatus() == 1) {
                        handleresponse(login_results.getContact(), login_results.getSid());
                    } else if (login_results.getCode() == 3) {
                        setUpTextForWrongField(1);
                        Log.d("Response received", "Wrong Conatct");
                    } else if (login_results.getCode() == 2) {
                        setUpTextForWrongField(2);
                        Log.d("Response received", "Wrong Password");
                    } else if (login_results.getCode() == 4) {
                        setUpTextForWrongField(0);
                        Log.d("Response received", "Wrong Password");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: Something went wrong: " + t.toString());

            }
        });

    }
//--------------------------------------------------------------------------------------------------------------------------------




    public void handleresponse(String contact, String sid) {

        Log.d(TAG, "handleresponse: Handling the Response");
        sessionHelper.createLoginSession(contact, sid);
        mProgress.dismiss();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

       }




       /*
       Setup text for wrong field
        */
       public void setUpTextForWrongField(int id) {

           /*
           dismiss progress bar
            */
           mProgress.dismiss();


           incorrect_contact = (TextView) findViewById(R.id.wrong_contact);
           incorrect_password = (TextView) findViewById(R.id.wrong_password);
        if (id == 1) {

            incorrect_contact.setText(R.string.incorrect_contact);
        } else if (id == 2)

        {


            incorrect_password.setText(R.string.incorrect_password);


        } else if (id == 3) {


            incorrect_contact.setText(R.string.wrong_phone_no);


        } else if (id == 4) {


            incorrect_contact.setText(null);


        } else if (id == 5) {


            incorrect_password.setText(R.string.small_pass);


        } else if (id == 0) {


            Toast.makeText(c, R.string.server_error, Toast.LENGTH_SHORT).show();


        } else if (id == 6) {


            incorrect_password.setText(null);


        } else {

            Log.d("In'setUpTextForWrongField'", "Id recived is other than 1or 2");
        }

    }


}


//-----------------------------------------end !!

