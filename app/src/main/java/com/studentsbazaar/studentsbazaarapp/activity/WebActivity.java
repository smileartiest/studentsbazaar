package com.studentsbazaar.studentsbazaarapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.studentsbazaar.studentsbazaarapp.R;

public class WebActivity extends AppCompatActivity {
    private WebView wv1;
    Bundle bundle;
    private String url = null, data = null, title = null;
    // private SpotsDialog progressDialog = null;
    // private ProgressDialog progressDialog = null;
    //PublisherAdView btmAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  Intent intent = getIntent();
        bundle = intent.getExtras();*/

        url = getIntent().getExtras() != null ? getIntent().getExtras().getString("url") : null;
        data = getIntent().getExtras() != null ? getIntent().getExtras().getString("data") : null;
        title = getIntent().getExtras() != null ? getIntent().getExtras().getString("title") : null;

        if (url.isEmpty()) {
            url = "https://www.fb.com";
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

        //  url = bundle.getString("url");
        Log.d("WEB_URL", url);

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setLayerType(View.LAYER_TYPE_NONE, null);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.getSettings().setUseWideViewPort(true);
        wv1.getSettings().setBuiltInZoomControls(true);
        wv1.getSettings().setDisplayZoomControls(false);

        wv1.setWebChromeClient(new MyWebChromeClient(this));
        wv1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //getActivity().startService(new Intent(getActivity().getApplicationContext(), InterstitialService.class));
              /*  if(url.contains(".docx")||url.contains(".pdf")){
                    view.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
                }*/
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // progressDialog = new SpotsDialog(this, R.style.Custom).show();
                //  dismissProgressDialog();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Error:" + description, Toast.LENGTH_SHORT).show();

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

