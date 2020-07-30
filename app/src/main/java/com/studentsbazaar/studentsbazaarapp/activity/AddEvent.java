package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.iceteck.silicompressorr.SiliCompressor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.helper.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddEvent extends AppCompatActivity {

    ImageView imagepost, addpost;
    FloatingActionButton next;
    TextInputLayout technical, non_technical, workshop, online;
    String encoded;
    Bitmap profilePicture;
    private static int RESULT_LOAD_IMAGE = 1;
    int i = 0;
    Toolbar my_Toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        my_Toolbar = findViewById(R.id.aevent_toolbar);
        setSupportActionBar(my_Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagepost = findViewById(R.id.aevent_post_image);
        addpost = findViewById(R.id.aevent_post_icon);

        technical = findViewById(R.id.aevent_technical);
        non_technical = findViewById(R.id.aevent_non_technical);
        workshop = findViewById(R.id.aevent_workshop);
        online = findViewById(R.id.aevent_online);

        next = findViewById(R.id.aevent_next);

        my_Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        imagepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(encoded.length()!=0) {
                    String tech1 = technical.getEditText().getText().toString();
                    String non_tech1 = non_technical.getEditText().getText().toString();
                    String workshop1 = workshop.getEditText().getText().toString();
                    String online1 = online.getEditText().getText().toString();
                    String event_list = "1. Technical : " + tech1 + "\n2. Non-Technical : " + non_tech1 + "\n3. Workshop : " + workshop1 + "\n4. Online : " + online1;
                    openDialog(event_list);
                }else{
                    Snackbar.make(v , "Please choose poster picture" , Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    void openDialog(String str) {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(AddEvent.this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setCornerRadius(20);
        builder.setCancelable(false);
        builder.setTitle("Please Conform Details !");
        builder.setMessage(str);
        builder.addButton("continue", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SharedPreferences sf = getSharedPreferences("event", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sf.edit();
                        ed.putString("elist", str).apply();
                        new Move_Show(AddEvent.this, AddEvent3.class);
                    }
                });
        builder.addButton("Cancel", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE) {
            try {
                if (data != null) {
                    if (data.getData() != null) {
                        Uri imageUri = data.getData();
                        InputStream imageStream = AddEvent.this.getContentResolver().openInputStream(imageUri);
                        profilePicture = BitmapFactory.decodeStream(imageStream);
                        imagepost.setImageBitmap(profilePicture);
                        File actualImage = FileUtil.from(this, data.getData());
                        Bitmap compressedImgFile = SiliCompressor.with(this).getCompressBitmap(actualImage.getAbsolutePath());
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        compressedImgFile.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        SharedPreferences sf = getSharedPreferences("event", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sf.edit();
                        ed.putString("epost", encoded);
                        ed.apply();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
