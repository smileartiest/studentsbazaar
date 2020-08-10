package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.studentsbazaar.studentsbazaarapp.CheckUserNumber;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.controller.Quiz_Control;
import com.studentsbazaar.studentsbazaarapp.firebase.Config;
import com.studentsbazaar.studentsbazaarapp.fragment.Frag_home;
import com.studentsbazaar.studentsbazaarapp.helper.PersistanceUtil;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Profile_Details;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;
import com.studentsbazaar.studentsbazaarapp.service.BroadcastService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.BuildConfig;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    LinearLayout linear2;
    Dialog d;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    NavigationView navigationView;
    static String token = "", STRTOKEN = "0";
    Context context;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String refreshedToken;
    SpotsDialog spotsDialog;
    int CURRENT_TIME;
    int LOCAL_TIME = 19;
    int LIMIT_TIME = 23;
    List<Profile_Details> getlogindetails = null;
    List<Quiz_Details> drawerResponseList = null;
    String uidata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spotsDialog = new SpotsDialog(this);
        setContentView(R.layout.login_main_page);
        loadfragment(new Frag_home());
        new Controller(this);
        new Quiz_Control(this);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Calendar calander = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
            String time = simpleDateFormat.format(calander.getTime());
            Log.d("Time", time);
            CURRENT_TIME = Integer.valueOf(time);
            if (LOCAL_TIME <= CURRENT_TIME && CURRENT_TIME <= LIMIT_TIME) {
                if (Quiz_Control.getQuizstatus() == null && Quiz_Control.getseenquiz() == null) {
                    Log.d("Time", time);
                } else if (Quiz_Control.getQuizstatus().equals(Quiz_Control.ATTEND) && Quiz_Control.getseenquiz().equals(Quiz_Control.LATER)) {
                    openDialog();
                }
            }

        }
        if (Controller.getprefer().equals(Controller.VISITOR)) {
            if (Controller.getuservierify() == null) {
                verifyaccount();
            }
        }
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        linear2 = findViewById(R.id.linear2);
        checkToken();
        context = this;
        init();

        Call<DownloadResponse> call = ApiUtil.getServiceClass().getQuizQuestions("1");
        call.enqueue(new Callback<DownloadResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.d("RESPONSE2", response.body().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    drawerResponseList = response.body().getQuiz_details();
                    if (drawerResponseList.get(0).getId() != null) {
                        Log.d("RESPONSE2", drawerResponseList.get(0).getId());
                    } else {
                        if (drawerResponseList.size() == 0) {
                        } else {
                            Quiz_Control.addviewrs(String.valueOf(drawerResponseList.get(0).getViewers()));
                            Log.d("Viewers " , ""+drawerResponseList.get(0).getViewers());
                            new Quiz_Control(HomeActivity.this).addresult(drawerResponseList.get(0).getCrct_Ans() ,drawerResponseList.get(0).getQuiz_Id() ,drawerResponseList.get(0).getQuiz_ques() ,drawerResponseList.get(0).getQuiz_Ans());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                //showErrorMessage();
                Log.d("RESPONSE3", "err" + t.getMessage());
            }
        });

        Call<String> call1 = ApiUtil.getServiceClass().getupdateapp(ApiUtil.GET_UPDATE);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(BuildConfig.VERSION_NAME.equals(response.body().toString())){}else{ update_service(response.body()); }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error" , t.getMessage());
            }
        });

        findViewById(R.id.main_contact_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(HomeActivity.this, ContactActivity.class);
            }
        });

    }

    public void loadfragment(Fragment frag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_framelayout, frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //this method action fro update notification to user.
    public void update_service(String version1){
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION);
        if(Controller.getusername()!= null){
            builder.setTitle("Hai , "+Controller.getusername()+" !");
        }else{
            builder.setTitle("Hai , User ! ");
        }
        builder.setMessage("Your current version is "+ BuildConfig.VERSION_NAME +". available version is "+version1+" . Update your application now.");
        builder.addButton("UPDATE", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_placement_menu, menu);

        if(Controller.getprefer().equals(Controller.REG)){
            menu.findItem(R.id.profile).setVisible(true);
        }else{
            menu.findItem(R.id.profile).setVisible(false);
        }
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

            case R.id.profile:
                new Move_Show(HomeActivity.this,ProfileActivity.class);
                finish();
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
                new Move_Show(HomeActivity.this, HomeActivity.class);

                break;
            case R.id.nav_sigin:
                new Move_Show(HomeActivity.this, Login_Page.class);

                break;
            case R.id.nav_signup:
                new Move_Show(HomeActivity.this, CheckUserNumber.class);

                break;

            case R.id.nav_disclaimer:
                new Move_Show(HomeActivity.this, DisclaimerActivity.class);

                break;
            case R.id.nav_logout:
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
                builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                builder.setIcon(R.drawable.newlogo);
                builder.setTitle("Hey , There !");
                builder.setMessage("Are you sure, want to logout from this app ?");
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
                builder.addButton("CANCEL", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.nav_aboutus:
                Bundle b = new Bundle();
                b.putString("url", "https://www.studentsbazaar.in/about-us/");
                b.putString("title", "ABOUT US");
                b.putString("data", "ABOUT US");
                Intent intEvent = new Intent(HomeActivity.this, WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void init() {
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.ADMIN)) {
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
        }else{
            navigationView.getMenu().getItem(5).setVisible(false);
        }
        requestPermission();
    }

    private void checkToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                refreshedToken = instanceIdResult.getToken();
                PersistanceUtil.setUserID(refreshedToken);

                storeRegIdInPref(refreshedToken);

                Config.setPrefToken(context, refreshedToken);

                Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
                registrationComplete.putExtra("token", refreshedToken);
                LocalBroadcastManager.getInstance(context).sendBroadcast(registrationComplete);
                STRTOKEN = Config.getPrefToken(context);
                if (!STRTOKEN.equals("0")) {
                    if (Controller.getTokenstatus() == null) {
                        pushToken(Config.getPrefToken(context));
                        Log.d("TOKEN", Config.getPrefToken(context));
                    } else if (Controller.getTokenstatus().equals(Controller.SENT)) {
                        Log.d("TOKEN", Config.getPrefToken(context));
                    }

                }
            }
        });
    }
    private void pushToken(String token) {
        Log.d("TOKEN", "check");
        spotsDialog.show();
        Call<String> call = ApiUtil.getServiceClass().updatetoken(token, Controller.getUID(), new Controller(HomeActivity.this).getphno());
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


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    private void openDialog() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setContentImageDrawable(R.drawable.congratulation_icon);
        builder.setTextGravity(Gravity.CENTER);
        builder.setTitle("Quiz Results published !");
        builder.setMessage("Click view to see your Quiz Results...");
        builder.addButton("View", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Quiz_Control.addseenquiz(Quiz_Control.LATER);
                new Move_Show(HomeActivity.this, Quiz_Events.class);
            }
        });

        builder.addButton("Later", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void verifyaccount() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setIcon(R.drawable.newlogo);
        builder.setCornerRadius(20);
        builder.setTitle("Welcome Back , Student Bazaar");
        builder.addButton("LOGIN", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new Move_Show(HomeActivity.this, Login_Page.class);
            }
        });
        builder.addButton("NOT NOW", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Controller.adduservierify(Controller.USERVERIFY);
            }
        });
        builder.show();
    }

    private void displayaccountstatus(String name) {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setIcon(R.drawable.newlogo);
        builder.setCornerRadius(20);
        builder.setTitle("Welcome Back , " + name);
        builder.addButton("LOGIN", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new Move_Show(HomeActivity.this, Login_Page.class);
            }
        });
        builder.addButton("NOT NOW", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Controller.adduservierify(Controller.USERVERIFY);
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setIcon(R.drawable.newlogo);
        builder.setCornerRadius(20);
        builder.setTitle("Hey , There !");
        builder.setMessage("Are You sure, want to close this app ?");
        builder.addButton("EXIT", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent myService = new Intent(HomeActivity.this, BroadcastService.class);
                stopService(myService);
                finishAffinity();
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent myService = new Intent(HomeActivity.this, BroadcastService.class);
        stopService(myService);
    }
}
