package com.example.suhail.loginattempt1.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.suhail.loginattempt1.Adapter.NoticeRecyclerViewAdapter;
import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interfaces.ApiInterface;
import com.example.suhail.loginattempt1.Models.notice_info;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Utils.SessionHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class NoticeFragment extends Fragment {
    Button logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_notice, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

           Log.d("Retrieving Notice"," Previous Data collected") ;

            recyclerView.setAdapter(new NoticeRecyclerViewAdapter(arrayList, R.layout.cardview, getActivity()));

        }

//--------------------------------------------------------------------------------------------------------------------------!>



      //<!------------------------API CALL FOR NEW NOTICES------------------------------------------------------------------

        ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);

        String url="https://api.myjson.com/bins/9m7sf";    // dummy URL

        Call<List<notice_info>> call = apiservice.getnotice(url);


        call.enqueue(new Callback<List<notice_info>>() {
            @Override
            public void onResponse(Call<List<notice_info>> call, Response<List<notice_info>> response) {

                String message_code = response.message();
                Log.d("Connection Status", "Successful");
                Log.d("Response Code", message_code);
                Toast.makeText(getActivity(), message_code, Toast.LENGTH_SHORT).show();

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

                   Toast.makeText(getActivity(), "No of objects received : " + info_rec.size(), Toast.LENGTH_SHORT).show();



                    recyclerView.setAdapter(new NoticeRecyclerViewAdapter(info_rec, R.layout.cardview, getActivity()));

                    //--------------------------------------------------------------!>

                }


            }

            @Override
            public void onFailure(Call<List<notice_info>> call, Throwable t) {

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


    void logout() {
        SessionHelper sessionHelper = new SessionHelper(getActivity());
        sessionHelper.logOutUser();

    }


}

