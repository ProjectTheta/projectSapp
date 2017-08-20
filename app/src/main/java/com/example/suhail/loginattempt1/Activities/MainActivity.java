package com.example.suhail.loginattempt1.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Utils.SessionHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Context c;
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: In Main on create");
        setContentView(R.layout.activity_main);
        sessionHelper = new SessionHelper(MainActivity.this);
        Log.d(TAG, "onCreate: Session Helper initialised");
        checkSharedPreferences();
    }

    private void checkSharedPreferences() {
        if(!sessionHelper.isLoggedIn()) {
            startActivity(new Intent(c, LoginActivity.class));
            finish();
        }
    }


}