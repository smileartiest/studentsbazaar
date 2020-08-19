package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.View_Details;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.MyAdapter> {

    View view;
    private LayoutInflater mInflater;
    private Context context;
    List<Project_details> mData;
    SpotsDialog spotsDialog ;
    Controller controller;

    public MyEventAdapter(Context context, List<Project_details> mData) {
        this.context = context;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        controller = new Controller(context);
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.row_myevents , parent , false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {
        holder.setIsRecyclable(false);

        final Project_details listItem = mData.get(position);

        holder.eventname.setText(listItem.getEvent_Name());
        holder.coordinator.setText(listItem.getContact_Person1_Name());
        holder.organiser.setText(listItem.getEvent_Organiser());
        holder.phone.setText(listItem.getContact_Person1_No());
        holder.city.setText(listItem.getCollege_Address());
        holder.department.setText(listItem.getConducted_By());

        if(listItem.getAccepted().equals("0")){
            holder.sts.setVisibility(View.VISIBLE);
            holder.sts.setImageResource(R.drawable.yello_current_icon);
        }else{
            holder.sts.setVisibility(View.VISIBLE);
            holder.sts.setImageResource(R.drawable.complete_green_tik_icon);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("view_details", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("coid",listItem.getEvent_Coordinator());
                editor.putString("post",listItem.getPoster());
                editor.putString("title",listItem.getEvent_Title());
                editor.putString("cat",listItem.getEvent_Type());
                editor.putString("sdate",listItem.getEvent_Start_Date());
                editor.putString("edate",listItem.getEvent_End_Date());
                editor.putString("organiser",listItem.getEvent_Organiser());
                editor.putString("city",listItem.getCollege_District());
                editor.putString("state",listItem.getCollege_State());
                editor.putString("dis",listItem.getEvent_Discription());
                editor.putString("Eventdetails",listItem.getEvent_Details());
                editor.putString("dept",listItem.getDept());
                editor.putString("guest",listItem.getEvent_guest());
                editor.putString("pronites",listItem.getEvent_pro_nites());
                editor.putString("etheme",listItem.getEvent_Name());
                editor.putString("accom",listItem.getEvent_accomodations());
                editor.putString("lastdate",listItem.getLast_date_registration());
                editor.putString("fees",listItem.getEntry_Fees());
                editor.putString("htr",listItem.getEvent_how_to_reach());
                editor.putString("cpname1",listItem.getContact_Person1_Name());
                editor.putString("cpno1",listItem.getContact_Person1_No());
                editor.putString("cpname2",listItem.getContact_Person2_Name());
                editor.putString("cpno2",listItem.getContact_Person2_No());
                editor.putString("webevent",listItem.getEvent_Website());
                editor.putString("webcoll",listItem.getCollege_Website());
                editor.putString("insta",listItem.getEvent_Instagram());
                editor.apply();
                Bundle b = new Bundle();
                b.putString("view","non_view");
                Controller.adddesignprefer(Controller.PREFER);
                Intent intent=new Intent(context, View_Details.class);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {

        public TextView eventname, coordinator, organiser, department, phone, city;
        ImageView sts;
        ConstraintLayout cardView;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            spotsDialog = new SpotsDialog(context);
            sts = itemView.findViewById(R.id.myevent_sts);
            eventname = (TextView) itemView.findViewById(R.id.myevent_name);
            coordinator = (TextView) itemView.findViewById(R.id.myevent_coordinator_name);
            organiser = (TextView) itemView.findViewById(R.id.myevent_organiser_name);
            department = (TextView) itemView.findViewById(R.id.myevent_deptartment);
            phone = (TextView) itemView.findViewById(R.id.myevent_phoneno);
            city = (TextView) itemView.findViewById(R.id.myevent_eventcity);
            cardView = itemView.findViewById(R.id.myevent_card);
        }
    }

}
