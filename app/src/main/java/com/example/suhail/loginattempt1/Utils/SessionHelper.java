package com.example.suhail.loginattempt1.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.suhail.loginattempt1.Activities.LoginActivity;

import java.util.HashMap;

/**
 * Created by vidur on 8/14/2017.
 */

public class SessionHelper extends LoginActivity {

    SharedPreferences sharedPreferences;

    //Editor for shared preferences..
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LoginSharedPreferences";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_ID = "sid";

    // Email address (make variable public to access from outside)
    public static final String KEY_CONTACT = "contact";

    /**
     * Constructor to initialise
     * @param context
     */

    public SessionHelper(Context context) {
        this.context = context;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    /**
     *
     * @param contact
     * @param sid
     */

    public void createLoginSession(String contact, String sid) {

        editor.putString(KEY_CONTACT, contact);
        editor.putString(KEY_ID, sid);
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();

    }

    /**
     * HashMap to get the user currently logged in
     * @return
     */

    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();

        // user contact
        user.put(KEY_CONTACT, sharedPreferences.getString(KEY_CONTACT, null));

        // user id
        user.put(KEY_ID, sharedPreferences.getString(KEY_ID, null));

        // return user
        return user;

    }

    /**
     * To return login status
     * @return
     */

    public boolean isLoggedIn() {

       if(sharedPreferences!=null)
       {
            return sharedPreferences.getBoolean(IS_LOGIN,false);
        }
        return false;

    }


    /**
     * Check if no user logged in after starting main activity and if not logged in send to the login activity
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


    /**
     * While Signing out user
     */
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
