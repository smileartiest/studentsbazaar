package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.BuildConfig;
import com.studentsbazaar.studentsbazaarapp.Quiz_history_page;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.VerifyPhoneNumber;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.controller.Quiz_Control;
import com.studentsbazaar.studentsbazaarapp.model.Score_Details;

import java.util.List;

import dmax.dialog.SpotsDialog;


public class ProfileActivity extends AppCompatActivity {
TextView meme,events,quiz,logout,username,usermail,userinitial,uiphonests_complete,uiphonests_incomplete,udi,refcode,phno,versionnane;
SpotsDialog spotsDialog;
TextView editicon,sendcodebtn;
ImageView profileback,profilecheck;

List<Score_Details> scoreresponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        meme=findViewById(R.id.profile_memes);
        events=findViewById(R.id.profile_evnets);
        quiz=findViewById(R.id.profile_quiz);
        logout=findViewById(R.id.profile_logout);
        username=findViewById(R.id.profile_name);
        usermail=findViewById(R.id.profile_email);
        userinitial=findViewById(R.id.profile_initial);
        profileback=findViewById(R.id.r_meme_propic);
        phno = findViewById(R.id.uitvprofilephno);
        uiphonests_complete = findViewById(R.id.profile_phno_sts_complete);
        uiphonests_incomplete = findViewById(R.id.profile_phno_sts_incomplete);
        udi = findViewById(R.id.profile_uid);
        refcode= findViewById(R.id.profile_refcode);
        sendcodebtn = findViewById(R.id.profile_refcode_send);
        profilecheck = findViewById(R.id.uitvprofilephno_check);
        versionnane = findViewById(R.id.profile_versionname);
        spotsDialog = new SpotsDialog(ProfileActivity.this);
        editicon = findViewById(R.id.profile_edit);
        new Controller(ProfileActivity.this);
        username.setText(Controller.getusername());
        usermail.setText(Controller.getusermail());
        char initial = Controller.getusername().charAt(0);
        userinitial.setText(String.valueOf(initial).toUpperCase());

        udi.setText(Controller.getUID());
        refcode.setText(Controller.getUID());
        phno.setText(new Controller(ProfileActivity.this).getphno());

        versionnane.setText("StudentsBazaar . version "+ BuildConfig.VERSION_NAME);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Controller.getUID().equals("0")){
            uiphonests_incomplete.setVisibility(View.INVISIBLE);
            uiphonests_complete.setVisibility(View.VISIBLE);
            profilecheck.setVisibility(View.INVISIBLE);
        }else if(new Controller(ProfileActivity.this).getmsts() == null || new Controller(ProfileActivity.this).getmsts().equals("0")){
            uiphonests_complete.setVisibility(View.INVISIBLE);
            uiphonests_incomplete.setVisibility(View.VISIBLE);
        }else{
            uiphonests_complete.setVisibility(View.VISIBLE);
            uiphonests_incomplete.setVisibility(View.INVISIBLE);
            profilecheck.setVisibility(View.INVISIBLE);
        }

        meme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, Mems.class);
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
                getquizdetail();
            }
        });

        if(Controller.getUID().equals("0") || Controller.getprefer().equals(Controller.ADMIN) || Controller.getprefer().equals(Controller.INFOZUB)){
            meme.setVisibility(View.GONE);
            events.setVisibility(View.GONE);
            quiz.setVisibility(View.GONE);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ProfileActivity.this);
                builder.setIcon(R.drawable.newlogo);
                builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                builder.setTitle("Hey , There !");
                builder.setMessage("Are you sure, want to logout this app ?");
                builder.addButton("LOGOUT", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spotsDialog.show();
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                spotsDialog.dismiss();
                                Controller.clearuserdetails();
                                Quiz_Control.clearquizControl();
                                finishAffinity();
                                startActivity(new Intent(getApplicationContext() , Login_Page.class));
                            }
                        }, 2000);
                    }
                });

                builder.addButton("NOT NOW", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        profileback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(ProfileActivity.this,HomeActivity.class);
                finish();
            }
        });
        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Move_Show.showToast("Comming Soon");
            }
        });

        sendcodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Monitor(ProfileActivity.this).sharerefcode(refcode.getText().toString());
            }
        });

        profilecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , VerifyPhoneNumber.class).putExtra("ph", new Controller(ProfileActivity.this).getphno()));
            }
        });

    }

    public void getquizdetail(){
        startActivity(new Intent(ProfileActivity.this , Quiz_history_page.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Move_Show(ProfileActivity.this,HomeActivity.class);
    }
}
