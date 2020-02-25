package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.model.College_Details;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivty extends AppCompatActivity {
    String androidId;
    List<College_Details> college_details;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activty);
        new Controller(SplashActivty.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        connectionverify();


    }


    private void pushDeviceId(final String androidId) {

        Call<String> call = ApiUtil.getServiceClass().updatedeviceid(androidId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Controller.addDIVID(androidId);
                Controller.addUserID(response.body());
                Controller.addprefer(Controller.VISITOR);
                collegedetails();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

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
                if (Controller.getprefer().equals(Controller.ADMIN)) {
                    new Move_Show(SplashActivty.this, HomeActivity.class);
                    Move_Show.showToast("Login Success");
                    finish();
                } else if (Controller.getprefer().equals(Controller.REG)) {
                    new Move_Show(SplashActivty.this, HomeActivity.class);
                    Move_Show.showToast("Login Success");
                    finish();
                } else {
                    new Move_Show(SplashActivty.this, HomeActivity.class);
                    finish();
                }

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

            if (Controller.getDIVID() == null) {

                pushDeviceId(androidId);

            } else {
                collegedetails();
            }
        } else {

            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(SplashActivty.this);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setTitle("Hey there !");
            builder.setMessage("Internet Connection is not available...\nplease switch on internet..");
            builder.addButton("Done", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
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

}
