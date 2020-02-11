package com.studentsbazaar.studentsbazaarapp.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Move_Show {

    private static Context context;


    public Move_Show(Context context, Class classname) {
        this.context = context;
        Intent intent = new Intent(context, classname);
        context.startActivity(intent);
    }


    public static void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
