package com.studentsbazaar.studentsbazaarapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.studentsbazaar.studentsbazaarapp.controller.SendSMS;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.Calendar;
import java.util.Random;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    TextView sts, otptime;
    EditText otpcode;
    TextInputLayout mobno, newpass, cpass;
    Button sendotp, veridyotp, resendotp, changepass;
    ConstraintLayout screen;
    SpotsDialog spotsDialog;
    String phno1,otp1;
    CountDownTimer ctime;
    Toolbar mytoolbar;

    int CURRENT_TIME;
    int LIMIT_TIME1 = 9;
    int LIMIT_TIME2 = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        mytoolbar = findViewById(R.id.fpass_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sts = findViewById(R.id.fpass_sts);
        otptime = findViewById(R.id.fpass_otp_time);
        otpcode = findViewById(R.id.fpass_otp_code);
        mobno = findViewById(R.id.fpass_mobno);
        newpass = findViewById(R.id.fpass_newpass);
        cpass = findViewById(R.id.fpass_conformpass);

        sendotp = findViewById(R.id.fpass_sendotp);
        veridyotp = findViewById(R.id.fpass_otp_verify);
        resendotp = findViewById(R.id.fpass_otp_resend);
        changepass = findViewById(R.id.fpass_chnagepass);

        screen = findViewById(R.id.fpass_screen);

        spotsDialog = new SpotsDialog(this);

        sts.setText("Enter your Valid Mobile Number");
        otptime.setVisibility(View.GONE);
        otpcode.setVisibility(View.GONE);
        veridyotp.setVisibility(View.GONE);
        resendotp.setVisibility(View.GONE);
        newpass.setVisibility(View.GONE);
        cpass.setVisibility(View.GONE);
        changepass.setVisibility(View.GONE);

        Calendar c = Calendar.getInstance();
        CURRENT_TIME = c.get(Calendar.HOUR);
        Log.d("Ctime : ", String.valueOf(CURRENT_TIME));
        if(LIMIT_TIME1 >= CURRENT_TIME && LIMIT_TIME2 <= CURRENT_TIME ){
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ForgetPassword.this);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setCornerRadius(20);
            builder.setCancelable(false);
            builder.setContentImageDrawable(R.drawable.timing_closed_icon);
            builder.setTitle("Sorry !");
            builder.setMessage("OTP timing 9AM to 9PM only . So please try again after some time");
            builder.addButton("OK", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            builder.show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phno1 = mobno.getEditText().getText().toString();
                if(phno1.length()!=0 && phno1.length()==10){
                    spotsDialog.setTitle("Checking");
                    spotsDialog.setTitle("Checking your Phone Number");
                    spotsDialog.show();
                    otp1 = getRandomNumberString();
                    Call<String> call = ApiUtil.getServiceClass().checkuid(phno1);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                if(response.body().equals("0")){
                                    spotsDialog.dismiss();
                                    Oops_dialog("Account not found this Number");
                                }else{
                                    new SendSMS(ForgetPassword.this , phno1 , otp1);
                                    sts.setText("Send OTP this number "+phno1+". Enter your Valid OTP ");
                                    mobno.setVisibility(View.GONE);
                                    sendotp.setVisibility(View.GONE);
                                    otptime.setVisibility(View.VISIBLE);
                                    otpcode.setVisibility(View.VISIBLE);
                                    veridyotp.setVisibility(View.VISIBLE);
                                    spotsDialog.dismiss();
                                    Hour(90);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("Forget Password Error" ,t.getMessage());
                        }
                    });
                }else{
                    Oops_dialog("Enter valid mobile Number ");
                }
            }
        });

        veridyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctime.cancel();
                String otpcode1 = otpcode.getText().toString();
                if(otpcode1.length()!=0){
                    if(otpcode1.equals(otp1)) {
                        sts.setText("OTP Verification success change your password now.");
                        otptime.setVisibility(View.GONE);
                        otpcode.setVisibility(View.GONE);
                        veridyotp.setVisibility(View.GONE);
                        resendotp.setVisibility(View.GONE);
                        newpass.setVisibility(View.VISIBLE);
                        cpass.setVisibility(View.VISIBLE);
                        changepass.setVisibility(View.VISIBLE);
                    }else{
                        Snackbar.make(screen , "OTP not matched " , Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(screen , "enter valid otp",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp1 = getRandomNumberString();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new SendSMS(ForgetPassword.this , phno1 , otp1);
                        sts.setText("Enter your Valid OTP ");
                        Hour(90);
                    }
                },2000);
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotsDialog.setTitle("Updating");
                spotsDialog.setMessage("please wait....");
                spotsDialog.show();
                String newpass1 = newpass.getEditText().getText().toString().trim();
                String cpass1 = cpass.getEditText().getText().toString().trim();
                if (newpass1.length() != 0 && cpass1.length() != 0) {
                    if (newpass1.equals(cpass1)) {
                        Call<String> call = ApiUtil.getServiceClass().updatepassword(phno1, cpass1);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                spotsDialog.dismiss();
                                if (response.body().equals("1")) {
                                    spotsDialog.dismiss();
                                    openconformationdialog();
                                } else if (response.body().equals("2")){
                                    spotsDialog.dismiss();
                                    Oops_dialog("Account not found");
                                }else {
                                    spotsDialog.dismiss();
                                    Oops_dialog("Invalid user details");
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("Password Error : " , t.getMessage());
                            }
                        });
                        Snackbar.make(screen , "Password change successfull",Snackbar.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Snackbar.make(screen , "Password Mis match",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    void Oops_dialog(String message1){
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ForgetPassword.this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setCornerRadius(20);
        builder.setContentImageDrawable(R.drawable.sad_emoji_icon);
        builder.setTitle("Oops !");
        builder.setMessage(message1);
        builder.addButton("OK", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public void openconformationdialog(){
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ForgetPassword.this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setCornerRadius(20);
        builder.setContentImageDrawable(R.drawable.happy_emoji_icon);
        builder.setTitle("Congratulation !");
        builder.setMessage("password changed successfully");
        builder.addButton("OK", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Snackbar.make(screen , "Password change successful" , Snackbar.LENGTH_SHORT).show();
                        finish();
                    }
                });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Snackbar.make(screen , "Please complete your process",Snackbar.LENGTH_SHORT).show();
    }

    public void Hour(int s){
        ctime = new CountDownTimer(s*1000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                otptime.setText("waiting for otp  :  "+ millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                resendotp.setVisibility(View.VISIBLE);
                otptime.setText("Re send OTP");
            }
        }.start();
    }

}
