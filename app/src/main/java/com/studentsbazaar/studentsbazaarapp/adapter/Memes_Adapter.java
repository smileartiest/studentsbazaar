package com.studentsbazaar.studentsbazaarapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.TempDataBaseHandler;
import com.studentsbazaar.studentsbazaarapp.model.Memes_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Memes_Adapter extends RecyclerView.Adapter<Memes_Adapter.Myviewholder> {

    Context context;
    private LayoutInflater mInflater;
    List<Memes_Details> mData;
    String memepost, userid;
    SpotsDialog spotsDialog;
    int index;
    Bitmap bitmap;
    Controller controller;
    TempDataBaseHandler tempDataBaseHandler;

    public Memes_Adapter(Context context, List<Memes_Details> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
        spotsDialog = new SpotsDialog(context);
        controller = new Controller(context);
        tempDataBaseHandler = new TempDataBaseHandler(context);
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_meme_design, parent, false);
        return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final Memes_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);
        holder.datetime.setText(listItem.getCreated_Date());
        String img = "https://lh3.googleusercontent.com/proxy/b9ZPTyW6h_CYZaMFJEetCCDJICacupceugq9aCEmWfPBVzKZSssv8DM2J_bq6zhQjok3CXTHzyoOF8Q0ytbPXC9kjwiK-LZcwrQPR2H2inoOQnVhjZraCSkPdjAQh3U";
        Glide.with(context).load(listItem.getMemes()).listener(new RequestListener<Drawable>() {
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
        holder.username.setText(listItem.getUser_Name());
        holder.caption.setVisibility(View.GONE);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Controller.getmemeview().equals(Controller.MEMEVIEW)) {

                } else {
                    if (controller.getprefer().equals(Controller.ADMIN) || controller.getprefer().equals(Controller.MEMEACCEPT)) {
                        memepost = listItem.getMemes();
                        userid = listItem.getUser_Id();
                        index = position;
                        alertmeme();
                    }
                }
            }
        });
        holder.viewvers.setText("Total Likes "+listItem.getSmile());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                holder.like.setImageResource(R.drawable.like_icon);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Monitor(context).shareImage(listItem.getMemes());
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Monitor(context).DownloadImage(listItem.getMemes());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView username, datetime, caption, viewvers;
        ImageView postermeme, menu , like ,share , download;
        RelativeLayout cardView;
        GifImageView gifImageView;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.r_meme_usname);
            caption = itemView.findViewById(R.id.r_meme_content);
            postermeme = itemView.findViewById(R.id.r_meme_postpic);
            gifImageView = itemView.findViewById(R.id.r_meme_gif);
            cardView = itemView.findViewById(R.id.r_meme_card);
            menu = itemView.findViewById(R.id.r_meme_menuicon);
            datetime = itemView.findViewById(R.id.r_meme_datetime);
            viewvers = itemView.findViewById(R.id.r_meme_viewers);
            like = itemView.findViewById(R.id.r_meme_likeicon);
            share = itemView.findViewById(R.id.r_meme_share);
            download = itemView.findViewById(R.id.r_meme_download);
        }
    }

    void updatememe(String approv, String meme, String UID) {
        spotsDialog.show();
        Call<String> call = ApiUtil.getServiceClass().updatememepost(approv, meme, UID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("1")) {
                    spotsDialog.dismiss();
                    mData.remove(index);
                    notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    void alertmeme() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Meme Update...");
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updatememe("1", memepost, userid);

            }
        });
        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updatememe("2", memepost, userid);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
