package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.CheckUserNumber;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.ViewPagerAdapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.helper.DepthPageTransformer;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class EventActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ViewPagerAdapter mAdapter;
    LinearLayout eventcard;

    SpotsDialog progressDialog;
    List<Project_details> drawerResponseList = null;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);
        progressDialog = new SpotsDialog(this);
        layout = (LinearLayout) findViewById(R.id.empty2);

        new Controller(this);
        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        loadData();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadData() {
        progressDialog.show();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.LOAD_EVENTS);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, retrofit2.Response<DownloadResponse> response) {

                Log.d("RESPONSE1", response.message().toString());

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    drawerResponseList = response.body().getProject_details();
                    Log.d("RESPONSE2", drawerResponseList.toString());
                    progressDialog.dismiss();
                    if (drawerResponseList.size() == 0) {
                        layout.setVisibility(View.VISIBLE);
                        viewPager2.setVisibility(View.INVISIBLE);
                    } else {
                        layout.setVisibility(View.INVISIBLE);
                        viewPager2.setVisibility(View.VISIBLE);
                        mAdapter = new ViewPagerAdapter(EventActivity.this, drawerResponseList);
                        viewPager2.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                //showErrorMessage();
                Log.d("RESPONSE3", "err" + t.getMessage());
            }
        });

    }

    void addeventprocess(){
        if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.ADMIN)) {
            new Move_Show(EventActivity.this, AddEvent2.class);
        } else {
            addEvent();
        }
    }

    void contactdetails(){
        new Move_Show(EventActivity.this, ContactActivity.class);
    }

    private void addEvent() {
        if (Controller.getprefer().equals(Controller.VISITOR)) {
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(EventActivity.this);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION);
            builder.setIcon(R.drawable.newlogo);
            builder.setTitle("Hey there , Do Register !");
            builder.setMessage("Kindly fill your details to continue adding event.");

            builder.addButton("LOGIN", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new Move_Show(EventActivity.this, Login_Page.class);
                }
            });

            builder.addButton("REGISTER", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(EventActivity.this , CheckUserNumber.class));
                }
            });

            builder.addButton("NOT NOW", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            new Move_Show(EventActivity.this, AddEvent2.class);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Move_Show(EventActivity.this,HomeActivity.class);
        finish();
    }
}
