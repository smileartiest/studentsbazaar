package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUp extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    EditText name, email, phno, acyear, year, semester, password,conpass;
    String uname, umail, uphone, uclname, cacyear, uyear, usemester,uconpass, upassword, degreestring, deptstring, sAffiliation;
    AutoCompleteTextView cgname, degree, department;
    String devid;
    Toolbar toolbar;
    String[] affiliation = {"-Select-", "Deemed University", "Autonomous", "Affiliated to Anna University", "Affiliated to Madras University", "Others"};
    FloatingActionButton submit1, submit2, submit3;
    SpotsDialog spotsDialog;
    CardView c1, c2, c3;
    Spinner spin;

    String[] degreelist = {"BE", "BTech", "Bsc", "MSc", "ME", "MBA", "others"};
    String[] deptlist = {"CSE", "ECE", "EEE", "MECH", "CIVL", "BIO", "ARO", "AUTO", "IT", "PROD", "BCom", "ARCH", "EIE", "ICE"};

    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spotsDialog = new SpotsDialog(this);
        new Controller(this);
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        phno = findViewById(R.id.reg_phonenumber);
        cgname = findViewById(R.id.reg_cgname);
        acyear = findViewById(R.id.reg_acyear);
        year = findViewById(R.id.reg_year);
        semester = findViewById(R.id.reg_semester);
        degree = findViewById(R.id.reg_degree);
        department = findViewById(R.id.reg_department);
        conpass=findViewById(R.id.reg_conpassword);
        password = (EditText) findViewById(R.id.reg_password);
        submit1 = findViewById(R.id.floatingActionButton1);
        submit2 = findViewById(R.id.floatingActionButton2);
        submit3 = findViewById(R.id.floatingActionButton3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        name.requestFocus();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayAdapter<String> ad1 = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, degreelist);
        degree.setAdapter(ad1);
        degree.setThreshold(1);

        ArrayAdapter<String> coll = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, ApiUtil.COLLEGEARRAY);
        cgname.setAdapter(coll);
        cgname.setThreshold(1);

        ArrayAdapter<String> ad2 = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, deptlist);
        department.setAdapter(ad2);
        department.setThreshold(1);

        c1 = findViewById(R.id.cardView1);
        c2 = findViewById(R.id.cardView2);
        c3 = findViewById(R.id.cardView3);

        spin = (Spinner) findViewById(R.id.reg_university);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, affiliation);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(),affiliation[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    @Override
    protected void onResume() {
        super.onResume();

        submit1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                i = 1;

                if (!name.getText().toString().isEmpty()) {

                    if (emailvalid(email.getText().toString())) {
                        if (phno.getText().toString().length() == 10) {
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            submit1.setVisibility(View.INVISIBLE);
                            submit2.setVisibility(View.VISIBLE);
                            submit3.setVisibility(View.INVISIBLE);
                        } else {
                            Move_Show.showToast("Enter Valid mobile no");
                            phno.requestFocus();
                        }
                    } else {
                        Move_Show.showToast("Enter Valid Email id");
                        email.requestFocus();
                    }
                } else {
                    Move_Show.showToast("Enter Username");
                    name.requestFocus();
                }
            }
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                i = 2;
                if (cgname.getText().toString().isEmpty()) {
                    Move_Show.showToast("Enter your College name");
                    cgname.requestFocus();
                } else if (spin.getSelectedItem().equals("-Select-")) {
                    Move_Show.showToast("Select your University Type");
                    spin.requestFocus();
                } else if (degree.getText().toString().isEmpty()) {
                    Move_Show.showToast("Enter Degree");
                    degree.requestFocus();
                } else if (acyear.getText().toString().isEmpty() || acyear.getText().toString().length() < 9) {
                    Move_Show.showToast("Enter Academic Year");
                    acyear.requestFocus();
                } else {
                    c1.setVisibility(View.INVISIBLE);
                    c2.setVisibility(View.INVISIBLE);
                    c3.setVisibility(View.VISIBLE);
                    submit1.setVisibility(View.INVISIBLE);
                    submit2.setVisibility(View.INVISIBLE);
                    submit3.setVisibility(View.VISIBLE);
                }
            }
        });
        acyear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (acyear.getText().toString().length()==4){
                    acyear.setText(acyear.getText().toString()+"-");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submit3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                uname = name.getText().toString();
                umail = email.getText().toString();
                uphone = phno.getText().toString();
                uclname = cgname.getText().toString();
                cacyear = acyear.getText().toString();
                sAffiliation = spin.getSelectedItem().toString();
                Log.d("Affiliation", sAffiliation + "<-");
                uyear = year.getText().toString();
                usemester = semester.getText().toString();
                upassword = password.getText().toString();
                degreestring = degree.getText().toString();
                deptstring = department.getText().toString();
                uconpass=conpass.getText().toString();
                devid = Controller.getDIVID();
                Log.d("Responsdate", devid);


                    if (deptstring.isEmpty()) {
                        Move_Show.showToast("Enter Department");
                    } else if (uyear.isEmpty()) {
                        Move_Show.showToast("Enter Year of Studying");
                    } else if (usemester.isEmpty()) {
                        Move_Show.showToast("Enter semester");
                    }else if (upassword.isEmpty() || uconpass.isEmpty()){
                        Move_Show.showToast("Enter password");
                    }else{
                        if (upassword.equals(uconpass)){
                            spotsDialog.show();
                Call<String> call = ApiUtil.getServiceClass().addaccount(uname, Controller.getUID(), upassword, uclname, sAffiliation, degreestring, deptstring, uyear, usemester, uphone, cacyear, umail, devid);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Log.d("Responsdate", response.raw().toString());
                        spotsDialog.dismiss();
                        if (response.body().equals("1")) {
                            Controller.addprefer(Controller.REG);
                            getAlert();
                        } else {

                            Move_Show.showToast(response.body().toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                        }else
                        {
                            Move_Show.showToast("Password mismatch");
                        }
                    }
            }
        });


    }


    public Boolean checkname(String name) {
        if (name.matches("\\d+(?:\\.\\d+)?")) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean emailvalid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {

        if (i == 0) {
            new Move_Show(SignUp.this, HomeActivity.class);
            finish();
        } else if (i == 1) {
            i = 0;
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.INVISIBLE);
            c3.setVisibility(View.INVISIBLE);
            submit1.setVisibility(View.VISIBLE);
            submit2.setVisibility(View.INVISIBLE);
            submit3.setVisibility(View.INVISIBLE);
        } else if (i == 2) {
            i = 1;
            c1.setVisibility(View.INVISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.INVISIBLE);
            submit1.setVisibility(View.INVISIBLE);
            submit2.setVisibility(View.VISIBLE);
            submit3.setVisibility(View.INVISIBLE);
        }

    }


    void getAlert() {
        SharedPreferences smeme = getSharedPreferences("meme", Context.MODE_PRIVATE);
        if (smeme.getString("source", "").equals("meme")) {
            Move_Show.showToast("Welcome to meme Box");
            new Move_Show(SignUp.this, Mems.class);
            finish();
        } else if (smeme.getString("source", "").equals("signup")) {
            new Move_Show(SignUp.this, HomeActivity.class);
            finish();
        } else {
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setTitle("Hey there ! Your Request has been Success...");
            builder.setMessage("Want to Add Event? or Home?");
            builder.addButton("Add event", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new Move_Show(SignUp.this, AddEvent2.class);
                    finish();

                }
            });
            builder.addButton("Home", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new Move_Show(SignUp.this, HomeActivity.class);
                    finish();
                }
            });
            builder.show();
        }
    }

}




