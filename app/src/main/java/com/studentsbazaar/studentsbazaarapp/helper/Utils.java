package com.studentsbazaar.studentsbazaarapp.helper;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.sql.Time;

public class Utils {

    public static boolean isNetworkAvailable(ConnectivityManager connectivityManager){

        try{
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }catch (Exception e){
            Log.d("UTILS",e.getMessage());
        }

        return false;
    }

    public String getCurTime(){

        int hours = new Time(System.currentTimeMillis()).getHours();
        int min = new Time(System.currentTimeMillis()).getMinutes();
        int sec = new Time(System.currentTimeMillis()).getSeconds();


        System.out.println("CUR_TIME->"+hours+":"+min+":"+sec);
        String time = hours+":"+min+":"+sec;


        return time;
    }
}
