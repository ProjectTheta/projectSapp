package com.example.suhail.loginattempt1.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
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
    Context c = MainActivity.this;
    SessionHelper sessionHelper;
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;
    int i = 0;

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
        if (!sessionHelper.isLoggedIn()) {
            startActivity(new Intent(c, LoginActivity.class));
            finish();
        }
    }


    public void enableBottomNav(Boolean b)
    {
        if(b)
        {bottomNavigationView.setEnabled(true);}
        else
        {
            bottomNavigationView.setEnabled(false);
        }

    }



    /**
     * Enabling the OnClickListeners
     *
     * @param mNav
     */

    public void enableTransaction(BottomNavigationView mNav) {


        Log.d(TAG, "enableTransaction: Transaction Enabled");
        mNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);


                switch (item.getItemId()) {
                    case R.id.ic_notice:


                        NoticeFragment noticeFragment = new NoticeFragment();

                        if (currentFragment == null)

                        {
                            Log.d("stsus", "nullfrgmnt");


                            fragmentTransaction.replace(R.id.container, noticeFragment).commit();
                            return true;


                        }
                        if (!(currentFragment instanceof NoticeFragment)) {


                            Toast.makeText(c, currentFragment.getId(), Toast.LENGTH_SHORT).show();
                            fragmentTransaction.replace(R.id.container, noticeFragment).commit();
                            return true;
                        } else {
                            return false;
                        }


                    case R.id.ic_academics:
                        AcademicsFragment academicsFragment = new AcademicsFragment();
                        if (!(currentFragment instanceof AcademicsFragment)) {
                            fragmentTransaction.replace(R.id.container, academicsFragment).commit();
                            return true;
                        } else {
                            return false;
                        }


                    case R.id.ic_result:
                        ResultFragment resultFragment = new ResultFragment();
                        if (!(currentFragment instanceof ResultFragment)) {
                            fragmentTransaction.replace(R.id.container, resultFragment).commit();
                            return true;
                        } else {
                            return false;
                        }

                    case R.id.ic_account:

                        AccountFragment accountFragment = new AccountFragment();

                        if (!(currentFragment instanceof AccountFragment)) {
                            fragmentTransaction.replace(R.id.container, accountFragment).commit();

                            return true;

                        } else {
                            return false;
                        }
                }


                return false;
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {

            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}