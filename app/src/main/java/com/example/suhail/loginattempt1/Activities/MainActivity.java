package com.example.suhail.loginattempt1.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.StrictMode;
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

import com.example.suhail.loginattempt1.Adapter.NoticeRecyclerViewAdapter;
import com.example.suhail.loginattempt1.Fragments.AcademicsFragment;
import com.example.suhail.loginattempt1.Fragments.AccountFragment;
import com.example.suhail.loginattempt1.Fragments.NoticeFragment;
import com.example.suhail.loginattempt1.Fragments.ResultFragment;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Utils.BottomNavHelper;
import com.example.suhail.loginattempt1.Utils.SessionHelper;

public class MainActivity extends AppCompatActivity {

    String Contact = null;
    private static final String TAG = "MainActivity";
    Context c = MainActivity.this;
    SessionHelper sessionHelper;
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;
    int i = 0;
    int isfromusersettigs;
    String key = "IsfromUserSettings";
    String value = null;
    String KeyContact = "alpha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Contact = getIntent().getStringExtra("Contact");

        if (Contact == null) {

            Contact = GetContact();

        } else {
            SaveContact(Contact);
        }
        isfromusersettigs = getIntent().getIntExtra("key1", 9);

        String data = String.valueOf(isfromusersettigs);
        Log.d("isfromusersettings", data);


        Log.d("Contact in Main", Contact);

        Log.d(TAG, "onCreate: In Main on create");
        setContentView(R.layout.activity_main);
        sessionHelper = new SessionHelper(MainActivity.this);
        Log.d(TAG, "onCreate: Session Helper initialised");
        checkSharedPreferences();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_bar);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        /**
         * Setting up the Navigation Bar
         */
        BottomNavHelper.setUpNav(bottomNavigationView);

        enableTransaction(bottomNavigationView);


        if (savedInstanceState == null && isfromusersettigs == 9) {

            NoticeFragment noticeFragment = new NoticeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, noticeFragment)
                    .commit();
        }

        //<--------------------------------------------------------------
        if (isfromusersettigs == 1 || isfromusersettigs == 2) {

            Bundle bundle = new Bundle();
            bundle.putString("Contact", Contact);

            AccountFragment accountFragment = new AccountFragment();
            accountFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            fragmentTransaction1.replace(R.id.container, accountFragment).commit();
        }

        //--------------->

    }

    private String GetContact() {

        SharedPreferences prefs = getSharedPreferences(KeyContact, MODE_PRIVATE);
        String restoredText = prefs.getString("Contact", null);
        if (restoredText != null) {
            String name = prefs.getString("Contact", "No name defined");//"No name defined" is the default value.
        }
        return restoredText;

    }

    private void SaveContact(String contact) {

        SharedPreferences.Editor editor = getSharedPreferences(KeyContact, MODE_PRIVATE).edit();
        editor.putString("Contact", contact);
        editor.apply();

    }

    private void checkSharedPreferences() {
        if (!sessionHelper.isLoggedIn()) {
            startActivity(new Intent(c, LoginActivity.class));
            finish();
        }
    }


    public void enableBottomNav(Boolean b) {
        if (b) {
            bottomNavigationView.setEnabled(true);
        } else {
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


                            //   Toast.makeText(c, currentFragment.getId(), Toast.LENGTH_SHORT).show();
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


                        Bundle bundle = new Bundle();
                        bundle.putString("Contact", Contact);

                        AccountFragment accountFragment = new AccountFragment();
                        accountFragment.setArguments(bundle);

// set Fragmentclass Arguments


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