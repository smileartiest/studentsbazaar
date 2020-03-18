package com.studentsbazaar.studentsbazaarapp.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class Quiz_Control {
    public Context context;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static  String SEEN="seen";
    public static String LATER="later";
    public static String ATTEND="attend";

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

    public static void addQuizStatus(String attend) {
        editor.putString("attend", attend).apply();
    }

    public static void addseenquiz(String seen) {
        editor.putString("seen", seen).apply();
    }
    public static void clearquizControl(){
        editor.clear();
        editor.apply();
    }

    public static void addquizquestion(String seen) {
        editor.putString("QUIZ", seen).apply();
    }

    public static void addcrctans(String seen) {
        editor.putString("crctans", seen).apply();
    }

    public static String getTotalPoint() {
        return sharedPreferences.getString("total", null);
    }

    public static String getCorrectans() {
        return sharedPreferences.getString("correct", null);
    }

    public static String getwrongans() {
        return sharedPreferences.getString("wrong", null);
    }

    public static String getQuizstatus() {
        return sharedPreferences.getString("attend", null);
    }

    public static String getseenquiz() {
        return sharedPreferences.getString("seen", null);
    }
    public static String getQuizquestion() {
        return sharedPreferences.getString("QUIZ", null);
    }
    public static String getcrctans() {
        return sharedPreferences.getString("crctans", null);
    }
}
