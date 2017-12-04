package com.example.suhail.loginattempt1.Fragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.suhail.loginattempt1.Activities.MainActivity;
import com.example.suhail.loginattempt1.Adapter.NoticeRecyclerViewAdapter;
import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interfaces.ApiInterface;
import com.example.suhail.loginattempt1.Models.notice_info;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Utils.SessionHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NoticeFragment extends Fragment {
    Button logout;
    private ProgressDialog mProgress;
    android.support.v7.widget.Toolbar toolbar;
    NoticeRecyclerViewAdapter noticeRecyclerViewAdapter;


    @Override
    public void onDestroyView() {


        super.onDestroyView();


        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_notice, container, false);


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.app_bar, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.notice_search));


        search(searchView);


        super.onCreateOptionsMenu(menu, inflater);
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText != null) {
                    noticeRecyclerViewAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//------------------------------------------------------Toolbar-

        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.appbar_notice);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notice");

        //----------------------------------------------


        mProgress = new ProgressDialog(getActivity());
        mProgress.setTitle("Loading Data");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        mProgress.setCanceledOnTouchOutside(false);


        if (isAdded()) {

            mProgress.show();

            ((MainActivity) getActivity()).enableBottomNav(false);


            final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerview_textnotice);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


            //<!----------------------------RETRIEVING PREVIOUSLY DOWNLOADED NOTICES -----------------------------------------------------------------------------


            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Gson gson = new Gson();
            String json = sharedPrefs.getString("notice_data", null);
            Type type = new TypeToken<ArrayList<notice_info>>() {
            }.getType();
            ArrayList<notice_info> arrayList = gson.fromJson(json, type);
            if (arrayList != null) {

                Log.d("Retrieving Notice", " Previous Data collected");

                recyclerView.setAdapter(new NoticeRecyclerViewAdapter(arrayList, R.layout.cardview, getActivity()));
                mProgress.dismiss();
                ((MainActivity) getActivity()).enableBottomNav(true);


            }

//--------------------------------------------------------------------------------------------------------------------------!>


            //<!------------------------API CALL FOR NEW NOTICES------------------------------------------------------------------

            mProgress.show();
            ((MainActivity) getActivity()).enableBottomNav(false);

            ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);

            String url = "https://api.myjson.com/bins/15xp7f";    // dummy URL

            Call<List<notice_info>> call = apiservice.getnotice(url);


            call.enqueue(new Callback<List<notice_info>>() {
                @Override
                public void onResponse(Call<List<notice_info>> call, Response<List<notice_info>> response) {

                    String message_code = response.message();
                    Log.d("Connection Status", "Successful");
                    Log.d("Response Code", message_code);
                  //  Toast.makeText(getActivity(), message_code, Toast.LENGTH_SHORT).show();

                    //--------------------!>>

                    List<notice_info> info_rec = response.body();

                    //<!------------------------------------------
                    //to avoid null point exception
                    if (info_rec == null) {
                        Toast.makeText(getActivity(), "nothing received", Toast.LENGTH_SHORT).show();
                        Log.d("Resonse", "Nothing Received");
                        //-----------------------------------------------!>


                    } else {

                        //<---------------------------------------------------Saving notices in memory----------------------------------------------------


                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        Gson gson = new Gson();

                        String json = gson.toJson(info_rec);

                        editor.putString("notice_data", json);
                        editor.commit();


                        //-------------------------------------------------------------------------------------------------------


                        //<!-----------------------------------------------------------------------
                        //setting up recycler view adapter

                        String Noofobjectsreceived ="No of objects received : " + info_rec.size();
                        Log.d("No of objects received",Noofobjectsreceived);

                        noticeRecyclerViewAdapter = new NoticeRecyclerViewAdapter(info_rec, R.layout.cardview, getActivity());
                        recyclerView.setAdapter(noticeRecyclerViewAdapter);

                        //--------------------------------------------------------------!>
                        mProgress.dismiss();
                        ((MainActivity) getActivity()).enableBottomNav(true);

                    }


                }

                @Override
                public void onFailure(Call<List<notice_info>> call, Throwable t) {
                    mProgress.dismiss();
                    ((MainActivity) getActivity()).enableBottomNav(true);

                }


            });


            logout = (Button) getActivity().findViewById(R.id.logou);
            logout.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "logging out", Toast.LENGTH_SHORT).show();
                            logout();

                        }


                    }
            );


        }

    }

    void logout() {
        SessionHelper sessionHelper = new SessionHelper(getActivity());
        sessionHelper.logOutUser();

    }


}









