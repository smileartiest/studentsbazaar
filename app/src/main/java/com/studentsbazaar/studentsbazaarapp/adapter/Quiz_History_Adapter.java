package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.TimeDate;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_History;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Quiz_History_Adapter extends RecyclerView.Adapter<Quiz_History_Adapter.Myviewholder> {

    View view;
    private LayoutInflater mInflater;
    private List<Quiz_History>  quiz_historyList;
    private Context context;
    int START_TIME = 9;
    int LOCAL_TIME = 19;
    int CURRENT_TIME;
    Calendar calander;

    public Quiz_History_Adapter(Context context , List<Quiz_History> quiz_historyList) {
        this.mInflater = LayoutInflater.from(context);
        this.quiz_historyList = quiz_historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.row_quiz_history , parent , false);
        return new Quiz_History_Adapter.Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final Quiz_History qs_list = quiz_historyList.get(position);
        holder.setIsRecyclable(false);
        holder.sno.setText(String.valueOf(position+1));
        holder.qs_no.setText(String.valueOf(qs_list.getQuiz_ID()));

        calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        String time = simpleDateFormat.format(calander.getTime());
        Log.d("Time", time);
        CURRENT_TIME = Integer.valueOf(time);

        if(new TimeDate().getDateYMD().equals(qs_list.getCreate_Date())){
            if (START_TIME <= CURRENT_TIME && LOCAL_TIME > CURRENT_TIME){
                holder.at_date.setText("wait for result");
                holder.sts.setImageResource(R.drawable.yello_current_icon);
            }else{
                holder.at_date.setText(qs_list.getCreate_Date());
                if(qs_list.getScore() == 1 ){
                    holder.sts.setImageResource(R.drawable.green_complete_icon);
                }else{
                    holder.sts.setImageResource(R.drawable.wrong_icon);
                }
            }
        }else{
            holder.at_date.setText(qs_list.getCreate_Date());
            if(qs_list.getScore() == 1 ){
                holder.sts.setImageResource(R.drawable.green_complete_icon);
            }else{
                holder.sts.setImageResource(R.drawable.wrong_icon);
            }
        }
    }

    @Override
    public int getItemCount() {
        return quiz_historyList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView sno,qs_no,at_date;
        ImageView sts;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.row_qs_history_sno);
            qs_no = itemView.findViewById(R.id.row_qs_history_qs_no);
            at_date = itemView.findViewById(R.id.row_qs_history_at_date);
            sts = itemView.findViewById(R.id.row_qs_history_ans_sts);
        }
    }

}
