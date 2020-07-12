package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.studentsbazaar.studentsbazaarapp.BuildConfig;
import com.studentsbazaar.studentsbazaarapp.CheckUserNumber;
import com.studentsbazaar.studentsbazaarapp.ForgetPassword;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Login_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Page extends AppCompatActivity {

    TextInputLayout phone,pass;
    Button loginbtn;
    CheckBox remember;
    TextView forgetpass,signup,versionnane;
    SpotsDialog spotsDialog;
    String androidId;
    List<Login_Details> getlogindetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        new Controller(this);
        phone = findViewById(R.id.signin_phon);
        pass = findViewById(R.id.signin_pass);
        remember = findViewById(R.id.signin_remember);
        loginbtn = findViewById(R.id.signin_loginbtn);
        signup = findViewById(R.id.signin_signuplink);
        forgetpass = findViewById(R.id.signin_forgetpasslink);
        versionnane = findViewById(R.id.signin_versionname);
        spotsDialog = new SpotsDialog(this);
        phone.requestFocus();

        versionnane.setText("StudentsBazaar . version "+ BuildConfig.VERSION_NAME);

    }

    @Override
    protected void onResume() {
        super.onResume();

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , ForgetPassword.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phno1 = phone.getEditText().getText().toString();
                String pass1 = pass.getEditText().getText().toString();

                if (phno1.length()==0 && phno1.length()>10) {
                    Move_Show.showToast("Please check your Number");
                } else if(pass1.length()==0){
                    Move_Show.showToast("Please fill Password");
                } else {
                    spotsDialog.show();
                    Call<DownloadResponse> call = ApiUtil.getServiceClass().getLoginDetails(phno1, pass1 , Controller.getDIVID());
                    call.enqueue(new Callback<DownloadResponse>() {
                        @Override
                        public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                            if(response.isSuccessful()){
                                spotsDialog.dismiss();
                                assert response.body() !=null;
                                getlogindetails = response.body().getLogin_Details();
                                if(getlogindetails.get(0).getSts().equals("0")){
                                    Log.d("User sts  ", "No User Found");
                                    error_dialog();
                                }else {
                                    Log.d("User ID   ", getlogindetails.get(0).getUser_Id());
                                    Log.d("User Name ", getlogindetails.get(0).getUser_Name());
                                    Log.d("Email ID  ", getlogindetails.get(0).getEmail_Id());
                                    Log.d("Mobile No ", getlogindetails.get(0).getMobile_No());
                                    Log.d("User Type ", getlogindetails.get(0).getUser_Type());
                                    Log.d("Mob Status ", getlogindetails.get(0).getMsts());
                                    Controller.addUserID(getlogindetails.get(0).getUser_Id());
                                    Controller.addusername(getlogindetails.get(0).getUser_Name());
                                    Controller.addusermail(getlogindetails.get(0).getEmail_Id());
                                    new Controller(Login_Page.this).addphno(getlogindetails.get(0).getMobile_No());
                                    Controller.addprefer(getlogindetails.get(0).getUser_Type());
                                    new Controller(Login_Page.this).addmobsts(getlogindetails.get(0).getMsts());
                                    success_dialog();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<DownloadResponse> call, Throwable t) {
                            Log.d("Login Error " , t.getMessage());
                        }
                    });
                }
            }

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Move_Show(Login_Page.this, CheckUserNumber.class);
            }
        });

    }

    void error_dialog(){
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Login_Page.this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setCornerRadius(20);
        builder.setContentImageDrawable(R.drawable.sad_emoji_icon);
        builder.setTextGravity(Gravity.CENTER);
        builder.setTitle("Incorrect user or password !");
        builder.addButton("OK", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    void success_dialog(){
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Login_Page.this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setCornerRadius(20);
        builder.setContentImageDrawable(R.drawable.happy_emoji_icon);
        builder.setTextGravity(Gravity.CENTER);
        builder.setCancelable(false);
        builder.setTitle("Congratulation ! login Successful");
        builder.addButton("CONTINUE", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new Move_Show(Login_Page.this, HomeActivity.class);
                        finish();
                    }
                });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Move_Show(Login_Page.this,HomeActivity.class);
        finish();
    }

}
