package com.example.suhail.loginattempt1.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.suhail.loginattempt1.Activities.LoginActivity;

import java.util.HashMap;

/**
 * Created by vidur on 8/14/2017.
 */

public class SessionHelper {

    SharedPreferences sharedPreferences;

    //Editor for shared preferences..
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LoginSharedPreferences";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    /**
     * Constructor to initialise
     * @param context
     */

    public SessionHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    /**
     * Create a login session once the user is successfully logged in
     * @param name
     * @param email
     */

    public void createLoginSession(String name, String email) {

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

    }

    /**
     * HashMap to get the user currently logged in
     * @return
     */

    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));

        // return user
        return user;

    }

    /**
     * To return login status
     * @return
     */
    public boolean isLoggedIn() {

        return sharedPreferences.getBoolean(IS_LOGIN, false);

    }

    /**
     * Check if no user logged in
     */

    public void checkLoginStatus() {
        if(!isLoggedIn()) {
            Intent i = new Intent(context, LoginActivity.class);

            //Clear all the previous activities if user not logged in
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }
    }

    public void logOutUser() {

        //Clearing all data from the shared preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }

}
