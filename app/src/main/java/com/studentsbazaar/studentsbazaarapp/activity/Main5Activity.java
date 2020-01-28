package com.studentsbazaar.studentsbazaarapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.firebase.Config;
import com.studentsbazaar.studentsbazaarapp.helper.PersistanceUtil;

public class Main5Activity extends AppCompatActivity {

    Context context;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        context = this;

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Main5Activity.this,  new OnSuccessListener<InstanceIdResult>() {
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
}
