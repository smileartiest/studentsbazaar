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
import com.studentsbazaar.studentsbazaarapp.adapter.Memes_Adapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Memes_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class Pending_Post extends AppCompatActivity {
    List<Memes_Details> memes_details = null;
    SpotsDialog spotsDialog;
    RecyclerView memeview;
    Memes_Adapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_pending);
        swipeRefreshLayout=findViewById(R.id.swipeToRefreshpost);
        layout=findViewById(R.id.empty7);
        memeview=findViewById(R.id.postview);
        spotsDialog=new SpotsDialog(this);
        new Controller(this);
        Controller.addupdateview(Controller.INITIAL);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpost);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        loadData();
        memeview.setHasFixedSize(true);
        memeview.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void loadData() {
        spotsDialog.show();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_PENDING_MEMES);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, retrofit2.Response<DownloadResponse> response) {

                Log.d("RESPONSE1", response.message().toString());

                if (response.isSuccessful()) {
                    swipeRefreshLayout.setRefreshing(false);

                    assert response.body() != null;
                    memes_details = response.body().getMemes_details();

                    Log.d("RESPONSE2", memes_details.toString());
                    spotsDialog.dismiss();
                    if (memes_details.size() == 0) {
                        layout.setVisibility(View.VISIBLE);
                        memeview.setVisibility(View.INVISIBLE);
                    } else {
                        layout.setVisibility(View.INVISIBLE);
                        memeview.setVisibility(View.VISIBLE);
                        mAdapter = new Memes_Adapter(Pending_Post.this, memes_details);
                        memeview.setAdapter(mAdapter);
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

}
