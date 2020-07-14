package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.model.College_Details;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;
import com.studentsbazaar.studentsbazaarapp.service.BroadcastService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivty extends AppCompatActivity {
    List<College_Details> college_details;
    String androidId;
    ConstraintLayout main_screen,no_internet_screen;
    TextView message,retry_btn;
    LottieAnimationView animationView;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Controller(SplashActivty.this);
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        main_screen = findViewById(R.id.splase_screen);
        no_internet_screen = findViewById(R.id.splace_no_internet);
        message = findViewById(R.id.splace_message);
        retry_btn = findViewById(R.id.splace_retry_btn);
        animationView = findViewById(R.id.splace_animation_view);

        connectionverify();

        startService(new Intent(this, BroadcastService.class));

    }

    private void collegedetails() {

        Call<DownloadResponse> call = ApiUtil.getServiceClass().getcollegedetails(ApiUtil.GET_COLLEGES);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                college_details = response.body().getCollege_Details();
                for (int i = 0; i < college_details.size(); i++) {
                    ApiUtil.COLLEGEARRAY.add(college_details.get(i).getCollege_Name());
                }
                if(Controller.getprefer()==null){
                    Controller.addprefer(Controller.VISITOR);
                }
                new Move_Show(SplashActivty.this, HomeActivity.class);
                finish();
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void connectionverify() {
        if (isNetworkAvailable()) {
            checkservice();
        } else {
            main_screen.setVisibility(View.GONE);
            no_internet_screen.setVisibility(View.VISIBLE);
            message.setText("Internet Connection is not available. Please switch on internet !");
            animationView.setAnimation(R.raw.internet_gif);
            animationView.playAnimation();
            animationView.loop(true);
            retry_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connectionverify();
                }
            });
        }

    }

    void checkservice(){

        Call<String> call = ApiUtil.getServiceClass().getserversts(ApiUtil.GET_SERVER_STATUS);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body().equals("0")){
                        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        Controller.addDIVID(androidId);
                        collegedetails();
                    }else {
                        main_screen.setVisibility(View.GONE);
                        no_internet_screen.setVisibility(View.VISIBLE);
                        message.setText("Server Maintenance.So Please Try Again After some time. !");
                        animationView.setAnimation(R.raw.server_maintain);
                        animationView.playAnimation();
                        animationView.loop(true);
                        retry_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("splase error " , t.getMessage());
            }
        });

    }

}
