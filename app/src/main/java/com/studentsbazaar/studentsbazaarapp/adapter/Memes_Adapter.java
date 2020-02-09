package com.studentsbazaar.studentsbazaarapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.model.Memes_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
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

    public Memes_Adapter(Context context, List<Memes_Details> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
        spotsDialog = new SpotsDialog(context);
    }

    @NonNull
    @Override
    public Memes_Adapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meme_design, parent, false);
        return new Memes_Adapter.Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Memes_Adapter.Myviewholder holder, final int position) {
        final Memes_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);
        Glide.with(context).load(listItem.getMemes()).into(holder.postermeme);
        holder.username.setText(listItem.getUser_Name());
        holder.caption.setText("");
        holder.posttime.setText("Date: " + listItem.getCreated_Date());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DETAILS", context.MODE_PRIVATE);
                if (sharedPreferences.getString("meme", "").equals("admin")) {
                    memepost = listItem.getMemes();
                    userid = listItem.getUser_Id();
                    index = position;
                    alertmeme();

                }
            }
        });
        holder.shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(context)
                        .load(listItem.getMemes())
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
        holder.downimg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String IMG = listItem.getMemes();


            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView username, posttime, smile, caption;
        ImageView postermeme, shareimg, downimg;
        CardView cardView;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            posttime = (TextView) itemView.findViewById(R.id.posttime);
            caption = (TextView) itemView.findViewById(R.id.postcaption);
            cardView = (CardView) itemView.findViewById(R.id.memecartview);
            postermeme = (ImageView) itemView.findViewById(R.id.memepost);
            shareimg = (ImageView) itemView.findViewById(R.id.shareimg);
            downimg = (ImageView) itemView.findViewById(R.id.downimg);


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
                updatememe("1", memepost, userid);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
