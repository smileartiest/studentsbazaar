package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.PlacementActivity;
import com.studentsbazaar.studentsbazaarapp.model.Campus;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unchecked")
public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> implements Filterable {


    Context context;
    private LayoutInflater mInflater;
    List<Campus> mData;
    List<Campus> filterdata;

    public JobListAdapter(List<Campus> mData, PlacementActivity context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        filterdata = new ArrayList<>(mData);
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_view, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Campus listItem = mData.get(position);

        Resources resources = context.getResources();
        holder.setIsRecyclable(false);

        holder.tvDate.setText(listItem.getDate());
        holder.tvplaced.setText(listItem.getNo_of_Students());
        holder.tvSal_package.setText(listItem.getPackage() + " (Lakhs/Annum)");
        holder.tvcollege.setText(listItem.getCollege_Name());
        if (listItem.getDomain().length() != 0) {
            holder.tvDomain.setText(listItem.getDomain());
        } else {
            holder.ld.setVisibility(View.GONE);
        }

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

    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    private Filter examplefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Campus> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filterdata);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Campus item : filterdata) {
                    if (item.getCompany_Name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData.clear();
            mData.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCompany, tvDomain, tvcollege, tvSal_package, tvplaced, tvDate;
        ImageView imBookmar, imShare;
        CardView cardView;
        LinearLayout linearLayout, ld;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDomain = (TextView) itemView.findViewById(R.id.id_domain);
            ld = (LinearLayout) itemView.findViewById(R.id.layout_domain);
            tvCompany = (TextView) itemView.findViewById(R.id.tvCompName);
            tvcollege = (TextView) itemView.findViewById(R.id.id_college_name);
            tvSal_package = (TextView) itemView.findViewById(R.id.id_package);
            tvplaced = (TextView) itemView.findViewById(R.id.id_students_placed);
            tvDate = (TextView) itemView.findViewById(R.id.id_date);
            cardView = (CardView) itemView.findViewById(R.id.id_cardView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.id_cardLayout);


        }

    }
}
