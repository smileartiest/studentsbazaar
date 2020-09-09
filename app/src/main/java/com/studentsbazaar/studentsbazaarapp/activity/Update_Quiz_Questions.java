package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.iceteck.silicompressorr.SiliCompressor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.helper.FileUtil;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_Quiz_Questions extends AppCompatActivity {
    ImageView qspic,anspic;
    Spinner qstype;
    TextInputLayout ans;
    Button postquiz;
    SpotsDialog spotsDialog;
    String qspicstring = null,anspicstring = null,qstypestring = null;
    String[] qstypelist = {"Choose Qstion Type" , "Choice","FillBlanks"};
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_IMAGE1 = 2;
    Bitmap profilePicture;
    String encoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_update_questions);

       Toolbar toolbar = (Toolbar) findViewById(R.id.uitbupdatequiz);

        // setnotification();
        // displaystatus();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Post Quiz");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.SRC_ATOP);
        }   // use a l
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        qspic = findViewById(R.id.uiedaddquizqspic);
        anspic = findViewById(R.id.uiedaddquizqsanspic);
        ans = findViewById(R.id.uiedaddquizqsanser);
        postquiz = (Button) findViewById(R.id.uibrnaddquiz);
        qstype = findViewById(R.id.uiedaddquizqstype);
        spotsDialog = new SpotsDialog(Update_Quiz_Questions.this);

        ArrayAdapter<String> ad = new ArrayAdapter<>(Update_Quiz_Questions.this , R.layout.spinlist , qstypelist);
        qstype.setAdapter(ad);

    }

    @Override
    protected void onResume() {
        super.onResume();

        qspic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        anspic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE1);
            }
        });

        qstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    qstypestring = "0";
                }else if(position==2){
                    qstypestring = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        postquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sf = getSharedPreferences("quiz", MODE_PRIVATE);
                qspicstring = sf.getString("qpost","");
                anspicstring = sf.getString("apost","");
                String ans1 = ans.getEditText().getText().toString();

                if(qspicstring!= null){
                    if(anspicstring!= null){
                        if(ans1.length()!=0){
                            if(qstypestring != null) {
                                spotsDialog.show();
                                Call call = ApiUtil.getServiceClass().addquizquestions(qstypestring, qspicstring, anspicstring, ans1);
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) {
                                        if (response.body().equals("1")) {
                                            spotsDialog.dismiss();
                                            Move_Show.showToast("Question Updated");
                                            finish();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                    }
                                });
                            }else { Move_Show.showToast("Please choose type");}
                        }else { ans.getEditText().setError("Please fill answer"); }
                    }else { Move_Show.showToast("Please Choose Answer Picture");}
                }else { Move_Show.showToast("Please Choose Question Picture"); }
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
                        InputStream imageStream = Update_Quiz_Questions.this.getContentResolver().openInputStream(imageUri);
                        profilePicture = BitmapFactory.decodeStream(imageStream);
                        qspic.setImageBitmap(profilePicture);
                        File actualImage = FileUtil.from(this, data.getData());
                        Bitmap compressedImgFile = SiliCompressor.with(this).getCompressBitmap(actualImage.getAbsolutePath());
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        compressedImgFile.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        SharedPreferences sf = getSharedPreferences("quiz", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sf.edit();
                        ed.putString("qpost", encoded);
                        ed.apply();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(requestCode == RESULT_LOAD_IMAGE1){
            try {
                if (data != null) {
                    if (data.getData() != null) {
                        Uri imageUri = data.getData();
                        InputStream imageStream = Update_Quiz_Questions.this.getContentResolver().openInputStream(imageUri);
                        profilePicture = BitmapFactory.decodeStream(imageStream);
                        anspic.setImageBitmap(profilePicture);
                        File actualImage = FileUtil.from(this, data.getData());
                        Bitmap compressedImgFile = SiliCompressor.with(this).getCompressBitmap(actualImage.getAbsolutePath());
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        compressedImgFile.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        SharedPreferences sf = getSharedPreferences("quiz", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sf.edit();
                        ed.putString("apost", encoded);
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
        new Move_Show(Update_Quiz_Questions.this,Quiz_Events.class);
    }
}
