package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;


public class SplashActivty extends AppCompatActivity {

    String androidId;
    ApiUtil apiUtil;
    Context context;
    String token;
    SharedPreferences sharedPreferences;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activty);

        context = this;

        sharedPreferences = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        apiUtil = new ApiUtil();

        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("DEV_ID", androidId + "");

if(getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("DEV_ID_STATS","").isEmpty()){
    pushDeviceId(androidId);
}else{
    Handler h = new Handler();
    h.postDelayed(new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }, 3000);
}



        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( SplashActivty.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String refreshedToken = instanceIdResult.getToken();
                Log.i("newToken",refreshedToken);
            /*    try {
                    String refreshToken = FirebaseInstanceId.getInstance().getToken("send_id", "FCM");
                    Log.i("newFCMToken",refreshToken);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

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

        //updatedeviceid.php?device=12345

        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(apiUtil.DEVICEID_URL + "?device=" + androidId);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, retrofit2.Response<DownloadResponse> response) {


                Log.d("RESPONSE", response.raw().toString() + "log_res");

                Log.d("RESPONSE", response.body().toString() + "log_msg");


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("DEV_ID", androidId);
                editor.putString("DEV_ID_STATS", "sent");
                editor.apply();


                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }, 3000);



            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {

            }


        });
    }

}
