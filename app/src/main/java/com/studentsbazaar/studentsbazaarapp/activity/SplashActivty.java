package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.model.College_Details;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;
import com.studentsbazaar.studentsbazaarapp.service.BroadcastService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivty extends AppCompatActivity {
    List<College_Details> college_details;
    String androidId;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Controller(SplashActivty.this);
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        connectionverify();

        startService(new Intent(this, BroadcastService.class));

    }

    private void collegedetails() {

        Call<DownloadResponse> call = ApiUtil.getServiceClass().getcollegedetails(ApiUtil.GET_COLLEGES);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                college_details = response.body().getCollege_Details();
                for (int i = 0; i < college_details.size(); i++) {
                    ApiUtil.COLLEGEARRAY.add(college_details.get(i).getCollege_Name());
                }
                if(Controller.getprefer()==null){
                    Controller.addprefer(Controller.VISITOR);
                }
                new Move_Show(SplashActivty.this, HomeActivity.class);
                finish();
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void connectionverify() {
        if (isNetworkAvailable()) {
            checkservice();
        } else {

            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(SplashActivty.this);
            builder.setCornerRadius(20);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setContentImageDrawable(R.drawable.check_internet_icon);
            builder.setTextGravity(Gravity.CENTER);
            builder.setTitle("Internet Connection is not available. Please switch on internet !");
            builder.addButton("DONE", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            connectionverify();
                            dialog.dismiss();
                        }
                    });
            builder.show();

        }

    }

    void checkservice(){

        Call<String> call = ApiUtil.getServiceClass().getserversts(ApiUtil.GET_SERVER_STATUS);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body().equals("0")){
                        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        Controller.addDIVID(androidId);
                        collegedetails();
                    }else {
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(SplashActivty.this);
                        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                        builder.setCornerRadius(20);
                        builder.setContentImageDrawable(R.drawable.server_maintain);
                        builder.setTextGravity(Gravity.CENTER);
                        builder.setTitle("Server Maintenance.So Please Try Again After some time. !");
                        builder.addButton("OK", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                        Controller.addDIVID(androidId);
                                        collegedetails();
                                        //finish();
                                    }
                                });
                        builder.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("splase error " , t.getMessage());
            }
        });

    }

}
