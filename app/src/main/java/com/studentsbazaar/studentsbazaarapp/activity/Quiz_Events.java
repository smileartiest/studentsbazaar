package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.studentsbazaar.studentsbazaarapp.AlarmHelper;
import com.studentsbazaar.studentsbazaarapp.NotificationPublisher;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.Quiz_Adapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.controller.Quiz_Control;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quiz_Events extends AppCompatActivity {

    RecyclerView Quiz_view;
    SpotsDialog progressDialog;
    Button submit;
    SharedPreferences sharedPreferences;
    List<Quiz_Details> drawerResponseList = null;
    Quiz_Adapter quiz_adapter;
    LinearLayout layout;
    private Toolbar toolbar;
    private AlarmHelper alarmHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__events);
        Quiz_view = (RecyclerView) findViewById(R.id.quiz_view);
        submit = (Button) findViewById(R.id.submit_quiz);
        Quiz_view.setHasFixedSize(true);
        new Quiz_Control(Quiz_Events.this);
        alarmHelper = new AlarmHelper(this);
        layout = (LinearLayout) findViewById(R.id.empty4);
        sharedPreferences = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setnotification();
        // displaystatus();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("QUIZ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


        }   // use a linear layout manager
        Quiz_view.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new SpotsDialog(this);
        loadData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizresults", String.valueOf(ApiUtil.QUIZ_RESULT));
                updateresults();
                addresults(Controller.getUID(),Quiz_Control.getCorrectans());

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void loadData() {
        Log.d("RESPONSE1", Controller.getUID());
        progressDialog.show();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getQuizQuestions(Controller.getUID());
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.d("RESPONSE2",response.body().toString());

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    drawerResponseList = response.body().getQuiz_details();

                    progressDialog.dismiss();
                    ApiUtil.TOTAL_QUIZ = drawerResponseList.size();
                    if (drawerResponseList.size() == 0) {
                        layout.setVisibility(View.VISIBLE);
                        Quiz_view.setVisibility(View.INVISIBLE);
                        submit.setVisibility(View.INVISIBLE);
                    }
                    else{
                        layout.setVisibility(View.INVISIBLE);
                        Quiz_view.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.VISIBLE);
                        quiz_adapter = new Quiz_Adapter(Quiz_Events.this, drawerResponseList);
                        Quiz_view.setAdapter(quiz_adapter);
                    }

                }
                // mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                //showErrorMessage();
                Log.d("RESPONSE3", "err" + t.getMessage());
            }
        });
    }

    void addresults(String uid, String results) {
        progressDialog.show();
        Call<String> call = ApiUtil.getServiceClass().addresultstoprofile(ApiUtil.ADD_QUIZ_RESULTS + "?uid=" + uid + "&result=" + results);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                if (response.body().equals("1")) {
                    displaystatus();
                   // getAlertwindow("Thanks\nyou will get the result by 6 PM,stay tuned... ");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    void getAlertwindow(String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Quiz_Events.this);
        builder.setTitle(message);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sharedPreferences = getSharedPreferences("QUIZ_STATUS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("QUIZ", "attend").apply();
                setnotification();
                Intent intent = new Intent(Quiz_Events.this, HomeActivity.class);
                startActivity(intent);
                finish();


            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    void setnotification() {
        Calendar calendar = Calendar.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    16, 20, 0);
        } else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    16, 20, 0);
        }


        setAlarm(calendar.getTimeInMillis());
    }

    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, NotificationPublisher.class);
        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Remainder Set", Toast.LENGTH_SHORT).show();
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

    void displaystatus() {
        Dialog d = new Dialog(Quiz_Events.this);
        d.requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        d.setCancelable(false);
        d.setContentView(R.layout.quiz_results_design);
        d.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        TextView totalmark = (TextView) d.findViewById(R.id.uitvresults);
        TextView totalcount = (TextView) d.findViewById(R.id.uitvcountresult);
        TextView correctans = (TextView) d.findViewById(R.id.uitvcorrectans);
        TextView worngans = (TextView) d.findViewById(R.id.uitvwrongans);
        ImageView closebtn = (ImageView) d.findViewById(R.id.uiivclosebtn);
        totalmark.setText(Quiz_Control.getCorrectans());
        totalcount.setText(totalcount.getText().toString().replace("10", Quiz_Control.getCorrectans()));
        correctans.setText(" "+Quiz_Control.getCorrectans()+" Correct");
        worngans.setText(" "+Quiz_Control.getwrongans()+" Wrong");
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }
    private void updateresults(){
        Quiz_Control.addTotalPoint(String.valueOf(ApiUtil.TOTAL_QUIZ));
        Quiz_Control.addCorrectans(String.valueOf(ApiUtil.QUIZ_RESULT));
        Quiz_Control.addworngans(String.valueOf(ApiUtil.TOTAL_QUIZ-ApiUtil.QUIZ_RESULT));
    }
}
