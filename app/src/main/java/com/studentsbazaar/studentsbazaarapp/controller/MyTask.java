package com.studentsbazaar.studentsbazaarapp.controller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyTask extends AsyncTask<Void, Void, Boolean> {
    private URL mUrl;

    public MyTask(URL url) {
        mUrl = url;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            HttpURLConnection urlc = (HttpURLConnection) mUrl.openConnection();
            urlc.setConnectTimeout(10 * 1000);
            urlc.connect();
            if (urlc.getResponseCode() == 200) {
                Log.wtf("Connection", "Success !");
                return true;
            } else {
                return false;
            }
        } catch (MalformedURLException e1){
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}