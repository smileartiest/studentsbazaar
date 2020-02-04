package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.Memes_Adapter;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Memes_Details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mems extends AppCompatActivity {
    List<Memes_Details> memes_details = null;
    SpotsDialog spotsDialog;
    RecyclerView memeview;
    Memes_Adapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Bitmap profilePicture;
    private static int RESULT_LOAD_IMAGE = 1;
    String profileimg, encoded;
    ImageView postmeme;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String UID;
    String epost = "0";
    Dialog dialog;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mems);
        spotsDialog = new SpotsDialog(this);
        memeview = findViewById(R.id.memerecyler);
        swipeRefreshLayout = findViewById(R.id.memeswipe);
        sharedPreferences = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        UID = sharedPreferences.getString("UID", null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.memetool);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Memes");

        }
        loadData();
        memeview.setHasFixedSize(true);
        memeview.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_placement_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.item2);

        if (sharedPreferences.getString("log", "").equals("admin")) {
            shareItem.setVisible(true);
        }else{
            shareItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                addJob();

                return true;
            case R.id.item2:

                Intent intent = new Intent(Mems.this,Pending_Post.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void addJob() {

        dialog = new Dialog(Mems.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.addpost);
        TextView post = dialog.findViewById(R.id.post);
        postmeme = dialog.findViewById(R.id.addnewsiv);
        Button postbtn = dialog.findViewById(R.id.addnewsbtn);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
//
            }
        });
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spotsDialog.show();
                convertBitmapToString(profilePicture);
                epost = profileimg;
                Log.d("RESPONSE3", epost);
                Call<String> call = ApiUtil.getServiceClass().addmemes(UID, "fine", epost);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("1")) {
                            spotsDialog.dismiss();
                            dialog.cancel();
                            alert();
                        } else {
                            Toast.makeText(Mems.this, response.body(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    void alert() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Mems.this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle("Hey ");
        builder.setMessage("Thanks for your Post....");
        builder.addButton("Done", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        loadData();

                    }
                });
        builder.show();
    }

    private void loadData() {
        spotsDialog.show();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_MEMES);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, retrofit2.Response<DownloadResponse> response) {

                Log.d("RESPONSE1", response.message().toString());

                if (response.isSuccessful()) {
                    swipeRefreshLayout.setRefreshing(false);

                    assert response.body() != null;
                    memes_details = response.body().getMemes_details();

                    Log.d("RESPONSE2", memes_details.toString());
                    spotsDialog.dismiss();
                    if (memes_details.size() == 0) {
//                        layout.setVisibility(View.VISIBLE);
//                        viewPager2.setVisibility(View.INVISIBLE);
                    } else {
//                        layout.setVisibility(View.INVISIBLE);
//                        viewPager2.setVisibility(View.VISIBLE);
                        mAdapter = new Memes_Adapter(Mems.this, memes_details);
                        memeview.setAdapter(mAdapter);
                        // mAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                //showErrorMessage();

                Log.d("RESPONSE3", "err" + t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE) {

                try {
                    if (data !=null){

                    if (data.getData()!=null) {
                        imageUri = data.getData();
                        InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                        profilePicture = BitmapFactory.decodeStream(imageStream);
                        postmeme.setImageBitmap(profilePicture);
                        Bitmap compressedImgFile = new Compressor(this).compressToBitmap(new File(imageUri.getPath()));
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        compressedImgFile.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        epost = encoded;

                    }}
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


}
