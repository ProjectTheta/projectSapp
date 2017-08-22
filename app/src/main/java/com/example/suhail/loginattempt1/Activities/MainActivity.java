package com.example.suhail.loginattempt1.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.suhail.loginattempt1.Fragments.AcademicsFragment;
import com.example.suhail.loginattempt1.Fragments.AccountFragment;
import com.example.suhail.loginattempt1.Fragments.NoticeFragment;
import com.example.suhail.loginattempt1.Fragments.ResultFragment;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Utils.BottomNavHelper;
import com.example.suhail.loginattempt1.Utils.SessionHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Context c;
    SessionHelper sessionHelper;
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: In Main on create");
        setContentView(R.layout.activity_main);
        sessionHelper = new SessionHelper(MainActivity.this);
        Log.d(TAG, "onCreate: Session Helper initialised");
        checkSharedPreferences();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);

        /**
         * Setting up the Navigation Bar
         */
        BottomNavHelper.setUpNav(bottomNavigationView);

        enableTransaction(bottomNavigationView);



    }

    private void checkSharedPreferences() {
        if(!sessionHelper.isLoggedIn()) {
            startActivity(new Intent(c, LoginActivity.class));
            finish();
        }
    }

    /**
     * Enabling the OnClickListeners
     * @param mNav
     */

    public void enableTransaction(BottomNavigationView mNav) {

        Log.d(TAG, "enableTransaction: Transaction Enabled");
        mNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.ic_notice:
                        NoticeFragment noticeFragment = new NoticeFragment();
                        fragmentTransaction.replace(R.id.container, noticeFragment).commit();
                        return true;

                    case R.id.ic_academics:
                        AcademicsFragment academicsFragment = new AcademicsFragment();
                        fragmentTransaction.replace(R.id.container, academicsFragment).commit();
                        return true;

                    case R.id.ic_result:
                        ResultFragment resultFragment = new ResultFragment();
                        fragmentTransaction.replace(R.id.container, resultFragment).commit();
                        return true;

                    case R.id.ic_account:
                        AccountFragment accountFragment = new AccountFragment();
                        fragmentTransaction.replace(R.id.container, accountFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }


}