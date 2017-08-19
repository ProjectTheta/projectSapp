package com.example.suhail.loginattempt1.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suhail.loginattempt1.ApiClient.ApiClient;
import com.example.suhail.loginattempt1.Interfaces.ApiInterface;
import com.example.suhail.loginattempt1.Models.RegisterStudent;
import com.example.suhail.loginattempt1.Models.ResponseForRegistrattion;
import com.example.suhail.loginattempt1.R;
import com.example.suhail.loginattempt1.Utils.SessionHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//Testing Branches.....
//Dummy Commit.....
//Another Commit....

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    EditText et_name, et_email, et_password, et_mobile, et_optionals;
    RadioGroup radioGroup;
    Context c = RegisterActivity.this;
    LinearLayout ll_check;
    RadioButton rb_non_med, rb_med, rb_arts, rb_commerce;
    Button bt_register;
    Spinner sp_class;
    String student_class;
    TextView tv_login;
    String Stream;
    List<String> optionals = new ArrayList<String>();
    List<String> classList = new ArrayList<String>();
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: In oncreate");
        Context c = RegisterActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionHelper = new SessionHelper(c);
        sp_class = (Spinner) findViewById(R.id.class_spinner);
        radioGroup = (RadioGroup) findViewById(R.id.stream_group);


        et_name = (EditText) findViewById(R.id.register_user_name);
        et_email = (EditText) findViewById(R.id.register_email);
        et_password = (EditText) findViewById(R.id.register_password);
        et_mobile = (EditText) findViewById(R.id.register_mobile);
        bt_register = (Button) findViewById(R.id.register_button);
        tv_login = (TextView) findViewById(R.id.login_act);

        classList.add("1");
        classList.add("2");
        classList.add("3");
        classList.add("4");
        classList.add("5");
        classList.add("6");
        classList.add("7");
        classList.add("8");
        classList.add("9");
        classList.add("10");
        classList.add("11");
        classList.add("12");

        /**
         * Setting up the Spinner
         */

        setUpSpinner(classList, c);

        setClickListener();

        /**
         * -------------------------------------------------------------------------------------------------------
         */

        /**
         * setting up radio buttons
         */


        /**
         * ------------------------------------------------------------------------------------------------------
         */

        registerbuttonlistner();


        tv_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();

                    }
                }
        );


    }

    /**
     * Register the student with the entered credentials
     */

    private void registerStudent() {

        Log.d(TAG, "registerStudent: Registering Student");
        String name = et_name.getText().toString();
        String stud_class = student_class;
        String mobile = et_mobile.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        //Send this to the api
        RegisterStudent student = new RegisterStudent(name, email, mobile, stud_class, optionals, password);
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<ResponseForRegistrattion> call = client.createStudent(student);
        call.enqueue(new Callback<ResponseForRegistrattion>() {
            @Override
            public void onResponse(Call<ResponseForRegistrattion> call, Response<ResponseForRegistrattion> response) {
                ResponseForRegistrattion responseForRegistrattion = response.body();
                if (responseForRegistrattion == null) {
                    Toast.makeText(RegisterActivity.this, "Server Problem plz try again later!!!!", Toast.LENGTH_SHORT).show();
                } else {
                    if (responseForRegistrattion.getStatus() == 1) {
                        Log.d(TAG, "onResponse: User Registered....");
                        handleRegisteredUser(responseForRegistrattion.getContact(), responseForRegistrattion.getSid());
                    } else {
                        Log.d(TAG, "onResponse: User could not be registered bcoz of reason " + responseForRegistrattion.getMessage());
                    }
                    startActivity(new Intent(c, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseForRegistrattion> call, Throwable t) {
                Log.d(TAG, "onFailure: Some error occured bcoz of: " + t.toString());
            }
        });

    }

    private void handleRegisteredUser(String contact, String sid) {
        sessionHelper.createLoginSession(contact, sid);
        startActivity(new Intent(c, MainActivity.class));
        finish();
    }


    /*
     method to clear layouts
     */
    void clearlayouts() {

       /*
       Clear Radio Group
        */
        Log.d(TAG, "clearlayouts: Clearing layouts");
        RadioGroup ll;
        ll = (RadioGroup) findViewById(R.id.stream_group);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.clearCheck();
        ll.removeAllViews();


        /*
        Clear chechboxes and Edittext
         */
        LinearLayout layout;
        layout = (LinearLayout) findViewById(R.id.optionallayout); //layout n which checkboxes will be added
        layout.removeAllViews();
        LinearLayout layout1;
        layout1 = (LinearLayout) findViewById(R.id.streamlayout);
        layout1.removeAllViews();

    }


    /*
    method to dynamically setup streqam checkboxes
     */
    private void SetUpStreamCheckBox() {
        Log.d(TAG, "SetUpStreamCheckBox: Setting up Check boxes");
        String[] Streams = getResources().getStringArray(R.array.Streams);


        RadioGroup ll;
        ll = (RadioGroup) findViewById(R.id.stream_group);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.clearCheck();
        ll.removeAllViews();


        for (int i = 0; i < Streams.length; i++) {

            RadioButton cb = new RadioButton(c);
            cb.setId(i);
            cb.setText(Streams[i]);
            ll.addView(cb);

            /*
              Listener for check events.....
             */
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        addStream(buttonView.getText().toString());
                        setUpCheckBoxStrings(buttonView.getId());
                        Toast.makeText(c, buttonView.getText(), Toast.LENGTH_SHORT).show();

                    } else {

                        removeStream(buttonView.getText().toString());
                    }

                }
            });
        }

    }


    /*----------------------------------------------------------------------------------------------------
      methods to handle stream
     */
    void addStream(String str) {
        Stream = str;

    }

    void removeStream(String str) {
        if (Stream == str) {
            Stream = null;
        }

    }
    /*
--------------------------------------------------------------------------------------------------
     */


    /**
     * Method to setup the radio button
     */

    void setUpText(int code) {
        if (code == 1) {
            LinearLayout layout;
            layout = (LinearLayout) findViewById(R.id.streamlayout);
            layout.removeAllViews();
            LinearLayout.LayoutParams checkparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkparams.setMargins(10, 10, 10, 10);
            checkparams.gravity = Gravity.LEFT;

            TextView textView = new TextView(this);
            textView.setText("Choose Stream");
            layout.addView(textView, checkparams);

        }


    }


    /**
     * Used to get the item selected by the student and send it to the server
     */

    private void setClickListener() {

        Log.d(TAG, "setClickListener: Setting Up Click Listeners");
        sp_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                student_class = classList.get(position);

                /*
                to handle radiobuttons
                 */
                if (position == 10 || position == 11) {
                    SetUpStreamCheckBox();
                    setUpText(1);
                } else {
                    clearlayouts();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Will handle this as an alert.....
            }
        });
    }

    /**
     * Method to setUp the optional Subjects according to the radio button selected
     */

    private void setUpCheckBoxStrings(int id) {

        List<String> moptionalSubjects = new ArrayList<String>();
        Log.d(TAG, "setUpCheckBoxes: Setting up the checkboxes....");

        if (id == 0) {

            moptionalSubjects.add("Physical");
            moptionalSubjects.add("Computer Science");
            moptionalSubjects.add("ED");
            moptionalSubjects.add("Economics");

        } else if (id == 1) {

            moptionalSubjects.add("Physical");
            moptionalSubjects.add("Economics");

        } else if (id == 2) {

            moptionalSubjects.add("Physical");
            moptionalSubjects.add("Information Technology");
            moptionalSubjects.add("Maths");

        } else {
            moptionalSubjects.add("Physical");
            moptionalSubjects.add("Information Technology");
            moptionalSubjects.add("Sociology");
            moptionalSubjects.add("Sychology");
        }

        setUpCheckBoxes(moptionalSubjects);

    }

    /**
     * Setting up boxes with the optional Subjects
     *
     * @param moptionalSubjects
     */

    private void setUpCheckBoxes(List<String> moptionalSubjects) {

        Log.d(TAG, "setUpCheckBoxes: Setting up final check boxes");
        clearalloptionals();
        LinearLayout layout;
        layout = (LinearLayout) findViewById(R.id.optionallayout); //layout n which checkboxes will be added
        layout.removeAllViews();
        LinearLayout.LayoutParams checkparams = new LinearLayout.LayoutParams(

                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        checkparams.setMargins(10, 10, 10, 10);
        checkparams.gravity = Gravity.LEFT;


        TextView text = new TextView(this);
        text.setText("Choose Optionals");

        layout.addView(text, checkparams);
        for (int i = 0; i < moptionalSubjects.size(); i++) {

            CheckBox cb = new CheckBox(c);
            cb.setId(i);
            cb.setText(moptionalSubjects.get(i));
            layout.addView(cb, checkparams);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        addStringtoOptionals(buttonView.getText().toString());

                    } else {

                        removeStringifUnchecked(buttonView.getText().toString());
                    }

                }
            });
        }

    }


    void registerbuttonlistner() {
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: In register onClick");
                Log.d(TAG, "onClick: Register activation");
                registerStudent();
            }
        });


    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }


    /**
     * Removing unselected String from optionals
     *
     * @param
     */

    private void clearalloptionals() {
        optionals.clear();

    }

    private void removeStringifUnchecked(String s) {

        optionals.remove(s);

    }

    /**
     * Adding selected checkbox to the list of optionals
     *
     * @param s
     */
    private void addStringtoOptionals(String s) {

        optionals.add(s);

    }


    /**
     * Method to setup the spinner
     *
     * @param data
     * @param c
     */

    private void setUpSpinner(List<String> data, Context c) {

        Log.d(TAG, "setUpSpinner: Setting Up Spinner");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, data);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_class.setAdapter(dataAdapter);


    }


}
