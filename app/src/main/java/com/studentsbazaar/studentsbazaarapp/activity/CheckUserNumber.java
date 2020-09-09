package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.SendSMS;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.Calendar;
import java.util.Random;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckUserNumber extends AppCompatActivity {

    TextInputLayout mobno;
    EditText otp_code;
    Button sendotp,verifyotp,resendotp;
    TextView otp_time,sts;
    SpotsDialog spotsDialog;
    String phno1,otp1;
    CountDownTimer ctime;
    ConstraintLayout screen;
    int CURRENT_TIME;
    int LIMIT_TIME1 = 9;
    int LIMIT_TIME2 = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_user_number);

        mobno = findViewById(R.id.cuser_mobno);
        otp_code = findViewById(R.id.cuser_otpcode);
        sendotp = findViewById(R.id.cuser_sendotp);
        verifyotp = findViewById(R.id.cuser_verify_otp);
        resendotp = findViewById(R.id.cuser_resend_otp);
        otp_time = findViewById(R.id.cuser_otptime);
        sts = findViewById(R.id.cuser_sts);
        screen = findViewById(R.id.cuser_screen);

        sts.setText("Enter your valid Mobile number Here");
        otp_code.setVisibility(View.GONE);
        verifyotp.setVisibility(View.GONE);
        resendotp.setVisibility(View.GONE);
        otp_time.setVisibility(View.GONE);

        spotsDialog = new SpotsDialog(this);

        Calendar c = Calendar.getInstance();
        CURRENT_TIME = c.get(Calendar.HOUR);
        if(LIMIT_TIME1 >= CURRENT_TIME && LIMIT_TIME2 <= CURRENT_TIME ){
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(CheckUserNumber.this);
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
                                    new SendSMS(CheckUserNumber.this ,phno1 , otp1 );
                                    Hour(90);
                                    sts.setText("Send OTP to this number "+phno1+". Enter your Valid OTP ");
                                    mobno.setVisibility(View.GONE);
                                    sendotp.setVisibility(View.GONE);
                                    otp_time.setVisibility(View.VISIBLE);
                                    otp_code.setVisibility(View.VISIBLE);
                                    verifyotp.setVisibility(View.VISIBLE);
                                }else{
                                    spotsDialog.dismiss();
                                    Snackbar.make(screen , "This number Already Registered" , Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            spotsDialog.dismiss();
                            Log.d("Check User Error" ,t.getMessage());
                        }
                    });
                }else{
                    Snackbar.make(screen , "Eneter valid mobile Number ",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctime.cancel();
                String otpcode1 = otp_code.getText().toString();
                if(otpcode1.length()!=0){
                    if(otpcode1.equals(otp1)) {
                        sts.setText("OTP Verification successfull");
                        startActivity(new Intent(getApplicationContext() , SignUp.class).putExtra("uid",phno1));finish();
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
                spotsDialog.setTitle("Re-send OTP");
                spotsDialog.setMessage("Please wait.....");
                spotsDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spotsDialog.dismiss();
                        new SendSMS(CheckUserNumber.this ,phno1 , otp1 );
                        Hour(90);
                    }
                },2000);
            }
        });

    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public void Hour(int s){
        ctime = new CountDownTimer(s*1000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                otp_time.setText("waiting for otp  :  "+ millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                resendotp.setVisibility(View.VISIBLE);
                otp_time.setText("Re send OTP");
            }
        }.start();
    }

}
