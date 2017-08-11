package com.example.suhail.loginattempt1.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suhail.loginattempt1.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    EditText et_name, et_email, et_password, et_address, et_mobile, et_class;
    Button bt_register;
    TextView tv_login;
    List<String> extraSubjects = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: In oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText) findViewById(R.id.register_user_name);
        et_email = (EditText) findViewById(R.id.register_email);
        et_password = (EditText) findViewById(R.id.register_password);
        et_address = (EditText) findViewById(R.id.register_address);
        et_mobile = (EditText) findViewById(R.id.register_mobile);
        et_class = (EditText) findViewById(R.id.register_class);
        bt_register = (Button) findViewById(R.id.register_button);
        tv_login = (TextView) findViewById(R.id.login_act);
        extraSubjects.add("Maths");
        extraSubjects.add("Physics");
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Register activation");
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();

                //registerStudent()
            }
        });
    }
}
