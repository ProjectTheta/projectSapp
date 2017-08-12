package com.example.suhail.loginattempt1.Activities;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suhail.loginattempt1.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    EditText et_name, et_email, et_password, et_address, et_mobile;
    RadioGroup radioGroup;
    Context c = RegisterActivity.this;
    LinearLayout ll_check;
    RadioButton rb_non_med, rb_med, rb_arts, rb_commerce;
    Button bt_register;
    Spinner sp_class;
    String student_class;
    TextView tv_login;
    List<String> extraSubjects = new ArrayList<String>();
    List<String> classList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: In oncreate");
        Context c = RegisterActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ll_check = (LinearLayout) findViewById(R.id.ll_checkboxes);

        sp_class = (Spinner) findViewById(R.id.class_spinner);
        radioGroup = (RadioGroup) findViewById(R.id.stream_group);
        rb_non_med = (RadioButton) findViewById(R.id.stream_science);
        rb_med = (RadioButton) findViewById(R.id.stream_medical);
        rb_arts = (RadioButton) findViewById(R.id.stream_arts);
        rb_commerce = (RadioButton) findViewById(R.id.stream_commerce);

        et_name = (EditText) findViewById(R.id.register_user_name);
        et_email = (EditText) findViewById(R.id.register_email);
        et_password = (EditText) findViewById(R.id.register_password);
        et_address = (EditText) findViewById(R.id.register_address);
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

        setUpRadioGroup();

        /**
         * ------------------------------------------------------------------------------------------------------
         */

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Register activation");
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();

                //registerStudent()
            }
        });
    }


    private void setUpRadioGroup() {

        final List<String> optionalSubjectList = new ArrayList<String>();
        Log.d(TAG, "setUpRadioGroup: Setting up the radio Group");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                TextView tv_new = new TextView(c);
                tv_new.setText("Please choose your additional subjects....");
                ll_check.addView(tv_new);
                Log.d(TAG, "onCheckedChanged: In Radio Change");
                if(checkedId == rb_non_med.getId()) {
                    //Hard coded for the time being but will come from the api once added
                    optionalSubjectList.add("Computer Science");
                    optionalSubjectList.add("Physical Education");
                    optionalSubjectList.add("ED");
                    for(int i = 0 ; i < optionalSubjectList.size(); i++) {
                        CheckBox cb = new CheckBox(c);
                        cb.setId(i);
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                switch (buttonView.getId()){
                                    case 1: extraSubjects.add(optionalSubjectList.get(0));
                                        break;
                                    case 2: extraSubjects.add(optionalSubjectList.get(1));
                                        break;
                                    case 3: extraSubjects.add(optionalSubjectList.get(2));
                                }
                            }
                        });
                        cb.setText(optionalSubjectList.get(i));
                        ll_check.addView(cb);
                        Log.d(TAG, "onCheckedChanged: " + extraSubjects.toString());
                    }
                }
                if(checkedId == rb_med.getId()) {
                    Toast.makeText(getApplicationContext(), "You Chose med.....", Toast.LENGTH_SHORT).show();
                }
                if(checkedId == rb_commerce.getId()) {
                    Toast.makeText(getApplicationContext(), "You Chose commerce.....", Toast.LENGTH_SHORT).show();
                }
                if(checkedId == rb_arts.getId()) {
                    Toast.makeText(getApplicationContext(), "You Chose arts.....", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void setClickListener() {

        Log.d(TAG, "setClickListener: Setting Up Click Listeners");
        sp_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: Selecting an item");
                String item = parent.getItemAtPosition(position).toString();
                student_class = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setUpSpinner(List<String> data, Context c) {
        Log.d(TAG, "setUpSpinner: Setting Up Spinner");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, data);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_class.setAdapter(dataAdapter);
    }



}
