package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
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
import com.studentsbazaar.studentsbazaarapp.activity.PlacementActivity;
import com.studentsbazaar.studentsbazaarapp.model.Campus;

import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {

    Typeface tf_regular;
    Context context;
    private LayoutInflater mInflater;
    List<Campus> mData;
    public JobListAdapter(List<Campus> drawerResponseList, PlacementActivity context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = drawerResponseList;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_view, parent, false);

        tf_regular = Typeface.createFromAsset(v.getContext().getAssets(), "caviar.ttf");

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Campus listItem = mData.get(position);

        Resources resources = context.getResources();
        holder.setIsRecyclable(false);

        holder.tvDate.setText(listItem.getDate());
        holder.tvplaced.setText(listItem.getNo_of_Students());
        holder.tvSal_package.setText(listItem.getPackage()+" (Lakhs/Annum)");
        holder.tvcollege.setText(listItem.getCollege_Name());
        holder.tvDomain.setText(listItem.getDomain());
        holder.tvCompany.setText(listItem.getCompany_Name());

    }

    @Override
    public int getItemCount() {

        return mData.size();


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCompany, tvDomain, tvcollege, tvSal_package, tvplaced, tvDate;
        ImageView imBookmar, imShare;
        CardView cardView;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDomain = (TextView) itemView.findViewById(R.id.id_domain);
            tvCompany = (TextView) itemView.findViewById(R.id.tvCompName);
            tvcollege = (TextView) itemView.findViewById(R.id.id_college_name);
            tvSal_package = (TextView) itemView.findViewById(R.id.id_package);
            tvplaced = (TextView) itemView.findViewById(R.id.id_students_placed);
            tvDate = (TextView) itemView.findViewById(R.id.id_date);
            cardView = (CardView) itemView.findViewById(R.id.id_cardView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.id_cardLayout);
            tvDomain.setTypeface(tf_regular);
            tvCompany.setTypeface(tf_regular);
            tvcollege.setTypeface(tf_regular);
            tvSal_package.setTypeface(tf_regular);
            tvplaced.setTypeface(tf_regular);
            tvDate.setTypeface(tf_regular);
            //tvEndDate.setTypeface(tf_regular);


        }

    }
}
