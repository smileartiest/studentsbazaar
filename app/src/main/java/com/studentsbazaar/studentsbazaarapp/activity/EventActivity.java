package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.navigation.NavigationView;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.ViewPagerAdapter;
import com.studentsbazaar.studentsbazaarapp.helper.DepthPageTransformer;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class EventActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager2 viewPager2;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    NavigationView navigationView;
    ViewPagerAdapter mAdapter;

    SpotsDialog progressDialog;
    List<Project_details> drawerResponseList = null;
    SharedPreferences spUserDetails;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        sharedPreferences = getSharedPreferences("DEV_ID", MODE_PRIVATE);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        layout = (LinearLayout) findViewById(R.id.empty2);
        viewPager2 = findViewById(R.id.viewPager2);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");

        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        spUserDetails = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        editor = spUserDetails.edit();
        navigationView.setNavigationItemSelectedListener(EventActivity.this);
        if (spUserDetails.getString("log", "").equals("visitor")) {
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);

        } else if (spUserDetails.getString("log", "").equals("reg")) {
            navigationView.getMenu().getItem(2).setVisible(false);

        }

        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        loadData();
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
                        // mAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                loadData();
             /*   starlotActivity(new Intent(JobList.this, JobList.class));
                finish();*/
                break;

            case R.id.nav_add_event:

                if (spUserDetails.getString("log", null).equals("reg") || spUserDetails.getString("log", null).equals("admin") ) {
                    Intent intent = new Intent(this, AddEvent2.class);
                    startActivity(intent);
                } else {
                    addEvent();
                }


                break;

            case R.id.nav_pending:

                editor.putString("PREFER", "PREF").apply();
                Intent intent1 = new Intent(this, Pending_Events.class);
                startActivity(intent1);
                break;

            case R.id.nav_edit:

                Intent intent2 = new Intent(this, Edit_Events.class);
                startActivity(intent2);

                break;
            case R.id.nav_contact:
                Intent intent4 = new Intent(this, ContactActivity.class);
                startActivity(intent4);

                break;


            case R.id.nav_aboutus:

                Bundle b = new Bundle();
                b.putString("url", "https://www.studentsbazaar.in/about-us/");
                b.putString("title", "ABOUT US");
                Intent intEvent = new Intent(EventActivity.this, WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);
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

    private void addEvent() {
        if (spUserDetails.getString("log", "").equals("visitor")) {

            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setTitle("Hey there ! Need to Register!");
            builder.setMessage("Kindly fill your details to continue adding event.");
            builder.addButton("OKAY", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent i = new Intent(EventActivity.this, SignUp.class);
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
        } else {
            startActivity(new Intent(getApplicationContext(), AddEvent2.class));
        }
    }
}
