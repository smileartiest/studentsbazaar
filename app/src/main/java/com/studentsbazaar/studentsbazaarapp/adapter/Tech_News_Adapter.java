package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.model.Tech_News_model;

import java.util.List;
import java.util.Random;

import dmax.dialog.SpotsDialog;
import pl.droidsonroids.gif.GifImageView;

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
                .inflate(R.layout.row_meme_design, parent, false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int position) {
        final Tech_News_model listItem = mData.get(position);
        holder.setIsRecyclable(false);
        holder.datetime.setText(listItem.getCreated_Date());
        Glide.with(context).load(listItem.getNews_Poster()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.gifImageView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.gifImageView.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.postermeme);
        holder.username.setText("Students Bazaar");
        if(listItem.getNews_Dis().length()!=0){
        holder.caption.setText(listItem.getNews_Dis());}else{
            holder.caption.setVisibility(View.GONE);
        }
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pmenu = new PopupMenu(context , holder.menu);
                pmenu.inflate(R.menu.mems_menu);
                pmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_meme_download:
                                Toast.makeText(context, "wait for update", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                pmenu.show();
            }
        });
        Random r = new Random();
        holder.viewvers.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView username, datetime,caption,viewvers;
        ImageView postermeme,menu;
        RelativeLayout cardView;
        GifImageView gifImageView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            username =  itemView.findViewById(R.id.r_meme_usname);
            caption =  itemView.findViewById(R.id.r_meme_content);
            postermeme = itemView.findViewById(R.id.r_meme_postpic);
            gifImageView= itemView.findViewById(R.id.r_meme_gif);
            cardView = itemView.findViewById(R.id.r_meme_card);
            menu = itemView.findViewById(R.id.r_meme_menuicon);
            datetime = itemView.findViewById(R.id.r_meme_datetime);
            viewvers = itemView.findViewById(R.id.r_meme_viewers);
        }
    }
}
