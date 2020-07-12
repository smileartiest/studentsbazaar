package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Login_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    List<Login_Details> getlogindetails = null;
    TextView sts;
    TextInputLayout name, email, acyear, year, semester, password, conpass,refcodetxt;
    String uname, umail, uphone, uclname, cacyear, uyear, usemester, uconpass, upassword, degreestring, deptstring, sAffiliation,refcode;
    AutoCompleteTextView cgname, degree, department;
    String devid;
    Toolbar toolbar;
    String[] affiliation = {"-Select-", "Deemed University", "Autonomous", "Affiliated to Anna University", "Affiliated to Madras University", "Others"};
    Button submit3;
    SpotsDialog spotsDialog;
    Spinner spin;

    String[] degreelist = {"BE", "BTech", "Bsc", "MSc", "ME", "MBA", "others"};
    String[] deptlist = {"CSE", "ECE", "EEE", "MECH", "CIVL", "BIO", "ARO", "AUTO", "IT", "PROD", "BCom", "ARCH", "EIE", "ICE"};

    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        spotsDialog = new SpotsDialog(this);
        new Controller(this);

        sts = findViewById(R.id.reg_sts);
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        cgname = findViewById(R.id.reg_cgname);
        acyear = findViewById(R.id.reg_acyear);
        year = findViewById(R.id.reg_year);
        semester = findViewById(R.id.reg_semester);
        degree = findViewById(R.id.reg_degree);
        department = findViewById(R.id.reg_department);
        conpass = findViewById(R.id.reg_conpassword);
        password = findViewById(R.id.reg_password);
        submit3 = findViewById(R.id.reg_complete);
        refcodetxt = findViewById(R.id.reg_refcode);
        toolbar = findViewById(R.id.toolbar);
        name.requestFocus();

        ArrayAdapter<String> ad1 = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, degreelist);
        degree.setAdapter(ad1);
        degree.setThreshold(1);

        ArrayAdapter<String> coll = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, ApiUtil.COLLEGEARRAY);
        cgname.setAdapter(coll);
        cgname.setThreshold(1);

        ArrayAdapter<String> ad2 = new ArrayAdapter<>(getApplicationContext(), R.layout.listrow, deptlist);
        department.setAdapter(ad2);
        department.setThreshold(1);


        spin = (Spinner) findViewById(R.id.reg_university);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, affiliation);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        sts.setText("Your Mobile Number "+getIntent().getStringExtra("uid"));

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

        submit3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                uname = name.getEditText().getText().toString();
                umail = email.getEditText().getText().toString();
                uphone = getIntent().getStringExtra("uid");
                uclname = cgname.getText().toString();
                cacyear = acyear.getEditText().getText().toString();
                sAffiliation = spin.getSelectedItem().toString();
                Log.d("Affiliation", sAffiliation + "<-");
                uyear = year.getEditText().getText().toString();
                usemester = semester.getEditText().getText().toString();
                upassword = password.getEditText().getText().toString();
                degreestring = degree.getText().toString();
                deptstring = department.getText().toString();
                uconpass = conpass.getEditText().getText().toString();
                refcode = refcodetxt.getEditText().getText().toString();
                devid = Controller.getDIVID();

                Log.d("Responsdate", devid);

                if(!checkname(uname)){
                    name.getEditText().setError("Enter valid name");
                    name.requestFocus();
                } else if(!emailvalid(umail)){
                    email.getEditText().setError("Please enter valid Email ID");
                    email.requestFocus();
                }else if (cgname.getText().toString().isEmpty()) {
                    cgname.setError("Enter your College name");
                    cgname.requestFocus();
                } else if (spin.getSelectedItem().equals("-Select-")) {
                    Move_Show.showToast("Select your University Type");
                    spin.requestFocus();
                } else if (degree.getText().toString().isEmpty()) {
                    degree.setError("Enter Degree");
                    degree.requestFocus();
                } else if (acyear.getEditText().getText().toString().isEmpty() || acyear.getEditText().getText().toString().length() < 9) {
                    acyear.getEditText().setError("Enter Academic Year");
                    acyear.requestFocus();
                } else if (deptstring.isEmpty()) {
                    department.setError("Enter Department");
                    department.requestFocus();
                } else if (uyear.isEmpty()) {
                    year.getEditText().setError("Enter Year of Studying");
                    year.requestFocus();
                } else if (usemester.isEmpty()) {
                    semester.getEditText().setError("Enter semester");
                    semester.requestFocus();
                } else if (upassword.isEmpty() || uconpass.isEmpty()) {
                    password.getEditText().setError("Please check password");
                    conpass.getEditText().setError("Please check password");
                    password.requestFocus();
                } else {
                    if (upassword.equals(uconpass)) {
                        spotsDialog.show();
                        Call<DownloadResponse> call = ApiUtil.getServiceClass().addaccount(uname, upassword, uclname, sAffiliation, degreestring, deptstring, uyear, usemester, uphone, cacyear, umail, devid , refcode);
                        call.enqueue(new Callback<DownloadResponse>() {
                            @Override
                            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                                if (response.isSuccessful()) {
                                    Log.d("Resopnse", response.body().toString());
                                    assert response.body() != null;
                                    getlogindetails = response.body().getLogin_Details();
                                    if (getlogindetails.get(0).getSts().equals("0")) {
                                        spotsDialog.dismiss();
                                        Log.d("User sts  ", "Registration Error , Please Try again..?");
                                        errordialog("Registration Error , Please Try again..?");
                                    } else {
                                        spotsDialog.dismiss();
                                        Log.d("User ID   ", getlogindetails.get(0).getUser_Id());
                                        Log.d("User Name ", getlogindetails.get(0).getUser_Name());
                                        Log.d("Email ID  ", getlogindetails.get(0).getEmail_Id());
                                        Log.d("Mobile No ", getlogindetails.get(0).getMobile_No());
                                        Log.d("User Type ", getlogindetails.get(0).getUser_Type());
                                        Controller.addUserID(getlogindetails.get(0).getUser_Id());
                                        Controller.addusername(getlogindetails.get(0).getUser_Name());
                                        Controller.addusermail(getlogindetails.get(0).getEmail_Id());
                                        new Controller(SignUp.this).addphno(getlogindetails.get(0).getMobile_No());
                                        Controller.addprefer(getlogindetails.get(0).getUser_Type());
                                        getAlert();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<DownloadResponse> call, Throwable t) {

                            }
                        });
                    } else {
                        conpass.getEditText().setError("Password mismatch");
                        conpass.requestFocus();
                    }
                }
            }
        });


    }

    public void errordialog(String sts) {
        spotsDialog.dismiss();
        /*final Dialog d = new Dialog(SignUp.this);
        d.setContentView(R.layout.calertdialog);
        TextView title = d.findViewById(R.id.calert_title);
        TextView message = d.findViewById(R.id.calert_message);
        Button btn1 = d.findViewById(R.id.calert_loginbtn);
        Button btn2 = d.findViewById(R.id.calert_registerbtn);
        Button btn3 = d.findViewById(R.id.calert_notnowbtn);

        title.setText("Oops !");
        message.setText(sts);

        btn3.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);

        btn1.setText("ok");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();*/
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
        Toast.makeText(this, "Please complete Your Process", Toast.LENGTH_SHORT).show();
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




