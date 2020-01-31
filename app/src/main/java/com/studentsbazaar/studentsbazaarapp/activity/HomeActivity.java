package com.studentsbazaar.studentsbazaarapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.SliderPagerAdapter;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Posters_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends AppCompatActivity {

    TextView tvProfile, tvEvent, tvPlacement, tvSyllabus, tvMemes, tvQuiz, tvHeadTitle;
    Typeface tf_regular;
    CardView cvEvent, cvPlacement, cvSyllabus, cvMemes, cvQuiz, cvProfile,cvdis,cvabout;
    LinearLayout linear1, linear2;
    private ViewPager vp_slider;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    SharedPreferences spUserDetails;
    ArrayList<String> slider_image_list;
    List<Posters_Details> posters_details;
    private TextView[] dots;
    int page_position = 0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), "caviar.ttf");
        tvEvent = findViewById(R.id.tvEventNew);
        tvHeadTitle = findViewById(R.id.head_title);
        tvMemes = findViewById(R.id.tvMeme);
        tvPlacement = findViewById(R.id.tvPlacement);
        tvProfile = findViewById(R.id.tvTitle);
        tvQuiz = findViewById(R.id.tvQuiz);
        tvSyllabus = findViewById(R.id.tvSylabus);
        //sendSMSMessage();
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);

        tvEvent.setTypeface(tf_regular);
        tvHeadTitle.setTypeface(tf_regular);
        tvMemes.setTypeface(tf_regular);
        tvPlacement.setTypeface(tf_regular);
        tvProfile.setTypeface(tf_regular);
        tvQuiz.setTypeface(tf_regular);
        tvSyllabus.setTypeface(tf_regular);

        cvEvent = findViewById(R.id.cardview2new);
        cvProfile = findViewById(R.id.cardview);
        cvPlacement = findViewById(R.id.cvplaced);
        cvSyllabus = findViewById(R.id.cardview4);
        cvMemes = findViewById(R.id.cardview5);
        cvQuiz = findViewById(R.id.cardview6);
        cvdis=findViewById(R.id.cvdis);
        cvabout=findViewById(R.id.cvabut);

        spUserDetails = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);

        if (spUserDetails.getString("log", "").equals("visitor") || spUserDetails.getString("log", "").equals("admin")) {
            updateView();
        }


        init();

        // method for adding indicators
        addBottomDots(0);

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);

        cvEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEvent = new Intent(HomeActivity.this, IntroActivity.class);
                startActivity(intEvent);
            }
        });

        cvPlacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bundle b = new Bundle();
                b.putString("url","http://www.studentsbazaar.in/placements/");
                b.putString("title","PLACEMENT");*/
                Intent intEvent = new Intent(HomeActivity.this, PlacementActivity.class);
                //intEvent.putExtras(b);
                startActivity(intEvent);
            }
        });

        cvQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEvent = new Intent(HomeActivity.this, Quiz_Events.class);
                    startActivity(intEvent);
//                SharedPreferences sharedPreferences = getSharedPreferences("QUIZ_STATUS", MODE_PRIVATE);
//                if (sharedPreferences.getString("QUIZ", "").isEmpty()) {
//                    Intent intEvent = new Intent(HomeActivity.this, Quiz_Events.class);
//                    startActivity(intEvent);
//                }else {
//
//                    getAlertwindow("Today Competition Completed...\nplease wait for your results...");
//
//                }
            }
        });
        cvdis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cvabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intEvent = new Intent(HomeActivity.this, DisclaimerActivity.class);
                startActivity(intEvent);
            }
        });

        cvMemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("url", "https://coe1.annauniv.edu/home/");
                b.putString("title", "RESULTS");
                b.putString("data", "RESULTS");
                Intent intEvent = new Intent(HomeActivity.this, WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);
                Toast.makeText(getApplicationContext(), "Still working..", Toast.LENGTH_SHORT).show();
            }
        });

        cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Bundle b = new Bundle();
                b.putString("url","");
                b.putString("title","PLACEMENT");
                Intent intEvent =  new Intent(HomeActivity.this,WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);*/
                Toast.makeText(getApplicationContext(), "Still working..", Toast.LENGTH_SHORT).show();
            }
        });

        cvSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("url", "https://www.unom.ac.in/");
                b.putString("title", "RESULTS");
                b.putString("data", "RESULTS");
                Intent intEvent = new Intent(HomeActivity.this, WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);
            }
        });

    }

    private void updateView() {

        linear1.setVisibility(View.GONE);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        param.leftMargin = 40;
        linear2.setLayoutParams(param);

    }


    private void init() {

       /* setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().hide();*/

        requestPermission();

        getColleges();
        vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);

        slider_image_list = new ArrayList<>();

//Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
// here i am adding few sample image links, you can add your own
        Call<DownloadResponse> call= ApiUtil.getServiceClass().getposters(ApiUtil.GET_POSTERS);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response)

            {
                if (response.isSuccessful()) {
                    posters_details = response.body().getPosters_details();
                    for (int i = 0; i < posters_details.size(); i++) {
                        Log.d("posters",String.valueOf(posters_details.get(i).getPoster()));
                        slider_image_list.add(String.valueOf(posters_details.get(i).getPoster()));
                    }
                    sliderPagerAdapter = new SliderPagerAdapter(HomeActivity.this, slider_image_list);
                    vp_slider.setAdapter(sliderPagerAdapter);

                }
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {

            }
        });




        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getColleges() {
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
//                boolean locationAccepted = false, locAccepted = false, coaseAccepted = false, smsAccepted = false;
//                if (grantResults.length > 0) {
//                    locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    locAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    coaseAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
//                    smsAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
//
//                    if (locationAccepted && locAccepted && coaseAccepted && smsAccepted) {
//                        // userService.getDownloadingService();
//                        System.out.println("Permission Granted, Now you can access location data and camera.");
//                    } else {
//                        // userService.getDownloadingService();
//                        // openDialog();
//                        System.out.println("Permission Granted, Now you can access location data and camera.");
//                    }
//                }
//
//                if (locationAccepted && locAccepted && coaseAccepted && smsAccepted) {
//                    // userService.getDownloadingService();
//                    System.out.println("Permission Granted, Now you can access location data and camera.");
//                } else {
//                    // userService.getDownloadingService();
//                    openDialog();
//                    System.out.println("Permission Granted, Now you can access location data and camera.");
//                }
                break;
        }
    }




    private void openDialog() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("Hey there ! Permission Denied!");
        builder.setMessage("Without this permission the app is unable to share the content to your friends,unable to give accurate results by using location.");
        builder.addButton("RE-TRY", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermission();
                dialog.dismiss();
            }
        });

        builder.addButton("I'M SURE", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(SplashActivity.this, "Upgrade tapped", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        builder.show();
    }

    void getAlertwindow(String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(message);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
