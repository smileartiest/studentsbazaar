package com.studentsbazaar.studentsbazaarapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.Quiz_Events;
import com.studentsbazaar.studentsbazaarapp.controller.Quiz_Control;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quiz_Result_Adapter extends RecyclerView.Adapter<Quiz_Result_Adapter.Myviewholder> {

    SpotsDialog progressDialog;
    private LayoutInflater mInflater;
    private List<Quiz_Details> mData;
    private Context context;
    private View view;
    private int results = 0;

    public Quiz_Result_Adapter(Quiz_Events context, List<Quiz_Details> drawerResponseList) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = drawerResponseList;
        this.progressDialog = new SpotsDialog(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.row_quiz_result, parent, false);
        return new Quiz_Result_Adapter.Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {

        final Quiz_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);

        holder.qsno.setText(String.valueOf(position+1));
        holder.qsid.setText(listItem.getQuiz_Id());
        if(listItem.getQuiz_Type().equals("0")){
            holder.qstype.setText("Choose best answer");
        }else{
            holder.qstype.setText("Fill in the blanks");
        }

        holder.qsans.setText(listItem.getCrct_Ans());

        if(listItem.getFlag() == 1){
            holder.qstst.setImageResource(R.drawable.complete_green_tik_icon);
            holder.complete.setVisibility(View.GONE);
        }else if(listItem.getFlag()==0){
            if(listItem.getQuiz_Id().equals(Quiz_Control.getqid())){
                holder.qstst.setImageResource(R.drawable.yello_current_icon);
                holder.complete.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.INVISIBLE);
            }else{
                holder.qstst.setVisibility(View.INVISIBLE);
                holder.complete.setVisibility(View.INVISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
            }
        }
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setTitle("Conformation");
                ad.setMessage("Are you sure ,  conform this question was complete?");
                ad.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progressDialog.show();
                        Call<String> call = ApiUtil.getServiceClass().updateqsflag(ApiUtil.UPDATE_DAILY_FLAG);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                progressDialog.dismiss();
                                if(response.isSuccessful()){
                                    if(response.body().equals("1")){
                                        Toast.makeText(context, "update successfull", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(context, "update error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(context, "update failed. please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                ad.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setTitle("Conformation");
                ad.setMessage("Are you sure ,  conform you delete this question ?");
                ad.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        holder.info_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog(listItem.getQuiz_Id() , listItem.getQuiz_ques() , listItem.getQuiz_Ans());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView qsno,qsid,qstype,qsans;
        ImageView qstst,info_icon;
        Button complete,delete;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            qsno = itemView.findViewById(R.id.r_quiz_qno);
            qsid = itemView.findViewById(R.id.r_quiz_qid);
            qstype = itemView.findViewById(R.id.r_quiz_qtype);
            qsans = itemView.findViewById(R.id.r_quiz_qcans);
            qstst = itemView.findViewById(R.id.r_quiz_qsts);
            complete = itemView.findViewById(R.id.r_quiz_complete);
            delete = itemView.findViewById(R.id.r_quiz_delete);
            info_icon = itemView.findViewById(R.id.r_quiz_info);
        }
    }

    void opendialog(String question , String qs_url , String ans_url){
        Dialog d = new Dialog(context);
        d.setContentView(R.layout.dialog_quiz_info);
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = d.findViewById(R.id.d_q_info_qs_title);
        ImageView pic_question = d.findViewById(R.id.d_q_info_qs_pic);
        ImageView pic_answer = d.findViewById(R.id.d_q_info_ans_pic);
        ImageView cancel_icon = d.findViewById(R.id.d_q_info_cancel);

        title.setText("Question ID ."+question);
        Glide.with(context).load(qs_url).into(pic_question);
        Glide.with(context).load(ans_url).into(pic_answer);

        cancel_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();
            }
        });
        d.show();
    }

}


