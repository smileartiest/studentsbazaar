package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentsbazaar.studentsbazaarapp.notification.AlarmHelper;
import com.studentsbazaar.studentsbazaarapp.notification.NotificationPublisher;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.Quiz_Result_Adapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.controller.Quiz_Control;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quiz_Events extends AppCompatActivity {

    List<Quiz_Details> drawerResponseList = null;
    private Toolbar toolbar;
    private AlarmHelper alarmHelper;
    int CURRENT_TIME;
    int START_TIME = 9;
    int LOCAL_TIME = 19;
    int LIMIT_TIME = 23;
    ConstraintLayout adminpage;
    ScrollView userpage;
    Quiz_Result_Adapter quiz_result_adapter;
    RecyclerView qsntlist;
    String uidata;
    ImageView quizqstn,indication_icon;
    RadioGroup option_type;
    RadioButton a, b, c, d;
    String today_ans;
    EditText fill_blank_ans;
    TextView qssts, completebtn, views, title, headtitle;
    FloatingActionButton add_qstn;
    Calendar calander;
    SpotsDialog spotsDialog;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_events);

        init();

        spotsDialog.show();

        headtitle.setText("Question No . " + new Quiz_Control(Quiz_Events.this).getqid());
        qsntlist = findViewById(R.id.quiz_quiz_list);

        qsntlist.setHasFixedSize(true);
        qsntlist.setLayoutManager(new LinearLayoutManager(this));

        option_type.setVisibility(View.GONE);
        fill_blank_ans.setVisibility(View.GONE);

        calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        String time = simpleDateFormat.format(calander.getTime());
        Log.d("Time", time);
        CURRENT_TIME = Integer.valueOf(time);

        if (Controller.getprefer().equals(Controller.ADMIN) || Controller.getprefer().equals(Controller.INFOZUB)) {
            loadFulldetails();
        } else if (START_TIME <= CURRENT_TIME && LOCAL_TIME > CURRENT_TIME) {
            userpage.setVisibility(View.VISIBLE);
            adminpage.setVisibility(View.GONE);
            loadData();
        } else if (LOCAL_TIME <= CURRENT_TIME && CURRENT_TIME <= LIMIT_TIME) {
            userpage.setVisibility(View.VISIBLE);
            adminpage.setVisibility(View.GONE);
            completebtn.setVisibility(View.GONE);
            option_type.setVisibility(View.GONE);
            fill_blank_ans.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            qssts.setText("Today's quiz timing over. Will catch you tomorrow with new question.");
            loadaresult();
        } else {
            spotsDialog.dismiss();
            completebtn.setVisibility(View.GONE);
            views.setVisibility(View.GONE);
            userpage.setVisibility(View.GONE);
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_Events.this);
            builder.setCornerRadius(20);
            builder.setContentImageDrawable(R.drawable.timing_closed_icon);
            builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
            builder.setTextGravity(Gravity.CENTER);
            builder.setTitle("Hai , " + Controller.getusername() +" . Quiz Timing from 9AM to 7PM . So wait for question. Thanks for your interest !");
            builder.addButton("Ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.SRC_ATOP);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        completebtn.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(completebtn.getText().toString().equals("Tab to get Answer")){
                    todayansdialog();
                }else{
                    if (today_ans != null && today_ans.length() != 0) {
                        completedialog();
                    } else {
                        errordialog();
                    }
                }
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today_ans = "A";
                ApiUtil.QUIZ_ATTENT = 1;
                new Quiz_Control(Quiz_Events.this).addtodayans("A");
                completebtn.setVisibility(View.VISIBLE);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today_ans = "B";
                ApiUtil.QUIZ_ATTENT = 1;
                new Quiz_Control(Quiz_Events.this).addtodayans("B");
                completebtn.setVisibility(View.VISIBLE);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today_ans = "C";
                ApiUtil.QUIZ_ATTENT = 1;
                new Quiz_Control(Quiz_Events.this).addtodayans("C");
                completebtn.setVisibility(View.VISIBLE);
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today_ans = "D";
                ApiUtil.QUIZ_ATTENT = 1;
                new Quiz_Control(Quiz_Events.this).addtodayans("D");
                completebtn.setVisibility(View.VISIBLE);
            }
        });

        fill_blank_ans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                today_ans = s.toString();
                new Quiz_Control(Quiz_Events.this).addtodayans(today_ans);
                Move_Show.showToast("Your Answer : " + today_ans);
                ApiUtil.QUIZ_ATTENT = 1;
                completebtn.setVisibility(View.VISIBLE);
            }
        });
        add_qstn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(Quiz_Events.this, Update_Quiz_Questions.class);
            }
        });
    }

    void errordialog() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_Events.this);
        builder.setCornerRadius(20);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setContentImageDrawable(R.drawable.sad_emoji_icon);
        builder.setTextGravity(Gravity.CENTER);
        builder.setTitle("Please check your answer first. ! ");
        builder.addButton("Ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    void completedialog() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_Events.this);
        builder.setCornerRadius(20);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setContentImageDrawable(R.drawable.happy_emoji_icon);
        builder.setTextGravity(Gravity.CENTER);
        builder.setTitle("Please confirm your answer !"+"\n"+"Your answer is "+today_ans +"\n . Proceed yes to continue.");
        builder.addButton("Continue", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                spotsDialog.show();
                if (ApiUtil.QUIZ_ATTENT == 0) {
                    spotsDialog.dismiss();
                    Move_Show.showToast("Please answer the question");
                } else {
                    Log.d("Quizresults", "" + ApiUtil.QUIZ_ATTENT);
                    if (Quiz_Control.getanswer().equals(Quiz_Control.getcans())) {
                        addresults(Controller.getUID(), "1", new Quiz_Control(Quiz_Events.this).getqid(),new Quiz_Control(Quiz_Events.this).getQuizquestion() ,new Quiz_Control(Quiz_Events.this).getAnspic() , new Quiz_Control(Quiz_Events.this).getcans() ,today_ans);
                    } else {
                        addresults(Controller.getUID(), "0", new Quiz_Control(Quiz_Events.this).getqid(),new Quiz_Control(Quiz_Events.this).getQuizquestion() ,new Quiz_Control(Quiz_Events.this).getAnspic() , new Quiz_Control(Quiz_Events.this).getcans() ,today_ans);
                    }
                }
            }
        });
        builder.addButton("Not now", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                today_ans = "";
            }
        });
        builder.show();
    }

    void loadData() {
        uidata = Controller.getUID();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getQuizQuestions(uidata);
        call.enqueue(new Callback<DownloadResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.d("RESPONSE2", response.body().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    drawerResponseList = response.body().getQuiz_details();
                    if (drawerResponseList.size() == 0) {
                        spotsDialog.dismiss();
                    } else if (drawerResponseList.get(0).getMsg().equals("1")) {
                        Log.d("RESPONSE2", drawerResponseList.get(0).getMsg());
                        spotsDialog.dismiss();
                        option_type.setVisibility(View.GONE);
                        fill_blank_ans.setVisibility(View.GONE);
                        completebtn.setVisibility(View.GONE);
                        title.setText("Wait for your Result !");
                        views.setText("Total viewers ."+new Quiz_Control(Quiz_Events.this).getviews());
                        Glide.with(getApplicationContext()).load(drawerResponseList.get(0).getQuiz_ques()).into(quizqstn);
                        qssts.setText("You have already Submitted your answer as : "+  drawerResponseList.get(0).getSubmit_Answer()+
                                "\nYou will get your result by 7 PM today.\n" +
                                "Stay tuned.");
                        indication_icon.setImageResource(R.drawable.complete_green_round_icon);
                        spotsDialog.dismiss();
                    } else if(drawerResponseList.get(0).getMsg().equals("2")){
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_Events.this);
                        builder.setCornerRadius(20);
                        builder.setCancelable(true);
                        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                        builder.setContentImageDrawable(R.drawable.timing_closed_icon);
                        builder.setTextGravity(Gravity.CENTER);
                        builder.setTitle("Hai , " + Controller.getusername()+"\n"+"Today no question posted.");
                        builder.addButton("Ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        builder.onDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        builder.show();
                    } else if(drawerResponseList.get(0).getMsg().equals("0")) {
                        Quiz_Control.addquizquestion(drawerResponseList.get(0).getQuiz_ques());
                        Quiz_Control.addcrctans(drawerResponseList.get(0).getCrct_Ans());
                        Quiz_Control.addviewrs(String.valueOf(drawerResponseList.get(0).getViewers()));
                        views.setText("Total viewers ."+drawerResponseList.get(0).getViewers());
                        new Quiz_Control(Quiz_Events.this).addresult(drawerResponseList.get(0).getCrct_Ans(), drawerResponseList.get(0).getQuiz_Id(), drawerResponseList.get(0).getQuiz_ques(), drawerResponseList.get(0).getQuiz_Ans());
                        if (drawerResponseList.get(0).getQuiz_Type().equals("0")) {
                            Glide.with(getApplicationContext()).load(new Quiz_Control(Quiz_Events.this).getQpic()).into(quizqstn);
                            views.setVisibility(View.VISIBLE);
                            option_type.setVisibility(View.VISIBLE);
                            fill_blank_ans.setVisibility(View.GONE);
                            title.setText("Please Choose Your Answer");
                            qssts.setText("Today's quiz timing will end by 7:00 PM.");
                        } else {
                            Glide.with(getApplicationContext()).load(new Quiz_Control(Quiz_Events.this).getQpic()).into(quizqstn);
                            views.setVisibility(View.VISIBLE);
                            option_type.setVisibility(View.GONE);
                            fill_blank_ans.setVisibility(View.VISIBLE);
                            title.setText("Please Enter Your Answer");
                            qssts.setText("Today's quiz timing will end by 7:00 PM.");
                        }
                        spotsDialog.dismiss();
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

    void todaynotattenquizdialog() {
        completebtn.setVisibility(View.GONE);
        views.setVisibility(View.GONE);
        userpage.setVisibility(View.GONE);
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_Events.this);
        builder.setCornerRadius(20);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setContentImageDrawable(R.drawable.timing_closed_icon);
        builder.setTextGravity(Gravity.CENTER);
        builder.setTitle("Hai , " + Controller.getusername()+"\n"+"Quiz Timing from 9AM to 7PM. Thanks for your interest !");
        builder.addButton("Ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    void todayansdialog() {
        Dialog d1 = new Dialog(Quiz_Events.this);
        d1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        d1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d1.setCancelable(false);
        d1.setContentView(R.layout.quiz_results_design);
        d1.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        TextView totalmark = d1.findViewById(R.id.uitvresults);
        ImageView anspic = d1.findViewById(R.id.uiianspic);
        ImageView closebtn = d1.findViewById(R.id.uiivclosebtn);
        CardView resultshare = (CardView) d1.findViewById(R.id.cardView9);

        if (new Quiz_Control(Quiz_Events.this).getscore().equals("0")) {
            totalmark.setText("Wrong Answer");
            totalmark.setTextColor(getResources().getColor(R.color.red));
        } else {
            totalmark.setText("Correct Answer");
            totalmark.setTextColor(getResources().getColor(R.color.green));
        }
        Quiz_Control.addseenquiz(Quiz_Control.SEEN);
        Glide.with(Quiz_Events.this).load(new Quiz_Control(Quiz_Events.this).getanspic()).into(anspic);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d1.dismiss();
            }
        });
        resultshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Monitor(Quiz_Events.this).shareImage(Quiz_Control.getAnspic());
            }
        });
        d1.show();
    }

    void loadFulldetails() {
        userpage.setVisibility(View.GONE);
        adminpage.setVisibility(View.VISIBLE);
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getQuizQuestions("0");
        call.enqueue(new Callback<DownloadResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.d("RESPONSE2", response.body().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    drawerResponseList = response.body().getQuiz_details();
                    spotsDialog.dismiss();
                    if (drawerResponseList.size() == 0) {
                        spotsDialog.dismiss();
                    } else {
                        qsntlist.setVisibility(View.VISIBLE);
                        Quiz_Control.addquizquestion(drawerResponseList.get(0).getQuiz_ques());
                        Quiz_Control.addcrctans(drawerResponseList.get(0).getCrct_Ans());
                        quiz_result_adapter = new Quiz_Result_Adapter(Quiz_Events.this, drawerResponseList);
                        qsntlist.setAdapter(quiz_result_adapter);
                        spotsDialog.dismiss();
                    }
                }else{
                    spotsDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                Log.d("RESPONSE3", "err" + t.getMessage());
                spotsDialog.dismiss();
            }
        });
    }

    void loadaresult(){
        uidata = Controller.getUID();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getQuizQuestions(uidata);
        call.enqueue(new Callback<DownloadResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.d("RESPONSE2", response.body().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    drawerResponseList = response.body().getQuiz_details();
                    if (drawerResponseList.size() == 0) {
                        spotsDialog.dismiss();
                    } else if (drawerResponseList.get(0).getMsg().equals("1")) {
                        Log.d("RESPONSE2", drawerResponseList.get(0).getMsg());
                        spotsDialog.dismiss();
                        option_type.setVisibility(View.GONE);
                        fill_blank_ans.setVisibility(View.GONE);
                        title.setText("Wait for your Result !");
                        Glide.with(getApplicationContext()).load(Quiz_Control.getQpic()).into(quizqstn);
                        qssts.setText("Today's quiz timing over. Will catch you tomorrow with new question.");
                        spotsDialog.dismiss();
                        views.setText("Total viewers ."+new Quiz_Control(Quiz_Events.this).getviews());
                        new Quiz_Control(Quiz_Events.this).addqsResult(drawerResponseList.get(0).getQuiz_Ans() , String.valueOf(drawerResponseList.get(0).getScore()));
                        completebtn.setText("Tab to get Answer");
                        completebtn.setVisibility(View.VISIBLE);
                    } else if(drawerResponseList.get(0).getMsg().equals("2")){
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_Events.this);
                        builder.setCornerRadius(20);
                        builder.setCancelable(true);
                        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                        builder.setContentImageDrawable(R.drawable.timing_closed_icon);
                        builder.setTextGravity(Gravity.CENTER);
                        builder.setTitle("Hai , " + Controller.getusername()+"\n"+"Today no question posted.");
                        builder.addButton("Ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        builder.onDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        builder.show();
                    } else if(drawerResponseList.get(0).getMsg().equals("0")) {
                        todaynotattenquizdialog();
                        spotsDialog.dismiss();
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

    void addresults(String uid, String results, String qid , String qpic , String anspic , String cans , String sans) {
        Call<String> call = ApiUtil.getServiceClass().addresultstoprofile(ApiUtil.ADD_QUIZ_RESULTS + "?uid=" + uid + "&qid=" + qid + "&result=" + results+"&qpic="+qpic +"&apic="+anspic +"&cans="+cans +"&sans="+sans);
        Log.d("status", "upload");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("status", "uploaded");
                if (response.isSuccessful()) {
                    spotsDialog.dismiss();
                    if (response.body().equals("1")) {
                        Calendar c1 = Calendar.getInstance();
                        Quiz_Control.adddate(c1.get(Calendar.YEAR) + "/" + (c1.get(Calendar.MONTH) + 1) + "/" + c1.get(Calendar.DATE));
                        conformationdialog("Congratulations ! \nYour answer has been updated successfully. Thanks for your participation.");
                    } else if (response.body().equals("2")) {
                        spotsDialog.dismiss();
                        Log.d("Quiz Update Error ", response.body());
                        conformationdialog("Oops ! Update error");
                    } else {
                        spotsDialog.dismiss();
                        Log.d("Quiz Update Error ", response.body());
                        conformationdialog("Oops ! Insert error");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                spotsDialog.dismiss();Log.d("errorlog", t.getMessage());
            }
        });
    }

    void conformationdialog(String title) {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_Events.this);
        builder.setCornerRadius(20);
        builder.setContentImageDrawable(R.drawable.congratulation_icon);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTextGravity(Gravity.CENTER);
        builder.setTitle(title);
        builder.addButton("Done", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
                new Move_Show(Quiz_Events.this, HomeActivity.class);
                //setnotification();
            }
        });
        builder.show();
    }

    private void setnotification() {
        Calendar calendar = Calendar.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    16, 53, 0);
        } else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    16, 53, 0);
        }
        setAlarm(calendar.getTimeInMillis());
    }

    private void setAlarm(long timeInMillis) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, NotificationPublisher.class);
        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Remainder Set", Toast.LENGTH_SHORT).show();

    }

    void init(){
        alarmHelper = new AlarmHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spotsDialog = new SpotsDialog(this);

        userpage = findViewById(R.id.quiz_user_page_card);
        adminpage = findViewById(R.id.quiz_admin_page);
        quizqstn = findViewById(R.id.f_quiz_pic);
        option_type = findViewById(R.id.f_quiz_type1);
        fill_blank_ans = findViewById(R.id.f_quiz_type2_ans);
        qssts = findViewById(R.id.f_quiz_sts);
        title = findViewById(R.id.f_quiz_title);
        headtitle = findViewById(R.id.quiz_id_title);
        completebtn = findViewById(R.id.f_quiz_completebtn);
        add_qstn = findViewById(R.id.quiz_add_qstn);
        views = findViewById(R.id.f_quiz_views);
        indication_icon = findViewById(R.id.f_quiz_indication_icon);
        a = findViewById(R.id.type1_a);
        b = findViewById(R.id.type1_b);
        c = findViewById(R.id.type1_c);
        d = findViewById(R.id.type1_d);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        new Move_Show(Quiz_Events.this, HomeActivity.class);
    }
}
