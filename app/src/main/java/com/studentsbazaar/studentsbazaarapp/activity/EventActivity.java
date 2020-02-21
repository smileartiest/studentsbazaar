package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
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
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        progressDialog = new SpotsDialog(this);
        layout = (LinearLayout) findViewById(R.id.empty2);
        new Controller(this);
        viewPager2 = findViewById(R.id.viewPager2);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        onBackPressed();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(EventActivity.this);
        if (Controller.getprefer().equals(Controller.VISITOR)) {
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);

        } else if (Controller.getprefer().equals(Controller.REG)) {
            navigationView.getMenu().getItem(2).setVisible(false);

        } else if (Controller.getprefer().equals(Controller.INFOZUB) || Controller.getprefer().equals(Controller.MEMEACCEPT)) {
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
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
                loadData();
                break;

            case R.id.nav_add_event:

                if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.ADMIN)) {
                    new Move_Show(EventActivity.this, AddEvent2.class);
                } else {
                    addEvent();
                }

                break;

            case R.id.nav_pending:
                new Move_Show(EventActivity.this, Pending_Events.class);
                break;

            case R.id.nav_edit:
                new Move_Show(EventActivity.this, Edit_Events.class);
                break;
            case R.id.nav_contact:
                new Move_Show(EventActivity.this, ContactActivity.class);
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
        if (Controller.getprefer().equals(Controller.VISITOR)) {

            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setTitle("Hey there ! Need to Register!");
            builder.setMessage("Kindly fill your details to continue adding event.");
            builder.addButton("OKAY", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new Move_Show(EventActivity.this, SignUp.class);

                }
            });

            builder.addButton("NO", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
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
    }
}
