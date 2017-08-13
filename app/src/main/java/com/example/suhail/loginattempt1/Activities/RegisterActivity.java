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

import com.example.suhail.loginattempt1.Models.RegisterStudent;
import com.example.suhail.loginattempt1.R;

import java.util.ArrayList;
import java.util.List;

//Testing Branches.....
//Dummy Commit.....
//Another Commit....

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    EditText et_name, et_email, et_password, et_address, et_mobile, et_optionals;
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
                Toast.makeText(getApplicationContext(), "ArrayList is: " + extraSubjects.toString(), Toast.LENGTH_SHORT).show();
                registerStudent();
            }
        });
    }

    /**
     * Register the student with the entered credentials
     */

    private void registerStudent() {
        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String stud_class = student_class;
        String mobile = et_mobile.getText().toString();
        String email = et_email.getText().toString();
        //Send this to the api
        RegisterStudent student = new RegisterStudent(name, email,
                                                    address, mobile,
                                                    stud_class, extraSubjects);
    }

    /**
     * Method to setup the radio button
     */

    private void setUpRadioGroup() {

        Log.d(TAG, "setUpRadioGroup: Setting up the radio Group");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                Log.d(TAG, "onCheckedChanged: In Radio Change");
                if(checkedId == rb_non_med.getId()) {

                    setUpCheckBoxStrings(checkedId);

                } else if(checkedId == rb_med.getId()) {

                    setUpCheckBoxStrings(checkedId);

                } else if(checkedId == rb_commerce.getId()) {

                    setUpCheckBoxStrings(checkedId);

                } else if(checkedId == rb_arts.getId()) {

                    setUpCheckBoxStrings(checkedId);

                }

            }
        });

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
        TextView tv_info = new TextView(c);
        tv_info.setText("Please choose the optional subjects");
        ll_check.removeAllViews();
        ll_check.addView(tv_info);

        if(id == R.id.stream_science) {

            moptionalSubjects.add("Physical");
            moptionalSubjects.add("Computer Science");
            moptionalSubjects.add("ED");
            moptionalSubjects.add("Economics");

        } else if(id == R.id.stream_medical) {

            moptionalSubjects.add("Physical");
            moptionalSubjects.add("Economics");

        } else if(id == R.id.stream_commerce) {

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
     * @param moptionalSubjects
     */

    private void setUpCheckBoxes(List<String> moptionalSubjects) {

        Log.d(TAG, "setUpCheckBoxes: Setting up final check boxes");
        
        for(int i = 0 ; i < moptionalSubjects.size() ; i++) {
            
            CheckBox cb = new CheckBox(c);
            cb.setId(i);
            cb.setText(moptionalSubjects.get(i));
            ll_check.addView(cb);
            
            //Listener for check events.....
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked) {
                        addStringtoOptionals(buttonView.getText().toString());
                    } else {
                        removeStringifUnchecked(buttonView.getText().toString());
                    }

                }
            });
        }
    }

    /**
     * Removing unselected String from optionals
     * @param s
     */

    private void removeStringifUnchecked(String s) {

        extraSubjects.remove(s);

    }

    /**
     * Adding selected checkbox to the list of optionals
     * @param s
     */
    private void addStringtoOptionals(String s) {

        extraSubjects.add(s);

    }


    /**
     * Method to setup the spinner
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
