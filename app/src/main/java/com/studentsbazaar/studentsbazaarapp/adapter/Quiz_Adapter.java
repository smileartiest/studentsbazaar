package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.Quiz_Events;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_Details;

import java.util.List;

public class Quiz_Adapter extends RecyclerView.Adapter<Quiz_Adapter.Myviewholder> {
    private LayoutInflater mInflater;
    private List<Quiz_Details> mData;
    private Context context;
    private Typeface tf_regular;
    private View view;
    public Quiz_Adapter(Quiz_Events context, List<Quiz_Details> drawerResponseList) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = drawerResponseList;
        this.context = context;
    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.quiz_design, parent, false);
        tf_regular = Typeface.createFromAsset(view.getContext().getAssets(), "caviar.ttf");

        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Quiz_Adapter.Myviewholder holder, int position) {
        final Quiz_Details listItem = mData.get(position);
        Resources resources = context.getResources();
        holder.setIsRecyclable(false);
        holder.question.setText(listItem.getQuiz_ques());
        holder.option1.setText(listItem.getOption1());
        holder.option2.setText(listItem.getOption2());
        holder.option3.setText(listItem.getOption3());
        holder.option4.setText(listItem.getOption4());
        Log.d("RESPONSE1-ADAPTER",listItem.getOption1());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView question;
        RadioButton option1, option2, option3, option4;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.quiz_question);
            option1 = (RadioButton) itemView.findViewById(R.id.optA);
            option2 = (RadioButton) itemView.findViewById(R.id.optB);
            option3 = (RadioButton) itemView.findViewById(R.id.optC);
            option4 = (RadioButton) itemView.findViewById(R.id.optD);

        }
    }
}
