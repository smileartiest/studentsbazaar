package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.Dialog;
import android.content.Context;
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
import com.iceteck.silicompressorr.SiliCompressor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.Memes_Adapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.helper.FileUtil;
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
    String UID;
    Toolbar toolbar;
    String epost = "0";
    Dialog dialog;
    Uri imageUri;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mems);
        spotsDialog = new SpotsDialog(this);
        memeview = findViewById(R.id.memerecyler);
        swipeRefreshLayout = findViewById(R.id.memeswipe);
        new Controller(this);
        Controller.addupdateview(Controller.MEMEVIEW);
        UID = Controller.getUID();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        str = intent.getStringExtra("apitype");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (str==null){
                getSupportActionBar().setTitle("Memes");
            }
            else if (str.equals("uid")){
                getSupportActionBar().setTitle("My Memes");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (str==null){
            new Move_Show(Mems.this,HomeActivity.class);
        }
        else if (str.equals("uid")){
            new Move_Show(Mems.this,ProfileActivity.class);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_placement_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.item2);
        MenuItem search = menu.findItem(R.id.action_search);
        menu.findItem(R.id.profile).setVisible(false);
        search.setVisible(false);
        if (Controller.getprefer().equals(Controller.ADMIN) || Controller.getprefer().equals(Controller.MEMEACCEPT)) {
            shareItem.setVisible(true);
        } else {
            shareItem.setVisible(false);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.shareitem:
                try {
                    new Monitor(this).sharetowhatsapp();
                } catch (Exception e) {

                }

                return true;
            case R.id.item1:


                if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.ADMIN)) {
                    addJob();
                } else {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Mems.this);
                    builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                    builder.setTitle("Hey there ! Do Register!");
                    builder.setMessage("Then Enjoy the Meme Box.Fun Guarantee !");
                    builder.addButton("OKAY", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            SharedPreferences smeme = getSharedPreferences("meme", Context.MODE_PRIVATE);
                            SharedPreferences.Editor seditor = smeme.edit();
                            seditor.putString("source", "meme").apply();
                            new Move_Show(Mems.this, SignUp.class);

                        }
                    });

                    builder.addButton("NO", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }


                return true;
            case R.id.item2:

                new Move_Show(Mems.this, Pending_Post.class);

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
            }
        });
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (encoded == null) {
                    Move_Show.showToast("Please Select memes");
                } else {
                    spotsDialog.show();
                    // convertBitmapToString(profilePicture);
                    Call<String> call = ApiUtil.getServiceClass().addmemes(UID, "fine", encoded);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.body().equals("1")) {
                                spotsDialog.dismiss();
                                dialog.cancel();
                                alert();
                            } else {
                                Move_Show.showToast(response.body());                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
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
        Call<DownloadResponse> call = null;
        if (str==null){
            call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_MEME);
        }else if (str.equals("uid")){
            call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_USER_MEMES+"?uid=313");
        }
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {

                Log.d("RESPONSE1", response.message().toString());

                if (response.isSuccessful()) {
                    swipeRefreshLayout.setRefreshing(false);

                    assert response.body() != null;
                    memes_details = response.body().getMemes_details();

                    Log.d("RESPONSE2", memes_details.toString());
                    spotsDialog.dismiss();
                    if (memes_details.size() == 0) {

                    } else {
                        mAdapter = new Memes_Adapter(Mems.this, memes_details);
                        memeview.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                Log.d("RESPONSE3", "err" + t.getMessage());
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
                        postmeme.setImageBitmap(profilePicture);
                        File actualImage = FileUtil.from(this, data.getData());
                        Bitmap compressedImgFile = SiliCompressor.with(this).getCompressBitmap(actualImage.getAbsolutePath());
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        compressedImgFile.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        Log.d("ENCODE", encoded);

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
