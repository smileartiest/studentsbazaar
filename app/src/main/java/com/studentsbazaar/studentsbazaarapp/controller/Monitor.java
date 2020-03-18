package com.studentsbazaar.studentsbazaarapp.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.studentsbazaar.studentsbazaarapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import dmax.dialog.SpotsDialog;

public class Monitor {
    private Context context;
    String fileUri;
    SpotsDialog spotsDialog;
    String dest_file_path = "test.pdf";
    int downloadedSize = 0, totalsize;
    float per = 0;

    public Monitor(Context context) {
        this.context = context;
        spotsDialog = new SpotsDialog(context);
    }

    public void sharetowhatsapp() {
        String shareBody = "Students Bazaar,India's highest rated students app. \nSource : Students Bazaar \nclick below link: \n studentsbazaar.in/app/";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.app_name)));
    }

    public void shareResults(String mark,String qus,String ans) {
        String shareBody = "Today I got "+mark+" point on Quiz\nQuestion : "+qus+"\nAnswer : "+ans+"\nStudents Bazaar,India's highest rated students app. \nSource : Students Bazaar \nclick below link: \n studentsbazaar.in/app/";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.app_name)));
    }

    public void sharevideourl(String videourl) {
        String shareBody = "Video Link : "+videourl+"\nStudents Bazaar,India's highest rated students app. \nSource : Students Bazaar \nclick below link: \n studentsbazaar.in/app/";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.app_name)));
    }

    public void chattowhatsapp(String number) {
        if (whatsappInstalledOrNot("com.whatsapp")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("whatsapp://send?text=" + "" + "&phone=91" + number));
            context.startActivity(browserIntent);
        } else {
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    public void postfacebook() {
        if (whatsappInstalledOrNot("com.facebook.katana")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("dgas"));
            context.startActivity(browserIntent);
        } else {
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public void shareImage(String url) {
        Picasso.with(context).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                spotsDialog.dismiss();
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/StudentBazaar");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), BitmapFactory.decodeFile(fileUri), null, null));
                // use intent to share image
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                String shareBody = "Students Bazaar,India's highest rated students app. \nSource : Students Bazaar \nclick below link: \nstudentsbazaar.in/app/";
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(share, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                spotsDialog.show();
            }
        });
    }

    public void downloadpdf(String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }


}
