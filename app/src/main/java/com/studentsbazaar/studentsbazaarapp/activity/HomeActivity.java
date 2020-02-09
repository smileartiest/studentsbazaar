package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.SliderPagerAdapter;
import com.studentsbazaar.studentsbazaarapp.firebase.Config;
import com.studentsbazaar.studentsbazaarapp.helper.PersistanceUtil;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Posters_Details;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView tvEvent, tvPlacement, tvSyllabus, tvMemes, tvQuiz, tvHeadTitle;
    CardView cvEvent, cvPlacement, cvSyllabus, cvMemes, cvQuiz, technews, cvdis, cvabout;
    LinearLayout linear2;
    RelativeLayout rlLayout;
    Dialog d;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    NavigationView navigationView;
    String weburl;
    static String token = "", STRTOKEN = "0";
    Context context;
    private ViewPager vp_slider;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String refreshedToken;
    private LinearLayout ll_dots;
    SpotsDialog spotsDialog;
    SliderPagerAdapter sliderPagerAdapter;
    SharedPreferences spUserDetails;
    SharedPreferences.Editor edit;
    ArrayList<String> slider_image_list;
    List<Posters_Details> posters_details;
    List<Project_details> drawerResponseList = null;
    private TextView[] dots;
    int page_position = 0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        spotsDialog = new SpotsDialog(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tvEvent = findViewById(R.id.tvEventNew);
        tvHeadTitle = findViewById(R.id.head_title);
        tvMemes = findViewById(R.id.tvMeme);
        tvPlacement = findViewById(R.id.tvPlacement);
        tvQuiz = findViewById(R.id.tvQuiz);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");

        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        tvSyllabus = findViewById(R.id.tvSylabus);
        //sendSMSMessage();
        linear2 = findViewById(R.id.linear2);
        checkToken();
        context = this;

        cvEvent = findViewById(R.id.cardview2new);
        cvPlacement = findViewById(R.id.cvplaced);
        cvSyllabus = findViewById(R.id.cardview4);
        cvMemes = findViewById(R.id.cardview5);
        cvQuiz = findViewById(R.id.cardview6);
        cvdis = findViewById(R.id.cvdis);
        cvabout = findViewById(R.id.cvabut);
        technews = findViewById(R.id.cardviewtechnews);

       // rlLayout.setVisibility(View.GONE);

        spUserDetails = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        edit = spUserDetails.edit();

        if (spUserDetails.getString("recentevent", null) == null) {

         //   loadData();
        }

        init();

        // method for adding indicators
      //  addBottomDots(0);

        final Handler handler = new Handler();
/*
        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };*/

   /*     new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);*/

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
        technews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intEvent = new Intent(HomeActivity.this, Tech_News.class);
                startActivity(intEvent);
            }
        });

        cvQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spUserDetails.getString("log", "").equals("reg") || spUserDetails.getString("log", "").equals("admin")) {
                    Intent intEvent = new Intent(HomeActivity.this, Quiz_Events.class);
                    startActivity(intEvent);
                } else {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(HomeActivity.this);
                    builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                    builder.setTitle("Hey there ! Do Register!");
                    builder.setMessage("We will monitor your score and will give surprise for you.");
                    builder.addButton("OKAY", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent i = new Intent(HomeActivity.this, SignUp.class);
                            startActivity(i);

                        }
                    });

                    builder.addButton("NO", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Toast.makeText(SplashActivity.this, "Upgrade tapped", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

//
            }
        });
        cvdis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.putString("meme", "user").apply();
                Intent intEvent = new Intent(HomeActivity.this, Mems.class);
                startActivity(intEvent);
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
                b.putString("title", "RESULTS-AU");
                b.putString("data", "RESULTS-AU");
                Intent intEvent = new Intent(HomeActivity.this, WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);
            }
        });


        cvSyllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//

                Intent intEvent = new Intent(HomeActivity.this, MUActivity.class);

                startActivity(intEvent);


            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:

             /*   starlotActivity(new Intent(JobList.this, JobList.class));
                finish();*/
                break;

            case R.id.nav_sigin:

                startActivity(new Intent(HomeActivity.this, MainActivity.class));
               // finish();
                break;
            case R.id.nav_signup:

                startActivity(new Intent(HomeActivity.this, SignUp.class));
               // finish();
                break;

            case R.id.nav_disclaimer:

                startActivity(new Intent(HomeActivity.this, DisclaimerActivity.class));
               // finish();
                break;

        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragment_container, fragment);

            //    mMapView.onResume();

            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void init() {

       /* setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().hide();*/

        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        requestPermission();






//Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
// here i am adding few sample image links, you can add your own
   /*     Call<DownloadResponse> call = ApiUtil.getServiceClass().getposters(ApiUtil.GET_POSTERS);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                if (response.isSuccessful()) {
                    posters_details = response.body().getPosters_details();
                    for (int i = 0; i < posters_details.size(); i++) {
                        Log.d("posters", String.valueOf(posters_details.get(i).getPoster()));
                        slider_image_list.add(String.valueOf(posters_details.get(i).getPoster()));
                    }
                    sliderPagerAdapter = new SliderPagerAdapter(HomeActivity.this, slider_image_list);
                    vp_slider.setAdapter(sliderPagerAdapter);

                }
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {

            }
        });*/


      /*  vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });*/
    }

    private void checkToken() {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                refreshedToken = instanceIdResult.getToken();
                Log.i("newToken", refreshedToken);
                PersistanceUtil.setUserID(refreshedToken);
                // Saving reg id to shared preferences
                storeRegIdInPref(refreshedToken);
                Config.setPrefToken(context, refreshedToken);
                // Notify UI that registration has completed, so the progress indicator can be hidden.
                //token = refreshedToken;
                Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
                registrationComplete.putExtra("token", refreshedToken);
                LocalBroadcastManager.getInstance(context).sendBroadcast(registrationComplete);
                STRTOKEN = Config.getPrefToken(context);
                Log.d("Token", STRTOKEN);
                if (!STRTOKEN.equals("0")) {
                    if (!spUserDetails.getString("TOKEN_STAT", "").equals("sent")) {
                        pushToken(Config.getPrefToken(context));
                    } else {
                        Log.d("TOKEN_STAT", "Already token sent.");
                    }

                }
            }
        });


    }

    private void pushToken(String token) {

        Log.d("UNIQTOKEN", token + "");
        spotsDialog.show();
        Call<String> call = ApiUtil.getServiceClass().updatetoken(token, spUserDetails.getString("UID", ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                edit.putString("TOKEN_STAT", "sent");
                edit.apply();
                assert response.body() != null;
                Log.d("RESPONSELOG", response.body());
                spotsDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("FAILURE_RESPONSE", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }


/*    private void addBottomDots(int currentPage) {
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
    }*/

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

    void loadData() {
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_RECENT_EVENTS);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {

                drawerResponseList = response.body().getProject_details();

                d = new Dialog(HomeActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setCancelable(false);
                d.setContentView(R.layout.results_design);
                ImageView imageView = d.findViewById(R.id.recentposter);
                TextView title = d.findViewById(R.id.recenttilte);
                TextView venue = d.findViewById(R.id.recentvenue);
                TextView day = d.findViewById(R.id.recent_start_date);
                TextView month = d.findViewById(R.id.recent_start_month);
                TextView apply = d.findViewById(R.id.recentapply);
                Button view = d.findViewById(R.id.recentview);
                Button later = d.findViewById(R.id.recentlater);

                Glide.with(HomeActivity.this).load(drawerResponseList.get(0).getPoster()).into(imageView);
                title.setText(drawerResponseList.get(0).getEvent_Title());
                venue.setText(drawerResponseList.get(0).getEvent_Organiser() + "," + drawerResponseList.get(0).getCollege_Address());
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(drawerResponseList.get(0).getEvent_Start_Date());
                    String[] sdate = date1.toString().split(" ");
                    if (sdate[0].equals("Sat") || sdate[0].equals("Sun")) {
                        day.setTextColor(Color.RED);
                        month.setTextColor(Color.RED);

                    } else {
                        day.setTextColor(Color.parseColor("#1B4F72"));
                        month.setTextColor(Color.parseColor("#1B4F72"));
                    }
                    day.setText(sdate[0]);
                    month.setText(sdate[1] + " " + sdate[2]);
                } catch (Exception e) {

                }
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (drawerResponseList.get(0).getEvent_Website().contains("http://") || drawerResponseList.get(0).getEvent_Website().contains("https://")){

                            weburl =drawerResponseList.get(0).getEvent_Website();
                        }else{
                            weburl ="http://"+drawerResponseList.get(0).getEvent_Website();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("url",weburl);
                        bundle.putString("data","reg url");
                        bundle.putString("title","reg title");
                        Intent in = new Intent(context, WebActivity.class);
                        in.putExtras(bundle);
                        context.startActivity(in);
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edit.putString("recentevent", "view").apply();
                        edit.putString("PREFER", "MORE").apply();
                        SharedPreferences sharedPreferences = context.getSharedPreferences("view_details", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("coid", drawerResponseList.get(0).getEvent_Coordinator());
                        editor.putString("post", drawerResponseList.get(0).getPoster());
                        editor.putString("title", drawerResponseList.get(0).getEvent_Title());
                        editor.putString("cat", drawerResponseList.get(0).getEvent_Type());
                        editor.putString("sdate", drawerResponseList.get(0).getEvent_Start_Date());
                        editor.putString("edate", drawerResponseList.get(0).getEvent_End_Date());
                        editor.putString("organiser", drawerResponseList.get(0).getEvent_sponsors());
                        editor.putString("city", drawerResponseList.get(0).getCollege_District());
                        editor.putString("state", drawerResponseList.get(0).getCollege_State());
                        editor.putString("dis", drawerResponseList.get(0).getEvent_Discription());
                        editor.putString("Eventdetails", drawerResponseList.get(0).getEvent_Details());
                        editor.putString("dept", drawerResponseList.get(0).getDept());
                        editor.putString("guest", drawerResponseList.get(0).getEvent_guest());
                        editor.putString("pronites", drawerResponseList.get(0).getEvent_pro_nites());
                        editor.putString("etheme", drawerResponseList.get(0).getEvent_Name());
                        editor.putString("accom", drawerResponseList.get(0).getEvent_accomodations());
                        editor.putString("lastdate", drawerResponseList.get(0).getLast_date_registration());
                        editor.putString("fees", drawerResponseList.get(0).getEntry_Fees());
                        editor.putString("htr", drawerResponseList.get(0).getEvent_how_to_reach());
                        editor.putString("cpname1", drawerResponseList.get(0).getContact_Person1_Name());
                        editor.putString("cpno1", drawerResponseList.get(0).getContact_Person1_No());
                        editor.putString("cpname2", drawerResponseList.get(0).getContact_Person2_Name());
                        editor.putString("cpno2", drawerResponseList.get(0).getContact_Person2_No());
                        editor.putString("webevent", drawerResponseList.get(0).getEvent_Website());
                        editor.putString("webcoll", drawerResponseList.get(0).getCollege_Website());
                        editor.putString("view", "view");
                        editor.apply();
                        Intent intent = new Intent(HomeActivity.this, View_Details.class);
                        startActivity(intent);
                        d.dismiss();
                    }
                });
                later.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edit.putString("recentevent", "later").apply();
                        d.dismiss();
                    }
                });


                d.show();


            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {

            }
        });

    }



}
