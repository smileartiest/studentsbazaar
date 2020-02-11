package com.studentsbazaar.studentsbazaarapp;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;

public class StudentBazaarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        FacebookSdk.setIsDebugEnabled(false);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
    }
}
