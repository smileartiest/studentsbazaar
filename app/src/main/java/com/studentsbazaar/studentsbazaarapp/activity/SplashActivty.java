package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.firebase.Config;
import com.studentsbazaar.studentsbazaarapp.model.College_Details;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Posters_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivty extends AppCompatActivity {

    String androidId;
    Context context;
    String token;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<College_Details> college_details;
    List<Posters_Details> posters_details;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activty);
        context = this;
        sharedPreferences = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("DEV_ID", androidId + "");
        connectionverify();

//


    }



    private void pushDeviceId(final String androidId) {

        Call<String> call = ApiUtil.getServiceClass().updatedeviceid(androidId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                editor.putString("UID", response.body());
                editor.putString("DEV_ID", androidId);
                editor.putString("log", "0");
                editor.apply();
                Log.d("RESPONSELOG", response.body());
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
                if (getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("log", "").equals("0") || getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("log", "").equals("visitor")) {
                    Intent intent = new Intent(SplashActivty.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else if (getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("log", "").equals("reg") || getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("log", "").equals("admin")){
                    Intent intent = new Intent(SplashActivty.this, HomeActivity.class);
                    startActivity(intent);
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

            if (getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("DEV_ID", "").isEmpty()) {
                Log.d("RESPONSELOG", "check");
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
