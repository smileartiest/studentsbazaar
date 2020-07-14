package com.studentsbazaar.studentsbazaarapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.studentsbazaar.studentsbazaarapp.adapter.Quiz_History_Adapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.TimeDate;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_History;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quiz_history_page extends AppCompatActivity {

    List<Quiz_History> download_response = null;
    ConstraintLayout name_card , no_data , score_card;
    TextView total_score , correct_ans , wrong_ans;
    RecyclerView history_list;
    Quiz_History_Adapter quiz_history_adapter;
    int START_TIME = 9;
    int LOCAL_TIME = 19;
    int CURRENT_TIME;
    Calendar calander;

    Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_history);

        my_toolbar = findViewById(R.id.quiz_hs_menu);
        name_card = findViewById(R.id.quiz_hs_name_card);
        no_data = findViewById(R.id.quiz_hs_nodata);
        history_list = findViewById(R.id.quiz_hs_list);
        score_card = findViewById(R.id.quiz_hs_score_card);
        total_score = findViewById(R.id.quiz_hs_score_t_qstn);
        correct_ans = findViewById(R.id.quiz_hs_score_c_ans);
        wrong_ans = findViewById(R.id.quiz_hs_score_w_ans);

        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        no_data.setVisibility(View.GONE);

        history_list.setHasFixedSize(true);
        history_list.setLayoutManager(new LinearLayoutManager(Quiz_history_page.this));

        my_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Call<DownloadResponse> call = ApiUtil.getServiceClass().getquizresult(Controller.getUID());
        call.enqueue(new Callback<DownloadResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.d("Result" , response.body().toString());
                if(response.isSuccessful()){
                    assert response.body() != null;
                    download_response = response.body().getQuiz_History();
                    Log.d("size " , String.valueOf(download_response.get(0).getUser_ID()));
                    if(download_response.size()==0){
                        name_card.setVisibility(View.GONE);
                        history_list.setVisibility(View.GONE);
                        score_card.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                    }else if(response.body().equals("0") ){
                        name_card.setVisibility(View.GONE);
                        history_list.setVisibility(View.GONE);
                        score_card.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                    }else{
                        calander = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
                        String time = simpleDateFormat.format(calander.getTime());
                        Log.d("Time", time);
                        CURRENT_TIME = Integer.valueOf(time);
                        Log.d("Today Date" , new TimeDate().getDateYMD());
                        total_score.setText("Total Completed Question's . " +download_response.size());
                        int temp = 0;
                        int today = 0;
                        for(int i = 0 ; i< download_response.size() ; i++){
                            if(String.valueOf(download_response.get(i).getScore()).equals("1")){
                                if(new TimeDate().getDateYMD().equals(download_response.get(i).getCreate_Date())){
                                    if (START_TIME <= CURRENT_TIME && LOCAL_TIME > CURRENT_TIME){
                                        today = today+1;
                                    }else{
                                        today = 0;
                                        temp = temp + 1;
                                    }
                                }else {
                                    temp = temp + 1;
                                    Log.d("Matched", temp + " / " + download_response.get(i).getScore());
                                }
                            }else{
                                if(new TimeDate().getDateYMD().equals(download_response.get(i).getCreate_Date())){
                                    if (START_TIME <= CURRENT_TIME && LOCAL_TIME > CURRENT_TIME){
                                        today = today+1;
                                    }else{
                                        today = 0;
                                        temp = temp + 1;
                                    }
                                }else {
                                    temp = temp + 0;
                                    Log.d("Matched", temp + " / " + download_response.get(i).getScore());
                                }
                            }
                        }
                        int wrongans = download_response.size() - temp - today;
                        correct_ans.setText(String.valueOf(temp));
                        wrong_ans.setText(String.valueOf(wrongans));

                        no_data.setVisibility(View.GONE);
                        name_card.setVisibility(View.VISIBLE);
                        history_list.setVisibility(View.VISIBLE);
                        score_card.setVisibility(View.VISIBLE);
                        quiz_history_adapter = new Quiz_History_Adapter(Quiz_history_page.this , download_response);
                        history_list.setAdapter(quiz_history_adapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                Log.d("Error " , t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}