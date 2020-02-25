package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.model.Tech_News_model;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class Tech_News_Adapter extends RecyclerView.Adapter<Tech_News_Adapter.MyviewHolder> {
    private LayoutInflater mInflater;
    List<Tech_News_model> mData;
    Context context;
    Bitmap bitmap;
    SpotsDialog spotsDialog;

    public Tech_News_Adapter(Context context, List<Tech_News_model> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
        spotsDialog = new SpotsDialog(context);
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meme_design, parent, false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int position) {
        final Tech_News_model listItem = mData.get(position);
        holder.setIsRecyclable(false);
        Glide.with(context).load(listItem.getNews_Poster()) .placeholder(R.drawable.please).into(holder.postermeme);
        holder.username.setText("Students Bazaar");
        holder.caption.setText(listItem.getNews_Dis());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              new Monitor(context).shareImage(listItem.getNews_Poster());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView username, smile, caption;
        ImageView postermeme, share;
        CardView cardView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            caption = (TextView) itemView.findViewById(R.id.postcaption);
            cardView = (CardView) itemView.findViewById(R.id.memecartview);
            postermeme = (ImageView) itemView.findViewById(R.id.memepost);
            share = (ImageView) itemView.findViewById(R.id.shareimg);

        }
    }
}
