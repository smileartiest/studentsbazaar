package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.firebase.Config;
import com.studentsbazaar.studentsbazaarapp.helper.PersistanceUtil;
import com.studentsbazaar.studentsbazaarapp.helper.Utils;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;


public class SplashActivty extends AppCompatActivity {

    String androidId;
    ApiUtil apiUtil;
    Context context;
    String token;
    Typeface tf_regular;
    SharedPreferences sharedPreferences;
    private ConnectivityManager connectivityManager;

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
        tf_regular = Typeface.createFromAsset(getAssets(), "caviar.ttf");

        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        check();


            }

    private void check() {

        ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert ConnectionManager != null;
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            checkIntent();
        }
        else
        {
            openDialog();
        }
    }

    private void checkIntent() {
        if (getSharedPreferences("USER_DETAILS", MODE_PRIVATE).getString("DEV_ID_STATS", "").isEmpty()) {
            pushDeviceId(androidId);
        } else {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }, 3000);
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivty.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String refreshedToken = instanceIdResult.getToken();
                Log.i("newToken", refreshedToken);
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

    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.internetcon);
        TextView tv = dialog.findViewById(R.id.text);
        tv.setTypeface(tf_regular);
        Button b = dialog.findViewById(R.id.id_confirm);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(connectivityManager)) {
                    checkIntent();
                    dialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Available", Toast.LENGTH_SHORT).show();

                }
            }
        });

        dialog.show();

    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }

    private void pushDeviceId(final String androidId) {

        //updatedeviceid.php?device=12345

        Call<String> call = ApiUtil.getServiceClass().getLoginDetails(apiUtil.DEVICEID_URL + "?device=" + androidId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {


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

                /*  if (response.isSuccessful()) {

                 *//*   assert response.body() != null;
                    if (response.body().getCode() == 1) {*//*
                       // dismissProgressDialog();

                       *//* editor.putString("USER_EMAIL_ID", semail);
                        editor.putString("USER_ID", response.body().getUuid());
                        editor.apply();*//*
                       // Log.d("RESPONSE", response.body().getUuid() + "");

                    } else {
                      *//*  responseList.setServerError(true);
                        responseList.setErrorName("something went wrong");
                        returnResponse(v);*//*
                    }


                } else {
                   *//* responseList.setServerError(true);
                    responseList.setErrorName("something went wrong");
                    returnResponse(v);*//*
                }*/

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //showErrorMessage();
                // dismissProgressDialog();
               /* responseList.setServerError(true);
                responseList.setErrorName("Network err");*/
                Log.d("RESPONSE", "err" + t.getMessage());
                //returnResponse(v);
            }
        });
    }
}
