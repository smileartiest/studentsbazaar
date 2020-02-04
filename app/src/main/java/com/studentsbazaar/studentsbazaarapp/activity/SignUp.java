package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
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

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class SignUp extends AppCompatActivity {


    EditText name, email, phno, acyear, year, semester, password;
    String uname, umail, uphone, uclname, cacyear, uyear, usemester, upassword, degreestring, deptstring;
    AutoCompleteTextView cgname, degree, department;
    String devid;
    FloatingActionButton submit1, submit2, submit3;
    SpotsDialog spotsDialog;
    CardView c1, c2, c3;

    String[] degreelist = {"BE", "BTech", "Bsc", "MSc", "ME", "MBA", "others"};
    String[] deptlist = {"CSE", "ECE", "EEE", "MECH", "CIVL", "BIO", "ARO", "AUTO", "IT", "PROD", "BCom", "ARCH", "EIE", "ICE"};

    int i = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spotsDialog = new SpotsDialog(this);
        sharedPreferences = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        editor = sharedPreferences.edit();
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

        ArrayAdapter<String> coll = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, ApiUtil.COLLEGEARRAY);
        cgname.setAdapter(coll);
        cgname.setThreshold(1);

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
                devid = sharedPreferences.getString("DEV_ID", null);
                Log.d("Responsdate", devid);
                Call<String> call = ApiUtil.getServiceClass().addaccount(uname, sharedPreferences.getString("UID", null), upassword, uclname, degreestring, deptstring, uyear, usemester, uphone, cacyear, umail, devid);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Log.d("Responsdate", response.body().toString());
                        spotsDialog.dismiss();
                        if (response.body().equals("1")) {
                            editor.putString("log", "reg");
                            editor.apply();
                            getAlert();
//

                        } else {

                            Toast.makeText(SignUp.this, "Register Failed ", Toast.LENGTH_SHORT).show();
                        }

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

    void getAlert() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("Hey there ! Your Request has been Success...");
        builder.setMessage("Want to Add Event? or Home?");
        builder.addButton("Add event", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent i = new Intent(SignUp.this, AddEvent2.class);
                startActivity(i);

            }
        });

        builder.addButton("Home", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(SplashActivity.this, "Upgrade tapped", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                Intent i = new Intent(SignUp.this, HomeActivity.class);
                startActivity(i);
            }
        });
        builder.show();

    }

}
