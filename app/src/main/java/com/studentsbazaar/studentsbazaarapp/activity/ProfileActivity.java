package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.controller.Quiz_Control;

import dmax.dialog.SpotsDialog;


public class ProfileActivity extends AppCompatActivity {
TextView meme,events,quiz,logout,username,usermail,userinitial;
SpotsDialog spotsDialog;
ImageView profileback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        meme=(TextView)findViewById(R.id.uitvprofilememes);
        events=(TextView)findViewById(R.id.uitvprofileevnets);
        quiz=(TextView)findViewById(R.id.uitvprofilequiz);
        logout=(TextView)findViewById(R.id.uitvprofilelogout);
        username=(TextView)findViewById(R.id.uitvprofileusername);
        usermail=(TextView)findViewById(R.id.uitvprofileemail);
        userinitial=(TextView)findViewById(R.id.uitvprofileinitial);
        profileback=(ImageView)findViewById(R.id.uiivprofileback);
        spotsDialog = new SpotsDialog(ProfileActivity.this);
        new Controller(ProfileActivity.this);
        username.setText(Controller.getusername());
        usermail.setText(Controller.getusermail());
        char initial = Controller.getusername().charAt(0);
        userinitial.setText(String.valueOf(initial));
        meme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,Mems.class);
                intent.putExtra("apitype","uid");
                startActivity(intent);
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,Pending_Events.class);
                intent.putExtra("apitype","uid");
                startActivity(intent);
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotsDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spotsDialog.dismiss();
                        Controller.clearuserdetails();
                        Quiz_Control.clearquizControl();
                        finishAffinity();
                    }
                }, 2000);
            }
        });
        profileback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(ProfileActivity.this,HomeActivity.class);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Move_Show(ProfileActivity.this,HomeActivity.class);
    }
}
