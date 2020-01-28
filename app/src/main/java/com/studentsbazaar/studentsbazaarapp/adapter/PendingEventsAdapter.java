package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.Pending_Events;
import com.studentsbazaar.studentsbazaarapp.activity.View_Details;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class PendingEventsAdapter extends RecyclerView.Adapter<PendingEventsAdapter.ViewHolder> {
    View view;
    private LayoutInflater mInflater;
    private Context context;
    Typeface tf_regular;
    List<Project_details> mData;
    SpotsDialog spotsDialog ;

    public PendingEventsAdapter(Pending_Events context, List<Project_details> drawerResponseList) {
        this.context = context;
        this.mData = drawerResponseList;
        this.mInflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public PendingEventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.pending_events, parent, false);
        tf_regular = Typeface.createFromAsset(view.getContext().getAssets(), "caviar.ttf");
        /*StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());*/
        return new PendingEventsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingEventsAdapter.ViewHolder holder, int position) {
        final Project_details listItem = mData.get(position);

        Resources resources = context.getResources();
        holder.setIsRecyclable(false);

        holder.eventname.setText(listItem.getEvent_Name());
        holder.coordinator.setText(listItem.getEvent_Coordinator());
        holder.College.setText(listItem.getCollege_Name());
        holder.phone.setText(listItem.getContact_Person1_No());
        holder.city.setText(listItem.getCollege_Address());
        holder.department.setText(listItem.getDept());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                SharedPreferences sharedPreferences = context.getSharedPreferences("view_details", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("coid",listItem.getEvent_Coordinator());
                editor.putString("post",listItem.getPoster());
                editor.putString("title",listItem.getEvent_Title());
                editor.putString("cat",listItem.getEvent_Type());
                editor.putString("sdate",listItem.getEvent_Start_Date());
                editor.putString("edate",listItem.getEvent_End_Date());
                editor.putString("organiser",listItem.getEvent_sponsors());
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
                editor.commit();
                Intent intent=new Intent(context, View_Details.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventname, coordinator, College, department, phone, city;
        ImageView imBookmar, imShare;
        CardView cardView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spotsDialog = new SpotsDialog(context);
            eventname = (TextView) itemView.findViewById(R.id.tvEventName);
            coordinator = (TextView) itemView.findViewById(R.id.id_coordinator_name);
            College = (TextView) itemView.findViewById(R.id.id_coll_name);
            department = (TextView) itemView.findViewById(R.id.id_deptartment);
            phone = (TextView) itemView.findViewById(R.id.id_phoneno);
            city = (TextView) itemView.findViewById(R.id.id_eventcity);
            cardView = (CardView) itemView.findViewById(R.id.id_eventcardView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.id_eventcardLayout);
            eventname.setTypeface(tf_regular);
            coordinator.setTypeface(tf_regular);
            College.setTypeface(tf_regular);
            department.setTypeface(tf_regular);
            phone.setTypeface(tf_regular);
            city.setTypeface(tf_regular);
        }
    }


}
