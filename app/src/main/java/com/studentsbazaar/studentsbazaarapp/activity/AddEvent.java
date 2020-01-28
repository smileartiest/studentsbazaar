package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentsbazaar.studentsbazaarapp.R;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

public class AddEvent extends AppCompatActivity {

    ImageView imagepost,addpost,addevent;
    ListView eventlist;
    TextView evntsts;
    FloatingActionButton next;
    Button addeventbtn;
    ArrayList<String> eventlistadapter = new ArrayList<>();
    SQLiteDatabase sq;
    Cursor c;

    String profileimg,elist,epost;
    Bitmap profilePicture;

    Dialog d;
    private static int RESULT_LOAD_IMAGE = 1;

    EditText event,description,amount;
    int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        imagepost = findViewById(R.id.aevent_post_image);
        addpost = findViewById(R.id.aevent_post_icon);
        addevent = findViewById(R.id.aevent_eventadd_icon);
        eventlist = findViewById(R.id.aevent_list);
        next = findViewById(R.id.aevent_complete);
        evntsts = findViewById(R.id.aevent_sts);

        evntsts.setVisibility(View.INVISIBLE);

        //sq = openOrCreateDatabase("event",MODE_PRIVATE , null);
        //sq.execSQL("create table edata(id int NOT NULL AUTO_INCREMENT,events varchar(100), PRIMARY KEY (id))");

        eventlistview();
    }

    @Override
    protected void onResume() {
        super.onResume();

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d = new Dialog(AddEvent.this);
                d.setContentView(R.layout.add_even_dialogbox);
                event = d.findViewById(R.id.add_eventname);
                description = d.findViewById(R.id.add_eventdesc);
                amount = d.findViewById(R.id.add_eventamount);
                addeventbtn = d.findViewById(R.id.add_eventaddbtn);

                addeventbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(event.getText().length()!=0)
                        {
                            if(description.getText().length()!=0)
                            {
                                if(amount.getText().length()!=0)
                                {
                                    i++;
                                    String content = "Event "+i+" : "+event.getText().toString()+"/"+description.getText().toString()+"/"+amount.getText().toString();
                                    eventlistadapter.add(content);
                                    eventlistview();

                                }
                                else
                                {
                                    i++;
                                    String content = "Event "+i+" : "+event.getText().toString()+"/"+description.getText().toString()+"/ Null";
                                    eventlistadapter.add(content);
                                    eventlistview();

                                }
                            }
                            else
                            {
                                description.setError("Please fill somthing");
                            }
                        }
                        else
                        {
                            event.setError("Please fill");
                        }
                    }
                });

                d.show();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                convertBitmapToString(profilePicture);
                epost = profileimg;
                elist = eventlistadapter.toString().replaceAll("\\[", "").replaceAll("\\]","");;

                SharedPreferences sf = getSharedPreferences("event" ,MODE_PRIVATE);
                SharedPreferences.Editor ed = sf.edit();
                ed.putString("epost" ,epost);
                Log.d("IMG_NAME",epost+"");
                ed.putString("elist" , elist);
                Log.d("IMG_NAME",elist+"");
                ed.apply();

                startActivity(new Intent(getApplicationContext() , AddEvent2.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT_LOAD_IMAGE)
        {
            try {

                Uri imageUri = data.getData();
                InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                profilePicture = BitmapFactory.decodeStream(imageStream);
                imagepost.setImageBitmap(profilePicture);
                Bitmap compressedImgFile = new Compressor(this).compressToBitmap(new File(imageUri.getPath()));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                compressedImgFile.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d("IMG_NAME", epost + "");

                SharedPreferences sf = getSharedPreferences("event", MODE_PRIVATE);
                SharedPreferences.Editor ed = sf.edit();
                ed.putString("epost", encoded);
                ed.apply();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void convertBitmapToString(Bitmap profilePicture) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        profilePicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] array = byteArrayOutputStream.toByteArray();
        profileimg = Base64.encodeToString(array, Base64.DEFAULT);
    }

    public void eventlistview()
    {
        if(eventlistadapter.size()==0)
        {
            evntsts.setVisibility(View.VISIBLE);
        }
        else
        {
            d.dismiss();
            evntsts.setVisibility(View.INVISIBLE);
            ArrayAdapter<String> ad = new ArrayAdapter<>(getApplicationContext() , R.layout.event_row , eventlistadapter);
            eventlist.setAdapter(ad);
        }
    }

}
