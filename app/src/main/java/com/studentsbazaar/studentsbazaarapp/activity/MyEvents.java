package com.studentsbazaar.studentsbazaarapp.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.MyEventAdapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class MyEvents extends AppCompatActivity {

    RecyclerView my_list;
    SwipeRefreshLayout my_swipe_refresh;
    TextView subtitle;
    Toolbar my_toolbar;
    SpotsDialog progressDialog;
    List<Project_details> drawerResponseList = null;
    MyEventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_events);

        initialise();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        my_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        my_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressDialog.show();
                loadData();
            }
        });

    }

    void loadData(){
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_USER_EVENTS+"?uid="+ Controller.getUID());
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, retrofit2.Response<DownloadResponse> response) {
                Log.d("RESPONSE1", response.message().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    drawerResponseList = response.body().getProject_details();
                    Log.d("RESPONSE2", drawerResponseList.toString());
                    progressDialog.dismiss();
                    my_swipe_refresh.setRefreshing(false);
                    adapter = new MyEventAdapter(MyEvents.this , drawerResponseList);
                    my_list.setAdapter(adapter);
                    if (drawerResponseList.size() == 0) {
                        my_list.setVisibility(View.INVISIBLE);
                    } else {
                        subtitle.setText("Total search . "+drawerResponseList.size());
                        my_list.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                //showErrorMessage();
                Log.d("RESPONSE3", "err" + t.getMessage());
            }
        });
    }

    //initialise components
    private void initialise() {
        my_toolbar = findViewById(R.id.myevent_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        my_toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.SRC_ATOP);

        progressDialog = new SpotsDialog(MyEvents.this);
        progressDialog.show();
        subtitle = findViewById(R.id.myevent_subtitle);
        my_swipe_refresh = findViewById(R.id.myevent_refresh);
        my_list  = findViewById(R.id.myevent_list);
        my_list.setHasFixedSize(true);
        my_list.setLayoutManager(new LinearLayoutManager(MyEvents.this));

    }
}