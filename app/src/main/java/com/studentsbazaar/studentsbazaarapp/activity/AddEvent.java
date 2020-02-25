package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    ImageView imagepost, addpost, addevent;
    FloatingActionButton next;
    String encoded;
    CardView cardtech, cardnontech, cardworkshop, cardonline;
    EditText edtech, ednontech, edworkshop, edonline;
    Button teching, technext, nonteching, nontechnext, workshoping, workshopnext, onlineing, onlinenext;
    StringBuilder stringBuilder,builderevent;
    TextView eventdetailstxt;
    Dialog d;
    Bitmap profilePicture;
    private static int RESULT_LOAD_IMAGE = 1;
    int i = 0;
    SharedPreferences sf;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        imagepost = findViewById(R.id.aevent_post_image);
        addpost = findViewById(R.id.aevent_post_icon);
        addevent = findViewById(R.id.aevent_eventadd_icon);
        next = findViewById(R.id.aevent_complete);
        eventdetailstxt=findViewById(R.id.uitveventdetails);

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
                edtech.requestFocus();
                technext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardtech.setVisibility(View.GONE);
                        cardnontech.setVisibility(View.VISIBLE);
                        ednontech.requestFocus();

                    }
                });
                nontechnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardnontech.setVisibility(View.GONE);
                        cardworkshop.setVisibility(View.VISIBLE);
                        edworkshop.requestFocus();

                    }
                });
                workshopnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardworkshop.setVisibility(View.GONE);
                        cardonline.setVisibility(View.VISIBLE);
                       edonline.requestFocus();

                    }
                });
                onlinenext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                        stringBuilder = new StringBuilder();
                        if (edtech.getText().toString().isEmpty()) {
                            stringBuilder.append("");
                        } else {
                            stringBuilder.append("/*Technical Events*/\n" + edtech.getText().toString() + "\n");

                        }
                        if (ednontech.getText().toString().isEmpty()) {
                            stringBuilder.append("");
                        } else {
                            stringBuilder.append("/*NonTechnical Events*/\n" + ednontech.getText().toString() + "\n");

                        }
                        if (edworkshop.getText().toString().isEmpty()) {
                            stringBuilder.append("");
                        } else {
                            stringBuilder.append("/*Workshop Events*/\n" + edworkshop.getText().toString() + "\n");

                        }
                        if (edonline.getText().toString().isEmpty()) {
                            stringBuilder.append("");
                        } else {
                            stringBuilder.append("/*Online Events*/\n" + edonline.getText().toString() + "\n");

                        }

                        String output = String.valueOf(stringBuilder).replace("/*NonTechnical Events*/", "<font color=#000000><b><br>NonTechnical Events<br></b></font>").replace("/*Technical Events*/", "<font color=#000000><b>Technical Events<br></b></font>").replace("/*Workshop Events*/", "<font color=#000000><b><br>Workshop Events<br></b></font>").replace("/*Online Events*/", "<font color=#000000><b><br>Online Events<br></b></font>");
                        eventdetailstxt.setText(Html.fromHtml(output));

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
                if (encoded == null) {
                    Move_Show.showToast("Please add Poster Image and Event Details...");
                } else if (stringBuilder == null) {
                    Move_Show.showToast("Please add Event Details");
                } else {
                    sf = getSharedPreferences("event", MODE_PRIVATE);
                    ed = sf.edit();
                    ed.putString("elist", stringBuilder.toString());
                    ed.apply();
                    Log.d("getstringbuilderdata", stringBuilder.toString());
                    new Move_Show(AddEvent.this, AddEvent3.class);
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
}
