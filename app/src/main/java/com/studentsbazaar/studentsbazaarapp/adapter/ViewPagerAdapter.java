package com.studentsbazaar.studentsbazaarapp.adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.EventActivity;
import com.studentsbazaar.studentsbazaarapp.activity.View_Details;
import com.studentsbazaar.studentsbazaarapp.activity.WebActivity;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    View view;
    private LayoutInflater mInflater;
    private Context context;
    String pdfName, TAG = "FILE", urlsite;
    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    List<Project_details> mData;
    Bitmap bitmap;
    Button tvShare, read_more;
    String weburl;
    Controller controller;

    public ViewPagerAdapter(EventActivity context, List<Project_details> drawerResponseList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = drawerResponseList;
        controller = new Controller(context);
        //this.viewPager2 = viewPager2;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.fragment_child, parent, false);
        tvShare = (Button) view.findViewById(R.id.share);
        read_more = (Button) view.findViewById(R.id.read_more);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Project_details listItem = mData.get(position);

        Resources resources = context.getResources();
        holder.setIsRecyclable(false);
        Glide.with(context)
                .load(listItem.getPoster())
                .placeholder(R.drawable.please)
                .into(holder.imageView);
        Log.d("IMG", listItem.getPoster() + "");
        holder.tvHead.setText(listItem.getEvent_Title());
        holder.tvCategory.setText(listItem.getEvent_Name());
        holder.tvcon.setText(listItem.getConducted_By());
        urlsite = listItem.getEvent_Website();
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(listItem.getEvent_Start_Date());
            String[] sdate = date1.toString().split(" ");
            if (sdate[0].equals("Sat") || sdate[0].equals("Sun")) {
                holder.tvStartDate.setTextColor(Color.RED);
                holder.tvmonth.setTextColor(Color.RED);

            } else {
                holder.tvStartDate.setTextColor(Color.parseColor("#1B4F72"));
                holder.tvmonth.setTextColor(Color.parseColor("#1B4F72"));
            }
            holder.tvStartDate.setText(sdate[0]);
            holder.tvmonth.setText(sdate[1] + " " + sdate[2]);
        } catch (Exception e) {

        }
        holder.tvOrganizer.setText(listItem.getEvent_Organiser());
        holder.tvCity.setText(listItem.getCollege_District());
        holder.tvState.setText(listItem.getCollege_State());
        holder.regview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listItem.getEvent_Website().contains("http://") || listItem.getEvent_Website().contains("https://")) {

                    weburl = listItem.getEvent_Website();
                } else {
                    weburl = "http://" + listItem.getEvent_Website();
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", weburl);
                bundle.putString("data", "reg url");
                bundle.putString("title", "reg title");
                Intent in = new Intent(context, WebActivity.class);
                in.putExtras(bundle);
                context.startActivity(in);
            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(context, perms[0]) != PackageManager.PERMISSION_GRANTED) {
                    openDialog();
                } else {
                    takeScreenshot(holder.itemView);
                }
            }
        });

      /*  holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageView.
                read_more.performClick();
            }
        });*/
        read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sharedPreferences = context.getSharedPreferences("view_details", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("coid", listItem.getEvent_Coordinator());
                editor.putString("post", listItem.getPoster());
                editor.putString("title", listItem.getEvent_Title());
                editor.putString("cat", listItem.getEvent_Type());
                editor.putString("sdate", listItem.getEvent_Start_Date());
                editor.putString("edate", listItem.getEvent_End_Date());
                editor.putString("organiser", listItem.getEvent_Organiser());
                editor.putString("city", listItem.getCollege_District());
                editor.putString("state", listItem.getCollege_State());
                editor.putString("dis", listItem.getEvent_Discription());
                editor.putString("Eventdetails", listItem.getEvent_Details());
                editor.putString("dept", listItem.getDept());
                editor.putString("guest", listItem.getEvent_guest());
                editor.putString("pronites", listItem.getEvent_pro_nites());
                editor.putString("etheme", listItem.getEvent_Name());
                editor.putString("accom", listItem.getEvent_accomodations());
                editor.putString("lastdate", listItem.getLast_date_registration());
                editor.putString("fees", listItem.getEntry_Fees());
                editor.putString("htr", listItem.getEvent_how_to_reach());
                editor.putString("cpname1", listItem.getContact_Person1_Name());
                editor.putString("cpno1", listItem.getContact_Person1_No());
                editor.putString("cpname2", listItem.getContact_Person2_Name());
                editor.putString("cpno2", listItem.getContact_Person2_No());
                editor.putString("webevent", listItem.getEvent_Website());
                editor.putString("webcoll", listItem.getCollege_Website());
                editor.putString("insta",listItem.getEvent_Instagram());
                editor.putString("view", "view");
                editor.apply();
                controller.adddesignprefer(Controller.MORE);
                Bundle b = new Bundle();
                b.putString("view", "non_view");
                Intent intent = new Intent(context, View_Details.class);
                intent.putExtras(b);
                context.startActivity(intent);


            }
        });


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

        TextView tvHead, tvCategory, tvStartDate, tvOrganizer, tvCity, tvState, tvmonth, regview, tvcon;
        ImageView imageView;
        LinearLayout layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHead = (TextView) itemView.findViewById(R.id.head_title);
            tvCategory = itemView.findViewById(R.id.id_category);
            tvStartDate = itemView.findViewById(R.id.id_start_date);
            tvmonth = itemView.findViewById(R.id.id_start_month);
            tvOrganizer = itemView.findViewById(R.id.id_organiser);
            tvCity = itemView.findViewById(R.id.id_city);
            tvState = itemView.findViewById(R.id.id_state);
            regview = itemView.findViewById(R.id.regview);
            tvcon = itemView.findViewById(R.id.id_cond_dept);
            layout = itemView.findViewById(R.id.layout);


            imageView = itemView.findViewById(R.id.tvParent);
        }
    }

    private void takeScreenshot(View view) {

        //btmLayout.setVisibility(View.VISIBLE);
        read_more.setVisibility(View.GONE);
        tvShare.setVisibility(View.GONE);


        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/StudentsBazImageFolder");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
                Log.i(TAG, "Created a new directory for PDF");
            }

            pdfName = "Rojgar" + now + ".jpg";

            view.setDrawingCacheEnabled(true);
            bitmap = Bitmap.createBitmap(view.getDrawingCache());

            view.setDrawingCacheEnabled(false);

            File imageFile = new File(docsFolder.getAbsolutePath(), pdfName);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            //btmLayout.setVisibility(View.INVISIBLE);

            openScreenshot(imageFile, context);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile, Context context) {
        read_more.setVisibility(View.VISIBLE);
        tvShare.setVisibility(View.VISIBLE);
        Log.d("URLPATH", imageFile.toString());
        Uri uri = Uri.fromFile(imageFile);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, "Students Bazaar,India's highest rated students app.\nplease follow link to participate this event\n" + urlsite + "\nSource : Students Bazaar\nhttp://tiny.cc/3lnhjz");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void openDialog() {

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("Hey there ! Permission not enabled!");
        builder.setMessage("Enable the media permission.\nGo to settings->App Manager->Students Bazaar->Permssion->Enable the missed permissions.");
        builder.addButton("DISMISS", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        builder.show();
    }


}
