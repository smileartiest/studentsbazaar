package com.studentsbazaar.studentsbazaarapp.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.studentsbazaar.studentsbazaarapp.R;

public class Move_Show {

    private static Context context;


    public Move_Show(Context context, Class classname) {
        this.context = context;
        Intent intent = new Intent(context, classname);
        context.startActivity(intent);
    }

    public static void showToast(String message) {
        Toast toast = Toast.makeText(context,
                message + "...!!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        toast.getView().setBackgroundResource(R.drawable.button);
        toast.getView().setElevation(10);
        toast.getView().setSoundEffectsEnabled(true);
        toast.getView().setPadding(10, 10, 10, 10);
        v.setTextColor(Color.parseColor("#FFFFFF"));
        v.setTextSize(16);
        v.setGravity(Gravity.CENTER);
        v.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        toast.show();
    }

}
