package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.Quiz_Events;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
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
                holder.option1.setEnabled(false);
                holder.option2.setEnabled(false);
                holder.option3.setEnabled(false);
                holder.option4.setEnabled(false);
                ApiUtil.QUIZ_ATTENT=1;
                int childCount = radioGroup.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(x);

                    if (btn.getId() == i) {


                        if (btn.getText().toString().equals(listItem.getCrct_Ans())) {
                            ApiUtil.QUIZ_RESULT = ApiUtil.QUIZ_RESULT + 1;
                            if (holder.option1.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                                holder.option1.setBackgroundResource(R.drawable.button);
                                holder.option1.setTextColor(Color.parseColor("#FFFFFF"));
                                // holder.option1.setBackgroundResource(R.drawable.button);
                            } else if (holder.option2.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                                holder.option2.setBackgroundResource(R.drawable.button);
                                holder.option2.setTextColor(Color.parseColor("#FFFFFF"));
                            } else if (holder.option3.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                                holder.option3.setBackgroundResource(R.drawable.button);
                                holder.option3.setTextColor(Color.parseColor("#FFFFFF"));
                            } else if (holder.option4.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                                holder.option4.setBackgroundResource(R.drawable.button);
                                holder.option4.setTextColor(Color.parseColor("#FFFFFF"));
                            }


                        } else {
                            if (holder.option1.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                                holder.option1.setBackgroundResource(R.drawable.button);
                                holder.option1.setTextColor(Color.parseColor("#FFFFFF"));
                            } else if (holder.option2.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                                holder.option2.setBackgroundResource(R.drawable.button);
                                holder.option2.setTextColor(Color.parseColor("#FFFFFF"));
                            } else if (holder.option3.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                                holder.option3.setBackgroundResource(R.drawable.button);
                                holder.option3.setTextColor(Color.parseColor("#FFFFFF"));
                            } else if (holder.option4.getText().toString().equalsIgnoreCase(btn.getText().toString())) {
                                holder.option4.setBackgroundResource(R.drawable.button);
                                holder.option4.setTextColor(Color.parseColor("#FFFFFF"));
                            }

                            ApiUtil.QUIZ_RESULT = ApiUtil.QUIZ_RESULT + 0;
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
