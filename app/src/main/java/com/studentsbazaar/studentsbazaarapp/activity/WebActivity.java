package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.R;

import dmax.dialog.SpotsDialog;

public class WebActivity extends AppCompatActivity {

    private WebView wv1;
    Bundle bundle;
    private String url = null, data = null, title = null;
    // private SpotsDialog progressDialog = null;
    // private ProgressDialog progressDialog = null;
    //PublisherAdView btmAd;
    SpotsDialog spotsDialog;
    LinearLayout layoutweb;
    Button reload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spotsDialog = new SpotsDialog(this);
      /*  Intent intent = getIntent();
        bundle = intent.getExtras();*/

        url = getIntent().getExtras() != null ? getIntent().getExtras().getString("url") : null;
        data = getIntent().getExtras() != null ? getIntent().getExtras().getString("data") : null;
        title = getIntent().getExtras() != null ? getIntent().getExtras().getString("title") : null;

        if (url.isEmpty()) {
            url = "https://www.studentsbazaar.com";
        }


        // try {
        setContentView(R.layout.activity_web);

        //  RojgarApplication.trackScreenName = "WebActivity";

        wv1 = findViewById(R.id.idWebview);


        //Toolbar toolbar = findViewById(R.id.toolbar);
        // btmAd = findViewById(R.id.adBtmView);
      /*  if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("ROJGAR LIVE");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }*/
        //  progressDialog = new SpotsDialog(this, R.style.Custom);

           /* progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);*/

        // showProgressDialog();

      /*  PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        PublisherAdRequest adRequest = builder.build();
        btmAd.loadAd(adRequest);
*/

        //Toast.makeText(getApplicationContext(),bundle.getString("url"),Toast.LENGTH_SHORT).show();
        loadWeb();

/*        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });*/
     /*   }catch (Exception e){
            Log.e("WEB_EXCEPTION",e.getMessage());
            transferToNoPackageFoundActivity(url);
        }*/


    }

   /* private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new SpotsDialog(this, R.style.Custom);
        }
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }*/

    private void transferToNoPackageFoundActivity(String url) {

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadWeb() {
        spotsDialog.show();
        //  url = bundle.getString("url");
        Log.d("WEB_URL", url);
        wv1.setInitialScale(1);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setInitialScale(30 * 10);
        wv1.setPadding(0, 0, 0, 0);
        wv1.setWebChromeClient(new MyWebChromeClient(this));
        wv1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                wv1.setVisibility(View.VISIBLE);
                spotsDialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), String.valueOf(errorCode), Toast.LENGTH_SHORT).show();
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(WebActivity.this);
                builder.setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION);
                builder.setTitle("Hey there !");
                builder.setMessage("\nSorry !\n\nThere’s been a delay in our response  .\n" +
                        "Kindly Keep Checking the reload option given below.\nThank you !\n");
                builder.addButton("Reload", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loadWeb();
                                dialog.dismiss();
                            }
                        });
                builder.show();

                wv1.setVisibility(View.INVISIBLE);
            }
        });
        //wv1.loadUrl(url);
        if (url.contains(".docx") || url.contains(".pdf")) {
            wv1.getSettings().setJavaScriptEnabled(true);
            wv1.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
        } else {
            wv1.loadUrl(url);
        }
    }

    void getAlert() {


    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }

    }

/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ("notification".equals(data)) {
            Intent i = new Intent(WebActivity.this, HomeActivity.class);
            startActivity(i);
        } else {
            finish();
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // dismissProgressDialog();

    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.item1);

        if (title.isEmpty()) {
            shareItem.setVisible(false);
        }
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            shareIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void shareIntent() {
        //String url = getIntent().getExtras() != null ? getIntent().getExtras().getString("url") : null;
        title = getIntent().getExtras() != null ? getIntent().getExtras().getString("title") : null;
        assert title != null;
        if (title.isEmpty()) {
            title = "ROJGAR LIVE - GOVT JOBS";
        }
        String content = "Shared via RojgarLive App \n \n https://bit.ly/2Tap4ru";
        String finalContent = title + "\n\nClick Below Link for more details.\n\n" + url + "\n\n" + content;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, finalContent);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "RojgarLive Send to"));
    }


}

