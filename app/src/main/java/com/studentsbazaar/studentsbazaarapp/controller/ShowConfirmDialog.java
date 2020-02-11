package com.studentsbazaar.studentsbazaarapp.controller;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studentsbazaar.studentsbazaarapp.R;

public class ShowConfirmDialog {

    public Context context;
    public Dialog d;
    public static TextView textView;
    public static Button button;
    public static ProgressBar progressBar;

    public ShowConfirmDialog(Context context, String donetxt) {
        this.context = context;
        d = new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.webalert);
        TextView textView = d.findViewById(R.id.donetext);
        textView.setText(donetxt);
        button = d.findViewById(R.id.donebtn);
        progressBar = d.findViewById(R.id.doneprogressBar);
        button.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
            }
        }, 3000);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();

    }


}
