package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUp extends AppCompatActivity {


    EditText name, email, phno, cgname, acyear, year, semester, password;
    String uname, umail, uphone, uclname, cacyear, uyear, usemester, upassword, degreestring, deptstring;
    AutoCompleteTextView degree, department;
    String devid;
    FloatingActionButton submit1, submit2, submit3;
    SpotsDialog spotsDialog;
    CardView c1, c2, c3;

    String[] degreelist = {"BE", "BTech", "Bsc", "MSc", "ME", "MBA", "others"};
    String[] deptlist = {"CSE", "ECE", "EEE", "MECH", "CIVL", "BIO", "ARO", "AUTO", "IT", "PROD", "BCom", "ARCH", "EIE", "ICE"};

    int i = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spotsDialog = new SpotsDialog(this);

        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        phno = findViewById(R.id.reg_phonenumber);
        cgname = findViewById(R.id.reg_cgname);
        acyear = findViewById(R.id.reg_acyear);
        year = findViewById(R.id.reg_year);
        semester = findViewById(R.id.reg_semester);
        degree = findViewById(R.id.reg_degree);
        department = findViewById(R.id.reg_department);
        password = (EditText) findViewById(R.id.reg_password);
        submit1 = findViewById(R.id.floatingActionButton1);
        submit2 = findViewById(R.id.floatingActionButton2);
        submit3 = findViewById(R.id.floatingActionButton3);

        ArrayAdapter<String> ad1 = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, degreelist);
        degree.setAdapter(ad1);
        degree.setThreshold(1);

        ArrayAdapter<String> ad2 = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, deptlist);
        department.setAdapter(ad2);
        department.setThreshold(1);

        c1 = findViewById(R.id.cardView1);
        c2 = findViewById(R.id.cardView2);
        c3 = findViewById(R.id.cardView3);
    }


    @Override
    protected void onResume() {
        super.onResume();

        submit1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                i = 1;

                if (checkname(name.getText().toString())) {
                    if (emailvalid(email.getText().toString())) {
                        if (phno.getText().toString().length() == 10) {
                            c1.setVisibility(View.INVISIBLE);
                            c2.setVisibility(View.VISIBLE);
                            c3.setVisibility(View.INVISIBLE);
                            submit1.setVisibility(View.INVISIBLE);
                            submit2.setVisibility(View.VISIBLE);
                            submit3.setVisibility(View.INVISIBLE);
                        } else {
                            phno.setError("invalid phone number");
                        }
                    } else {
                        email.setError("please enter valid email id");
                    }
                } else {
                    name.setError("Please enter valid name");
                }
            }
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                i = 2;

                c1.setVisibility(View.INVISIBLE);
                c2.setVisibility(View.INVISIBLE);
                c3.setVisibility(View.VISIBLE);
                submit1.setVisibility(View.INVISIBLE);
                submit2.setVisibility(View.INVISIBLE);
                submit3.setVisibility(View.VISIBLE);

            }
        });

        submit3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                spotsDialog.show();
                sharedPreferences = getSharedPreferences("DEV_ID", MODE_PRIVATE);
                uname = name.getText().toString();
                umail = email.getText().toString();
                uphone = phno.getText().toString();
                uclname = cgname.getText().toString();
                cacyear = acyear.getText().toString();
                uyear = year.getText().toString();
                usemester = semester.getText().toString();
                upassword = password.getText().toString();
                degreestring = degree.getText().toString();
                deptstring = department.getText().toString();
                devid = sharedPreferences.getString("did", null);
                Log.d("Responsdate",devid);
                Call<String> call = ApiUtil.getServiceClass().addaccount(uname,upassword,uclname,degreestring,deptstring,uyear,usemester,uphone,cacyear,umail,devid);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Log.d("Responsdate",response.body().toString());
                            spotsDialog.dismiss();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("R", "registered");
                            editor.putString("eid","0");
                            editor.putString("uid",response.body().toString());
                            editor.commit();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                        builder.setTitle("Congrats...Your User Account Activated...Click DONE to Continue");
                        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(SignUp.this,EventActivity.class);
                                startActivity(intent);

                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                            Toast.makeText(SignUp.this, "Registration Success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

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

}
