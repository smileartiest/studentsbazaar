package com.studentsbazaar.studentsbazaarapp.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.studentsbazaar.studentsbazaarapp.activity.SplashActivty;


/**
 * Created by Melon on 10/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    NotificationUtils notificationUtils;
    Context mContext;
    final static int RQS_STOP_SERVICE = 1;
    String fcmTitle,fcmMsg;

    NotifyServiceReceiver notifyServiceReceiver;

    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
    }


    @Override
    public void onCreate() {
      //  notifyServiceReceiver = new NotifyServiceReceiver();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
// TODO Auto-generated method stub
        //this.unregisterReceiver(notifyServiceReceiver);
        super.onDestroy();
    }

    public class NotifyServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            int rqs = arg1.getIntExtra("RQS", 0);
            if (rqs == RQS_STOP_SERVICE) {
                stopSelf();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       Log.e(TAG, "From: " + remoteMessage.getFrom());
        System.out.println("Message Recived");

        /*if (remoteMessage == null)
            return;*/

        // Check if message contains a notification payload.


        // Check if message contains a data payload.
        fcmTitle = remoteMessage.getNotification().getTitle();
        fcmMsg = remoteMessage.getNotification().getBody();
        Intent resultIntent = new Intent(getApplicationContext(), SplashActivty.class);
        // resultIntent.putExtra("Data", bundleData);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
             showNotificationMessage(getApplicationContext(), fcmTitle, fcmMsg, resultIntent);

    }





    /**
     * Showing notification with text only
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);

    }

    /**
     * Showing notification with text and image
     */



}
