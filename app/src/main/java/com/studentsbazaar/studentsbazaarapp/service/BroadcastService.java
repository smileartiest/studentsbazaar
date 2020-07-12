package com.studentsbazaar.studentsbazaarapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.facebook.login.Login;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.TimeDate;

public class BroadcastService extends Service {
    private final static String TAG = "BroadcastService";

    private static String CHANNEL_ID ="com.studentsbazaar.studentsbazaarapp.BroadcastService";
    private final int NOTIFICATION_ID = 001;

    public static final String COUNTDOWN_BR = "com.fresherslive.gold.rate.india.service.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);
    SharedPreferences sharedPreferences;

    CountDownTimer cdt = null;
    int time;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Starting timer...");
        sharedPreferences = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        cdt = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeDate t = new TimeDate();
                time = (int) (millisUntilFinished / 1000);
                Log.i(TAG, "CountdownTime seconds remaining: " + time +" "+t.gettime());
                if(time == 1){
                    if(sharedPreferences.getInt("USAGE",0)==0){
                        editor.putInt("USAGE",30).apply();
                    }else{
                        int lasttime = sharedPreferences.getInt("USAGE",0);
                        editor.putInt("USAGE",lasttime+30).apply();
                    }
                    Log.i(TAG, "CountdownTime seconds remaining: Threshold Reached "+sharedPreferences.getInt("USAGE",0) );
                }
                bi.putExtra("countdown", millisUntilFinished);
                sendBroadcast(bi);
            }
            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
                start();
            }
        };
        cdt.start();
    }
    public void timeFinis(){
        Log.i(TAG,"Count "+time);
    }
    @Override
    public void onDestroy() {
        cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification(String title , String message){
        createNotificationChannel();
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_home_icon_ai)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}


