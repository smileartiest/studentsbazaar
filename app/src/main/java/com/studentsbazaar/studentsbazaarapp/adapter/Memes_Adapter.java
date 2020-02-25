package com.studentsbazaar.studentsbazaarapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.model.Memes_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class Memes_Adapter extends RecyclerView.Adapter<Memes_Adapter.Myviewholder> {


    Context context;
    private LayoutInflater mInflater;
    List<Memes_Details> mData;
    String memepost, userid;
    SpotsDialog spotsDialog;
    int index;
    Bitmap bitmap;
    Controller controller;

    public Memes_Adapter(Context context, List<Memes_Details> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
        spotsDialog = new SpotsDialog(context);
        controller = new Controller(context);
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meme_design, parent, false);
        return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final Memes_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);
        String img ="https://lh3.googleusercontent.com/proxy/b9ZPTyW6h_CYZaMFJEetCCDJICacupceugq9aCEmWfPBVzKZSssv8DM2J_bq6zhQjok3CXTHzyoOF8Q0ytbPXC9kjwiK-LZcwrQPR2H2inoOQnVhjZraCSkPdjAQh3U";
        Glide.with(context).load(listItem.getMemes()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
               holder.gifImageView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
               holder.gifImageView.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.postermeme);
        holder.username.setText(listItem.getUser_Name());
        holder.caption.setText("");
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
        holder.shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              new Monitor(context).shareImage(listItem.getMemes());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView username, smile, caption;
        ImageView postermeme, shareimg;
        CardView cardView;
        GifImageView gifImageView;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            caption = (TextView) itemView.findViewById(R.id.postcaption);
            cardView = (CardView) itemView.findViewById(R.id.memecartview);
            postermeme = (ImageView) itemView.findViewById(R.id.memepost);
            shareimg = (ImageView) itemView.findViewById(R.id.shareimg);
            gifImageView=(GifImageView)itemView.findViewById(R.id.gifmeme);



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
