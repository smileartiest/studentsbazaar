package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.firebase.Config;
import com.studentsbazaar.studentsbazaarapp.helper.PersistanceUtil;
import com.studentsbazaar.studentsbazaarapp.model.College_Details;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
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

        if (getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("DEV_ID_STATS", "").isEmpty()) {
            pushDeviceId(androidId);

        } else {
            collegedetails();
        }


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivty.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String refreshedToken = instanceIdResult.getToken();
                Log.i("newToken", refreshedToken);


                PersistanceUtil.setUserID(refreshedToken);

                // Saving reg id to shared preferences

                storeRegIdInPref(refreshedToken);
                Config.setPrefToken(context, refreshedToken);

                // Notify UI that registration has completed, so the progress indicator can be hidden.
                token = refreshedToken;
                Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
                registrationComplete.putExtra("token", refreshedToken);
                LocalBroadcastManager.getInstance(context).sendBroadcast(registrationComplete);

            }
        });


    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }

    private void pushDeviceId(final String androidId) {

        Call<String> call = ApiUtil.getServiceClass().updatedeviceid(androidId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                editor.putString("UID", response.body());
                editor.putString("DEV_ID", androidId);
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
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {

            }
        });

    }

}
