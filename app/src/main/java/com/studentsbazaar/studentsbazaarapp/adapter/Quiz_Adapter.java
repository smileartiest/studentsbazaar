package com.studentsbazaar.studentsbazaarapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Api;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.AddEvent2;
import com.studentsbazaar.studentsbazaarapp.activity.AddEvent3;
import com.studentsbazaar.studentsbazaarapp.activity.EventActivity;
import com.studentsbazaar.studentsbazaarapp.activity.HomeActivity;
import com.studentsbazaar.studentsbazaarapp.activity.Quiz_Events;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

public class Quiz_Adapter extends RecyclerView.Adapter<Quiz_Adapter.Myviewholder> {
    private LayoutInflater mInflater;
    private List<Quiz_Details> mData;
    private Context context;
    private View view;
    private int results = 0;

    public Quiz_Adapter(Quiz_Events context, List<Quiz_Details> drawerResponseList) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = drawerResponseList;
        this.context = context;
    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.quiz_design, parent, false);

        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, int position) {
        final Quiz_Details listItem = mData.get(position);
        Resources resources = context.getResources();
        holder.setIsRecyclable(false);
        holder.question.setText(listItem.getQuiz_ques());
        holder.option1.setText(listItem.getOption1());
        holder.option2.setText(listItem.getOption2());
        holder.option3.setText(listItem.getOption3());
        holder.option4.setText(listItem.getOption4());
        Log.d("RESPONSE1-ADAPTER", listItem.getOption1());
        holder.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int childCount = radioGroup.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(x);

                    if (btn.getId() == i) {

                        Toast.makeText(context, btn.getText().toString(), Toast.LENGTH_SHORT).show();
                        if (btn.getText().toString().equals(listItem.getCrct_Ans())) {
                            ApiUtil.QUIZ_RESULT = ApiUtil.QUIZ_RESULT +1;

                        }else{

                            ApiUtil.QUIZ_RESULT = ApiUtil.QUIZ_RESULT + 0 ;
                        }
                    }

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView question;
        RadioButton option1, option2, option3, option4, gettext;
        RadioGroup group;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.quiz_question);
            option1 = (RadioButton) itemView.findViewById(R.id.optA);
            option2 = (RadioButton) itemView.findViewById(R.id.optB);
            option3 = (RadioButton) itemView.findViewById(R.id.optC);
            option4 = (RadioButton) itemView.findViewById(R.id.optD);
            group = (RadioGroup) itemView.findViewById(R.id.radioGroup);

        }
    }


}
