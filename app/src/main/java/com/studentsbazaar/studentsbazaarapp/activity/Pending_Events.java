package com.studentsbazaar.studentsbazaarapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.PendingEventsAdapter;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class Pending_Events extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView pendingeventsrecycler;
    SpotsDialog progressDialog;
    List<Project_details> drawerResponseList = null;
    PendingEventsAdapter mAdapter;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending__events);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipepending);
        pendingeventsrecycler = (RecyclerView) findViewById(R.id.pending_events_recycler);
        pendingeventsrecycler.setHasFixedSize(true);
        pendingeventsrecycler.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new SpotsDialog(this, R.style.Custom);
        layout = (LinearLayout) findViewById(R.id.empty1);
        progressDialog.show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpending);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Pending Events");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

    }

    private void loadData() {

        progressDialog.show();

        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_PENDING_EVENTS);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, retrofit2.Response<DownloadResponse> response) {

                Log.d("RESPONSE1", response.message().toString());

                if (response.isSuccessful()) {


                    assert response.body() != null;
                    drawerResponseList = response.body().getProject_details();

                    Log.d("RESPONSE2", drawerResponseList.toString());
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    mAdapter = new PendingEventsAdapter(Pending_Events.this, drawerResponseList);
                    pendingeventsrecycler.setAdapter(mAdapter);
                    if (drawerResponseList.size() == 0) {
                        layout.setVisibility(View.VISIBLE);
                        pendingeventsrecycler.setVisibility(View.INVISIBLE);
                    } else {

                        layout.setVisibility(View.INVISIBLE);
                        pendingeventsrecycler.setVisibility(View.VISIBLE);
                    }

                    // mAdapter.notifyDataSetChanged();

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
    public void onBackPressed() {
        super.onBackPressed();
        new Move_Show(Pending_Events.this,EventActivity.class);
    }
}
