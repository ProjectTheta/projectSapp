package com.example.suhail.loginattempt1.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.example.suhail.loginattempt1.Fragments.AcademicsFragment;
import com.example.suhail.loginattempt1.Fragments.AccountFragment;
import com.example.suhail.loginattempt1.Fragments.NoticeFragment;
import com.example.suhail.loginattempt1.Fragments.ResultFragment;
import com.example.suhail.loginattempt1.R;

import java.lang.reflect.Field;

/**
 * Created by vidur on 8/22/2017.
 */

public class BottomNavHelper {

    private static final String TAG = "BottomNavHelper";

    /**
     * Removing the animation of the bottom nav bar
     *
     * @param mNav
     */

    public static void setUpNav(BottomNavigationView mNav) {
        Log.d(TAG, "setUpNav: Setting up the view");
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mNav.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.d(TAG, "setUpNav: Unable to changeView");
        } catch (IllegalAccessException e) {
            Log.d(TAG, "Unable to change value of shift mode", e);
        }
    }
}
