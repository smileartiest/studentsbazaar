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
import android.widget.Toast;

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

    ImageView imagepost, addpost, addevent, cancelbtn;
    ListView eventlist;
    TextView evntsts;
    FloatingActionButton next;
    Button addeventbtn;

    ArrayList<String> eventlistadapter = new ArrayList<>();
    SQLiteDatabase sq;
    Cursor c;

    String profileimg, elist, epost="0";
    Bitmap profilePicture;

    Dialog d;
    private static int RESULT_LOAD_IMAGE = 1;

    EditText event, description;
    int i = 0;
    SharedPreferences sf;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        Log.d("sizeofarray", epost);

        imagepost = findViewById(R.id.aevent_post_image);
        addpost = findViewById(R.id.aevent_post_icon);
        addevent = findViewById(R.id.aevent_eventadd_icon);
        eventlist = findViewById(R.id.aevent_list);
        next = findViewById(R.id.aevent_complete);
        evntsts = findViewById(R.id.aevent_sts);
        cancelbtn = findViewById(R.id.id_event_cancel);
        evntsts.setVisibility(View.INVISIBLE);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventlistadapter.clear();
                eventlistview();
                eventlist.setVisibility(View.INVISIBLE);
            }
        });
        eventlistview();
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

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d = new Dialog(AddEvent.this);
                d.setContentView(R.layout.add_even_dialogbox);
                event = d.findViewById(R.id.add_eventname);
                description = d.findViewById(R.id.add_eventdesc);

                addeventbtn = d.findViewById(R.id.add_eventaddbtn);

                addeventbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (event.getText().toString().isEmpty() && description.getText().toString().isEmpty()) {
                            Toast.makeText(AddEvent.this, "Enter all the fields...", Toast.LENGTH_SHORT).show();
                        } else if (event.getText().toString().isEmpty()) {
                            event.setError("Please fill");
                        } else if (description.getText().toString().isEmpty()) {
                            description.setError("Please fill somthing");
                        } else {

                        }


                        i++;
                        String content = "Event " + i + " : " + event.getText().toString() + "/" + description.getText().toString();
                        eventlistadapter.add(content);
                        eventlistview();
                        eventlist.setVisibility(View.VISIBLE);
                        i = 0;


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
                elist = eventlistadapter.toString().replaceAll("\\[", "").replaceAll("\\]", "");
                sf = getSharedPreferences("event", MODE_PRIVATE);
                ed = sf.edit();
                ed.putString("epost", epost);
                Log.d("IMG_NAME", epost + "");
                ed.putString("elist", elist);
                Log.d("IMG_NAME", elist + "");
                ed.apply();
                if (epost.isEmpty() && elist.isEmpty()) {
                    Toast.makeText(AddEvent.this, "Please add Poster Image and Event Details...", Toast.LENGTH_SHORT).show();
                } else {

                    startActivity(new Intent(getApplicationContext(), AddEvent3.class));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE) {
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

    public void eventlistview() {
        if (eventlistadapter.size() == 0) {
            evntsts.setVisibility(View.VISIBLE);

        } else {
            d.dismiss();
            evntsts.setVisibility(View.INVISIBLE);
            ArrayAdapter<String> ad = new ArrayAdapter<>(getApplicationContext(), R.layout.event_row, eventlistadapter);
            eventlist.setAdapter(ad);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (epost.equals("0")){
            Intent intent=new Intent(AddEvent.this,AddEvent2.class);
            startActivity(intent);
        }


    }
}
