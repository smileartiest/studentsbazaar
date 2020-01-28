package com.studentsbazaar.studentsbazaarapp.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.Quiz_Adapter;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class Quiz_Events extends AppCompatActivity {
    RecyclerView Quiz_view;
    SpotsDialog progressDialog;


    List<Quiz_Details> drawerResponseList = null;
    Quiz_Adapter quiz_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__events);
        Quiz_view = (RecyclerView) findViewById(R.id.quiz_view);
        Quiz_view.setHasFixedSize(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("QUIZ");

        }   // use a linear layout manager
        Quiz_view.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new SpotsDialog(this);
        loadData();
    }

    void loadData() {

        progressDialog.show();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getQuizQuestions(ApiUtil.GET_QUIZ_QUESTIONS);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, retrofit2.Response<DownloadResponse> response) {

                Log.d("RESPONSE1", response.raw().toString());

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    drawerResponseList = response.body().getQuiz_details();
                    progressDialog.dismiss();
                     quiz_adapter = new Quiz_Adapter(Quiz_Events.this, drawerResponseList);
                    Quiz_view.setAdapter(quiz_adapter);


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
}
