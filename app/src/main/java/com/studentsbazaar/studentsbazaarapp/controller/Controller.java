package com.studentsbazaar.studentsbazaarapp.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class Controller {
    public Context context;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String VISITOR = "visitor";
    public static String ADMIN = "admin";
    public static String REG = "reg";
    public static String INITIAL = "0";
    public static String SENT = "sent";
    public static String PREFER = "prefer";
    public static String MORE = "more";
    public static String INFOZUB = "infozub";
    public static String MEMEACCEPT = "meme";
    public static String MEMEVIEW = "memeview";
    public static String USERVERIFY = "later";

    public Controller(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static void addprefer(String type) {
        editor.putString("log", type).apply();
    }

    public static void addDIVID(String id) {
        editor.putString("DEV_ID", id).apply();
    }

    public static void addUserID(String user) {
        editor.putString("UID", user).apply();
    }

    public static void addTokenStatus(String token) {
        editor.putString("TOKEN_STAT", token).apply();
    }

    public static void adduservierify(String later) {
        editor.putString("LATER", later).apply();
    }

    public static void adddesignprefer(String status) {
        editor.putString("PREFER", status).apply();
    }

    public static void addeventid(String eventid) {
        editor.putString("EVENT", eventid).apply();
    }

    public static void addupdateview(String view) {
        editor.putString("MEMEVIEW", view).apply();
    }

    public static String getprefer() {
        return sharedPreferences.getString("log", null);
    }

    public static String getDIVID() {
        return sharedPreferences.getString("DEV_ID", null);
    }

    public static String getUID() {
        return sharedPreferences.getString("UID", null);
    }

    public static String getTokenstatus() {
        return sharedPreferences.getString("TOKEN_STAT", null);
    }

    public static String getdesignprefer() {
        return sharedPreferences.getString("PREFER", null);
    }

    public static String getmemeview() {
        return sharedPreferences.getString("MEMEVIEW", null);
    }

    public static String getevent() {
        return sharedPreferences.getString("EVENT", null);
    }
    public static String getuservierify(){
        return sharedPreferences.getString("LATER", null);
    }
}
