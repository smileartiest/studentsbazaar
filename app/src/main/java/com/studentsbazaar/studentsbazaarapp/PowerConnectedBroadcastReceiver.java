package com.studentsbazaar.studentsbazaarapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

class PowerConnectedBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "PowerConnectedBroRe";
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmHelper alarmManager = new AlarmHelper(context);

        Log.d(TAG, "PendingIntent.getBroadcast(...) != null: " + alarmManager.isAlarmScheduled());
    }
}