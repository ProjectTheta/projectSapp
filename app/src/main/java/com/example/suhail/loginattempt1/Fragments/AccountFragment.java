package com.example.suhail.loginattempt1.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.suhail.loginattempt1.Activities.LoginActivity;
import com.example.suhail.loginattempt1.Activities.MainActivity;
import com.example.suhail.loginattempt1.Activities.UserSettingsActivity;
import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interfaces.ApiInterface;
import com.example.suhail.loginattempt1.Models.LoginResponse;
import com.example.suhail.loginattempt1.Models.LoginStudent;
import com.example.suhail.loginattempt1.Models.student_info;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Utils.SessionHelper;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */

public class AccountFragment extends Fragment {


    private ProgressDialog mProgress;

    CircleImageView image_profile;
    TextView Email;
    TextView Contact_TextView;
    TextView Optionals;
    android.support.v7.widget.Toolbar toolbar;
    public String User_Contact = "Contact";
    String contact = null;

    public AccountFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.account, menu);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        contact = getArguments().getString(User_Contact);

        //    Toast.makeText(getActivity(), contact.toString(), Toast.LENGTH_SHORT).show();
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_account, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //------------------------------------------------------Toolbar-

        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.appbar_accout);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notice");

        //----------------------------------------------

        Email = (TextView) getActivity().findViewById(R.id.SetEmail);
        Contact_TextView = (TextView) getActivity().findViewById(R.id.SetContact);
        image_profile = (CircleImageView) getActivity().findViewById(R.id.image_profile);
        contact = getArguments().getString(User_Contact);


        mProgress = new ProgressDialog(getActivity());
        mProgress.setTitle("Loading Data...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        mProgress.show();

        LoadStudentData(contact);

    }

    void LoadStudentData(String contact) {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String LoginPara = contact;

        Call<student_info> call = apiInterface.getstudentinfo(LoginPara);

        // Toast.makeText(getActivity(), LoginPara, Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<student_info>() {
            @Override
            public void onResponse(Call<student_info> call, Response<student_info> response) {
                student_info info = response.body();

                if (info == null) {
                    Log.d("student_info", "Nothing received");
                } else {

                    //Toast.makeText(getActivity(), info.toString(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getActivity(),response.body().toString(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getActivity(), info.getName(), Toast.LENGTH_SHORT).show();

                    Log.d("response", response.body().toString());




                    Log.d("Response Strudent Call", "Data Recieved");
                    ResponseHandler(info);


                }


            }

            @Override
            public void onFailure(Call<student_info> call, Throwable t) {

                mProgress.dismiss();
                Log.d("FailedResponse", "failed", t);

                Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
            }

        });
    }

    void ResponseHandler(student_info info) {

        String default_imageurl = "http://www.kalingakusum.org/images/team/advisors.jpg";

        Email.setText(info.getEmail());
        Contact_TextView.setText(info.getContact());
        toolbar.setTitle(info.getName());
        mProgress.dismiss();

        Picasso.with(getActivity()).setLoggingEnabled(true);

        Picasso.with(getActivity()).load(default_imageurl).into(image_profile);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.notice_account)
        {
            Intent intent = new Intent(getActivity(), UserSettingsActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);


    }


}
