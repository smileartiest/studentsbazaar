package com.studentsbazaar.studentsbazaarapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.model.Memes_Details;

import java.util.List;

public class Memes_Adapter extends RecyclerView.Adapter<Memes_Adapter.Myviewholder> {

    Typeface tf_regular;
    Context context;
    private LayoutInflater mInflater;
    List<Memes_Details> mData;

    public Memes_Adapter(Context context, List<Memes_Details> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public Memes_Adapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meme_design, parent, false);

        tf_regular = Typeface.createFromAsset(v.getContext().getAssets(), "caviar.ttf");

        return new Memes_Adapter.Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Memes_Adapter.Myviewholder holder, int position) {
        final Memes_Details listItem = mData.get(position);
        holder.setIsRecyclable(false);
        Glide.with(context).load(listItem.getMemes()).into(holder.postermeme);
        holder.username.setText(listItem.getUser_Name());
        holder.caption.setText(listItem.getCaption());
        holder.posttime.setText("Date: "+listItem.getCreated_Date());
        holder.smile.setText(listItem.getSmile() + " Smiles");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView username, posttime, smile, caption;
        ImageView postermeme;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            posttime = (TextView)itemView.findViewById(R.id.posttime);
            smile = (TextView)itemView.findViewById(R.id.smilecount);
            caption = (TextView)itemView.findViewById(R.id.postcaption);
            postermeme = (ImageView) itemView.findViewById(R.id.memepost);

        }
    }
}
