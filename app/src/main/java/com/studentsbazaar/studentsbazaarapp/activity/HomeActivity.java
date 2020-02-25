package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.SliderPagerAdapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.controller.Quiz_Control;
import com.studentsbazaar.studentsbazaarapp.firebase.Config;
import com.studentsbazaar.studentsbazaarapp.helper.PersistanceUtil;
import com.studentsbazaar.studentsbazaarapp.model.Posters_Details;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.text.SimpleDateFormat;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvEvent, tvPlacement, tvSyllabus, tvMemes, tvQuiz, tvHeadTitle;
    CardView cvEvent, cvPlacement, cvSyllabus, cvMemes, cvQuiz, technews, cvdis, cvabout;
    LinearLayout linear2;
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
    ArrayList<String> slider_image_list;
    List<Posters_Details> posters_details;
    List<Project_details> drawerResponseList = null;
    private TextView[] dots;
    int page_position = 0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    int CURRENT_TIME;
    int LOCAL_TIME=18;
    int LIMIT_TIME=23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        spotsDialog = new SpotsDialog(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        new Controller(this);
        new Quiz_Control(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tvEvent = findViewById(R.id.tvEventNew);
        tvHeadTitle = findViewById(R.id.head_title);
        tvMemes = findViewById(R.id.tvMeme);
        tvPlacement = findViewById(R.id.tvPlacement);
        tvQuiz = findViewById(R.id.tvQuiz);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            Calendar calander = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
            String time = simpleDateFormat.format(calander.getTime());
            Log.d("Time",time);
            CURRENT_TIME = Integer.valueOf(time);
            if (LOCAL_TIME < CURRENT_TIME && CURRENT_TIME<LIMIT_TIME) {
                if (Quiz_Control.getQuizstatus()==null && Quiz_Control.getseenquiz()==null) {

                }
                else if (Quiz_Control.getQuizstatus().equals(Quiz_Control.ATTEND) && Quiz_Control.getseenquiz().equals(Quiz_Control.LATER)){
                    new Move_Show(HomeActivity.this,Quiz_Events.class);
                }
            }
        }

        //new ShowConfirmDialog(HomeActivity.this,"please a wait a min");
        //ShowConfirmDialog.textView.setVisibility();
        if (Controller.getprefer().equals(Controller.VISITOR)) {
            if (Controller.getuservierify() ==null){
                verifyaccount();
            }

        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");

        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        tvSyllabus = findViewById(R.id.tvSylabus);

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
//        spUserDetails = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
//        edit = spUserDetails.edit();
//
//        if (spUserDetails.getString("recentevent", null) == null) {
//
//            //   loadData();
//        }
        init();

        // method for adding indicators
        //  addBottomDots(0);

//        final Handler handler = new Handler();
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
                new Move_Show(HomeActivity.this, IntroActivity.class);
            }
        });

        cvPlacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(HomeActivity.this, PlacementActivity.class);
            }
        });
        technews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Move_Show(HomeActivity.this, Tech_News.class);
            }
        });

        cvQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.ADMIN) || Controller.getprefer().equals(Controller.INFOZUB)) {
                    new Move_Show(HomeActivity.this, Quiz_Events.class);
                } else {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(HomeActivity.this);
                    builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                    builder.setTitle("Hey there ! Do Register!");
                    builder.setMessage("We will monitor your score and will give surprise for you.");
                    builder.addButton("OKAY", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new Move_Show(HomeActivity.this, SignUp.class);

                        }
                    });

                    builder.addButton("NO", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

            }
        });
        cvdis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.adddesignprefer(Controller.PREFER);
                new Move_Show(HomeActivity.this, Mems.class);
            }
        });
        cvabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("url", "http://uniqsolutions.co.in/Admin/Files/Tech_Video.php");
                b.putString("title", "INTERESTING VIDEOS");
                b.putString("data", "INTERESTING VIDEOS");
                Intent intEvent = new Intent(HomeActivity.this, WebActivity.class);
                intEvent.putExtras(b);
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
                new Move_Show(HomeActivity.this, MUActivity.class);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_placement_menu, menu);
        menu.findItem(R.id.item1).setVisible(false);
        menu.findItem(R.id.item2).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.shareitem:
                try {
                    new Monitor(this).sharetowhatsapp();
                } catch (Exception e) {

                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_sigin:
                new Move_Show(HomeActivity.this, MainActivity.class);
                break;
            case R.id.nav_signup:
                new Move_Show(HomeActivity.this, SignUp.class);
                break;

            case R.id.nav_disclaimer:
                new Move_Show(HomeActivity.this, DisclaimerActivity.class);
                break;
            case R.id.nav_logout:
                spotsDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Controller(HomeActivity.this).addprefer(Controller.VISITOR);
                        spotsDialog.dismiss();
                        new Move_Show(HomeActivity.this, HomeActivity.class);
                    }
                }, 2000);
                break;
            case R.id.nav_aboutus:
                Bundle b = new Bundle();
                b.putString("url", "https://www.studentsbazaar.in/about-us/");
                b.putString("title", "ABOUT US");
                Intent intEvent = new Intent(HomeActivity.this, WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);

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
        if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.ADMIN)) {
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
        }
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
                if (!STRTOKEN.equals("0")) {
                    if (Controller.getTokenstatus() == null) {
                        pushToken(Config.getPrefToken(context));
                        Log.d("TOKEN", Config.getPrefToken(context));
                    } else if (Controller.getTokenstatus().equals(Controller.SENT)){
                        Log.d("TOKEN", Config.getPrefToken(context));
                    }

                }
            }
        });


    }

    private void pushToken(String token) {
        Log.d("TOKEN", "check");
        spotsDialog.show();
        Call<String> call = ApiUtil.getServiceClass().updatetoken(token, Controller.getUID());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Controller.addTokenStatus(Controller.SENT);
                assert response.body() != null;
                spotsDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
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

    private void verifyaccount() {
        Call call = ApiUtil.getServiceClass().getaccountverification(Controller.getUID(), Controller.getDIVID());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
               if (!response.body().equals("0")){
                   displayaccountstatus(response.body().toString());
               }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void displayaccountstatus(String name) {
        Dialog d = new Dialog(HomeActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.account_verification);
        d.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        d.show();
        TextView accountholdername=(TextView)d.findViewById(R.id.uitvaccountholdername);
        Button okbtn=(Button)d.findViewById(R.id.uibtnaccountlogin);
        Button laterbtn=(Button)d.findViewById(R.id.uibtnaccountlater);
        accountholdername.setText("Welcome Back , "+name);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(HomeActivity.this, MainActivity.class);
            }
        });
        laterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.adduservierify(Controller.USERVERIFY);
                d.dismiss();


            }
        });
    }


}
