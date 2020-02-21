package com.studentsbazaar.studentsbazaarapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_Quiz_Questions extends AppCompatActivity {
    EditText quesion, optionA, optionB, optionC, optionD, crctans;
    Button postquiz;
    SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__quiz__questions);

       Toolbar toolbar = (Toolbar) findViewById(R.id.uitbupdatequiz);

        // setnotification();
        // displaystatus();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Post Quiz");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


        }   // use a l
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        quesion = (EditText) findViewById(R.id.uiedaddquizqes);
        optionA = (EditText) findViewById(R.id.uiedaddoptionA);
        optionB = (EditText) findViewById(R.id.uiedaddoptionB);
        optionC = (EditText) findViewById(R.id.uiedaddoptionC);
        optionD = (EditText) findViewById(R.id.uiedaddoptionD);
        crctans = (EditText) findViewById(R.id.uiedaddcrctans);
        postquiz = (Button) findViewById(R.id.uibrnaddquiz);
        spotsDialog = new SpotsDialog(Update_Quiz_Questions.this);
        postquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ques = quesion.getText().toString();
                String optA = optionA.getText().toString();
                String optB = optionB.getText().toString();
                String optC = optionC.getText().toString();
                String optD = optionD.getText().toString();
                String crct = crctans.getText().toString();
                if (crct.equalsIgnoreCase(optA) || crct.equalsIgnoreCase(optB) || crct.equalsIgnoreCase(optC) || crct.equalsIgnoreCase(optD)){
                    if (ques.isEmpty()){
                        Move_Show.showToast("Enter Quesion");
                    }else if (optA.isEmpty()){
                        Move_Show.showToast("Option A is empty");
                    }else  if (optB.isEmpty()){
                        Move_Show.showToast("Option B is empty");
                    }else if (optC.isEmpty()){
                        Move_Show.showToast("Option C is empty");
                    }else if (optD.isEmpty()){
                        Move_Show.showToast("Option D is empty");
                    }else if (crct.isEmpty()){
                        Move_Show.showToast("Correct Answer is empty");
                    }else{
                        spotsDialog.show();
                        Call call= ApiUtil.getServiceClass().addquizquestions(ques,optA,optB,optC,optD,crct);
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.body().equals("1")){
                                    spotsDialog.dismiss();
                                    Move_Show.showToast("Question Updated");
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {

                            }
                        });
                    }
                }else{
                    Move_Show.showToast("Correct answer is not match the option's");
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Move_Show(Update_Quiz_Questions.this,Quiz_Events.class);
    }
}
