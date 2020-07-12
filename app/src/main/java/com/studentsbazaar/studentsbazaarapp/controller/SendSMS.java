package com.studentsbazaar.studentsbazaarapp.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMS {

    private String phno1;
    private String otp1;
    private Context context;

    public SendSMS(Context context , String phno , String otp) {
        this.context = context;
        this.phno1 = phno;
        this.otp1 = otp;
        new SendMSG().execute();
    }

    private  class SendMSG extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Construct data
                String apiKey = "apikey=" + "Z7XGpTSIh04-GVN4MnCJmtEe9hSyQs9whqArH0xa9r";
                String message = "&message=" + "welcome to studentsbazaar. Your verification code is "+otp1;
                String sender = "&sender=" + "TXTLCL";
                String numbers = "&numbers=" + "91"+phno1;

                // Send data
                HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                String data = apiKey + numbers + message + sender;
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                conn.getOutputStream().write(data.getBytes("UTF-8"));
                final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                }
                rd.close();
                Log.d("Status", stringBuffer.toString());

            } catch (Exception e) {
                Log.d("Status", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}