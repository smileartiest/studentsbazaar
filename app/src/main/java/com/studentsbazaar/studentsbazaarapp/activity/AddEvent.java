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
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentsbazaar.studentsbazaarapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    String profileimg, elist, epost = "0";
    CardView cardtech, cardnontech, cardworkshop, cardonline;
    EditText edtech, ednontech, edworkshop, edonline;
    Button teching, technext, nonteching, nontechnext, workshoping, workshopnext, onlineing, onlinenext;
    StringBuilder stringBuilder;

    Dialog d;
    Bitmap profilePicture;
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
        stringBuilder = new StringBuilder();
        imagepost = findViewById(R.id.aevent_post_image);
        addpost = findViewById(R.id.aevent_post_icon);
        addevent = findViewById(R.id.aevent_eventadd_icon);
        eventlist = findViewById(R.id.aevent_list);
        next = findViewById(R.id.aevent_complete);
        evntsts = findViewById(R.id.aevent_sts);
        evntsts.setVisibility(View.INVISIBLE);
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
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setCancelable(false);
                d.setContentView(R.layout.add_even_dialogbox);
                cardtech = d.findViewById(R.id.eventtechnical);
                cardnontech = d.findViewById(R.id.eventnontechnical);
                cardworkshop = d.findViewById(R.id.eventworkshop);
                cardonline = d.findViewById(R.id.eventonline);
                edtech = d.findViewById(R.id.addeventteched);
                ednontech = d.findViewById(R.id.addnonteched);
                edworkshop = d.findViewById(R.id.addworksoped);
                edonline = d.findViewById(R.id.addonlineed);
                teching = d.findViewById(R.id.addeventing2);
                technext = d.findViewById(R.id.addeventnxt2);
                nonteching = d.findViewById(R.id.addnonteching2);
                nontechnext = d.findViewById(R.id.addeventnxt3);
                workshoping = d.findViewById(R.id.addworkshoping);
                workshopnext = d.findViewById(R.id.addeventnxt4);
                onlineing = d.findViewById(R.id.addonlineing);
                onlinenext = d.findViewById(R.id.addeventnxt5);
                cardtech.setVisibility(View.VISIBLE);

                technext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardtech.setVisibility(View.GONE);
                        cardnontech.setVisibility(View.VISIBLE);
                        if (edtech.getText().toString().isEmpty()) {
                            stringBuilder.append("");
                        } else {
                            stringBuilder.append("Technical Event's \n" + edtech.getText().toString() + "\n");

                        }
                    }
                });
                nontechnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardnontech.setVisibility(View.GONE);
                        cardworkshop.setVisibility(View.VISIBLE);
                        if (ednontech.getText().toString().isEmpty()) {
                            stringBuilder.append("");
                        } else {
                            stringBuilder.append("Non Technical Event's \n" + ednontech.getText().toString() + "\n");

                        }
                    }
                });
                workshopnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardworkshop.setVisibility(View.GONE);
                        cardonline.setVisibility(View.VISIBLE);
                        if (edworkshop.getText().toString().isEmpty()) {
                            stringBuilder.append("");
                        } else {
                            stringBuilder.append("Workshop Event's \n" + edworkshop.getText().toString() + "\n");

                        }
                    }
                });
                onlinenext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        if (edonline.getText().toString().isEmpty()) {
                            stringBuilder.append("");
                        } else {
                            stringBuilder.append("Online Event's \n" + edonline.getText().toString() + "\n");

                        }
                        evntsts.setText(stringBuilder.toString());

                    }
                });
                teching.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();

                    }
                });
                nonteching.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardnontech.setVisibility(View.GONE);
                        cardtech.setVisibility(View.VISIBLE);
                    }
                });
                workshoping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardworkshop.setVisibility(View.GONE);
                        cardnontech.setVisibility(View.VISIBLE);
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
                sf = getSharedPreferences("event", MODE_PRIVATE);
                ed = sf.edit();
                ed.putString("epost", epost);
                Log.d("IMG_NAME", epost + "");
                try {
                    ed.putString("elist", URLEncoder.encode(stringBuilder.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("IMG_NAME",   stringBuilder.toString());
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
                if (data != null) {
                    if (data.getData() != null) {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                        profilePicture = BitmapFactory.decodeStream(imageStream);
                        imagepost.setImageBitmap(profilePicture);
                        Bitmap compressedImgFile = new Compressor(this).compressToBitmap(new File(imageUri.getPath()));
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        compressedImgFile.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

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


}
