package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
    public Tech_News_Adapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meme_design, parent, false);
        return new Tech_News_Adapter.MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Tech_News_Adapter.MyviewHolder holder, int position) {
        final Tech_News_model listItem = mData.get(position);
        holder.setIsRecyclable(false);
        Glide.with(context).load(listItem.getNews_Poster()).into(holder.postermeme);
        holder.username.setText("Students Bazaar");
        holder.caption.setText(listItem.getNews_Dis());
        holder.posttime.setText("Date: " + listItem.getCreated_Date());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(context)
                        .load(listItem.getNews_Poster())
                        .asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

                        .into(new SimpleTarget<Bitmap>(250, 250) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                spotsDialog.dismiss();

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_TEXT, "Students Bazaar,India's highest rated students app.\nSource : Students Bazaar\nhttp://tiny.cc/3lnhjz\"");
                                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), resource, "", null);


                                Uri screenshotUri = Uri.parse(path);


                                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                intent.setType("image/*");

                                context.startActivity(Intent.createChooser(intent, "Share image via..."));
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();


                                super.onLoadFailed(e, errorDrawable);
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                spotsDialog.show();
                                Toast.makeText(context, "Please wait", Toast.LENGTH_SHORT).show();

                                super.onLoadStarted(placeholder);
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView username, posttime, smile, caption;
        ImageView postermeme, share;
        CardView cardView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            posttime = (TextView) itemView.findViewById(R.id.posttime);
            caption = (TextView) itemView.findViewById(R.id.postcaption);
            cardView = (CardView) itemView.findViewById(R.id.memecartview);
            postermeme = (ImageView) itemView.findViewById(R.id.memepost);
            share = (ImageView) itemView.findViewById(R.id.shareimg);

        }
    }
}
