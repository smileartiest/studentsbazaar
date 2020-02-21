package com.studentsbazaar.studentsbazaarapp.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class Quiz_Control {
    public Context context;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public Quiz_Control(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("QUIZ_CONTROL", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static void addTotalPoint(String total) {
        editor.putString("total", total).apply();
    }
    public static void addCorrectans(String correct) {
        editor.putString("correct", correct).apply();
    }
    public static void addworngans(String wrong) {
        editor.putString("wrong", wrong).apply();
    }


    public static String getTotalPoint() {
        return sharedPreferences.getString("total", null);
    }
    public static String getCorrectans(){
        return sharedPreferences.getString("correct", null);
    }
    public static String getwrongans(){
        return sharedPreferences.getString("wrong", null);
    }

}
