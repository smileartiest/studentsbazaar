package com.studentsbazaar.studentsbazaarapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
//        holder.shareimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                bitmap= ScreenShot.takescreenshotrootview(holder.postermeme);
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("image/jpeg");
//                String bitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"title", null);
//                Uri bitmapUri = Uri.parse(bitmapPath);
//                share.putExtra(Intent.EXTRA_STREAM,bitmapUri);
//                context.startActivity(Intent.createChooser(share,"Share via"));
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView username, posttime, smile, caption;
        ImageView postermeme, shareimg;
        CardView cardView;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            posttime = (TextView) itemView.findViewById(R.id.posttime);
            caption = (TextView) itemView.findViewById(R.id.postcaption);
            cardView = (CardView) itemView.findViewById(R.id.memecartview);
            postermeme = (ImageView) itemView.findViewById(R.id.memepost);
            shareimg = (ImageView) itemView.findViewById(R.id.shareimg);


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
