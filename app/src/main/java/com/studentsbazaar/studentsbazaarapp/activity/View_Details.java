package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import dmax.dialog.SpotsDialog;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_Details extends AppCompatActivity {
    TextView instagram,title, category, sdate, edate, organizer, city, state, Discription, eventdetails, department, guest, pronites, theme, accomadtation, lastdate, entryfees, howtoreach, cpnam1, cpno1, cpname2, cpno2, eventweb, collegeweb;
    Button submit, edit, register_now;
    ImageView head_poster, w1, w2, c1, c2;
    GifImageView gifImageView;
    CardView cardinsta,cardCollegeweb, cardEventweb, cardTheme, cardPronits, cardAco, cardGuest, cardDept, cardEvent;
    String stitle, sategory, ssdate, sedate, sorganizer, scity, sstate, sDiscription, seventdetails, sdepartment, sguest, spronites, stheme, saccomadtation, slastdate, sentryfees, showtoreach, scpnam1, scpno1, scpname2, scpno2;
    String posterurl, coid, webevent, webcoll, weburl,insta;
    SpotsDialog spotsDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view__details);
        submit = (Button) findViewById(R.id.head_btSubmit);
        edit = (Button) findViewById(R.id.head_Update);
        title = (TextView) findViewById(R.id.head_title);
        category = (TextView) findViewById(R.id.head_category);
        cardAco = (CardView) findViewById(R.id.id_card_acco);
        cardCollegeweb = (CardView) findViewById(R.id.id_college_website);
        cardinsta=(CardView)findViewById(R.id.id_event_instagram);
        cardEventweb = (CardView) findViewById(R.id.id_event_website);
        cardEvent = (CardView) findViewById(R.id.id_card_event);
        cardTheme = (CardView) findViewById(R.id.id_card_theme);
        cardPronits = (CardView) findViewById(R.id.id_card_pronites);
        cardGuest = (CardView) findViewById(R.id.id_card_guest);
        cardDept = (CardView) findViewById(R.id.id_card_dept);
        sdate = (TextView) findViewById(R.id.head_start_date);
        gifImageView=(GifImageView)findViewById(R.id.gifview);
        edate = (TextView) findViewById(R.id.head_end_date);
        organizer = (TextView) findViewById(R.id.head_organiser);
        city = (TextView) findViewById(R.id.head_city);
        state = (TextView) findViewById(R.id.head_state);
        Discription = (TextView) findViewById(R.id.head_desc_content);
        eventdetails = (TextView) findViewById(R.id.head_event_det_content);
        department = (TextView) findViewById(R.id.head_dept_content);
        guest = (TextView) findViewById(R.id.head_guest_content);
        pronites = (TextView) findViewById(R.id.head_pronites_content);
        theme = (TextView) findViewById(R.id.head_theme_content);
        accomadtation = (TextView) findViewById(R.id.head_acco_content);
        lastdate = (TextView) findViewById(R.id.head_lastDate_content);
        entryfees = (TextView) findViewById(R.id.head_regFee_content);
        howtoreach = (TextView) findViewById(R.id.head_reach_content);
        cpnam1 = (TextView) findViewById(R.id.uitvname1);
        cpno1 = (TextView) findViewById(R.id.uitvphone1);
        cpname2 = (TextView) findViewById(R.id.uitvname2);
        cpno2 = (TextView) findViewById(R.id.uitvphone2);
        w1 = (ImageView) findViewById(R.id.uiivwhatsapp1);
        w2 = (ImageView) findViewById(R.id.uiivwhatsapp2);
        c1 = (ImageView) findViewById(R.id.uiivcall1);
        c2 = (ImageView) findViewById(R.id.uiivcall2);
        head_poster = (ImageView) findViewById(R.id.head_slider);
        eventweb = (TextView) findViewById(R.id.head_event_web);
        collegeweb = (TextView) findViewById(R.id.head_college_web);
        instagram = (TextView)findViewById(R.id.head_event_instagram);
        register_now = (Button) findViewById(R.id.register_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.viewtoolb);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("View Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        spotsDialog = new SpotsDialog(this);
        new Controller(this);
       /* if (spUserDetails.getString("PREFER", null).equals("PREF")) {
            submit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        } else {
            submit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        }*/
        getviewDetails();

        w1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Monitor(View_Details.this).chattowhatsapp(cpno1.getText().toString().trim());
            }
        });
        w2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Monitor(View_Details.this).chattowhatsapp(cpno2.getText().toString().trim());
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(cpno1.getText().toString());
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(cpno2.getText().toString());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedEvents(coid, "1");
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updatedEvents(coid, "2");

            }
        });

        cpno1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall(cpno1.getText().toString());
            }
        });

        cpno2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall(cpno2.getText().toString());
            }
        });

        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (webevent.contains("http://") || webevent.contains("https://")) {

                    weburl = webevent;
                } else {
                    weburl = "http://" + webevent;
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", weburl);
                bundle.putString("data", "REG FROM");
                bundle.putString("title", "reg title");
                Intent in = new Intent(View_Details.this, WebActivity.class);
                in.putExtras(bundle);
                startActivity(in);

            }
        });
    }

    private void makeCall(String toString) {

        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + toString));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    Activity#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for Activity#requestPermissions for more details.
//                return;
//            }
//        }
        startActivity(i);
    }

    void getviewDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("view_details", Context.MODE_PRIVATE);
        coid = sharedPreferences.getString("coid", null);
        posterurl = sharedPreferences.getString("post", null);
        stitle = sharedPreferences.getString("title", null);
        sategory = sharedPreferences.getString("cat", null).replaceAll("\\[", "").replaceAll("\\]","");
        ssdate = sharedPreferences.getString("sdate", null);
        sedate = sharedPreferences.getString("edate", null);
        sorganizer = sharedPreferences.getString("organiser", null);
        scity = sharedPreferences.getString("city", null);
        sstate = sharedPreferences.getString("state", null);
        sDiscription = sharedPreferences.getString("dis", null);
        seventdetails = sharedPreferences.getString("Eventdetails", null);
        sdepartment = sharedPreferences.getString("dept", null).replaceAll("\\[", "").replaceAll("\\]","");
        sguest = sharedPreferences.getString("guest", null);
        spronites = sharedPreferences.getString("pronites", null);
        stheme = sharedPreferences.getString("etheme", null);
        saccomadtation = sharedPreferences.getString("accom", null);
        slastdate = sharedPreferences.getString("lastdate", null);
        sentryfees = sharedPreferences.getString("fees", null);
        showtoreach = sharedPreferences.getString("htr", null);
        scpnam1 = sharedPreferences.getString("cpname1", null);
        scpno1 = sharedPreferences.getString("cpno1", null);
        scpname2 = sharedPreferences.getString("cpname2", null);
        scpno2 = sharedPreferences.getString("cpno2", null);
        webevent = sharedPreferences.getString("webevent", null);
        webcoll = sharedPreferences.getString("webcoll", null);
        insta =sharedPreferences.getString("insta",null);
        if (Controller.getdesignprefer().equals(Controller.MORE) ) {
            submit.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            register_now.setVisibility(View.VISIBLE);
        } else if (Controller.getdesignprefer().equals(Controller.PREFER)) {
            submit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            register_now.setVisibility(View.GONE);
        }
        Glide.with(View_Details.this)
                .load(posterurl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
               gifImageView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
               gifImageView.setVisibility(View.GONE);
                return false;
            }
        }).into(head_poster);
        title.setText(stitle);
        category.setText(sategory);
        sdate.setText(ssdate);
        edate.setText(sedate);
        organizer.setText(sorganizer);
        city.setText(scity);
        state.setText(sstate);
        Discription.setText(sDiscription);
        instagram.setText(insta);
        if (seventdetails.length() == 0) {
            cardEvent.setVisibility(View.GONE);
        } else {
            String output = String.valueOf(seventdetails).replace("/*NonTechnical Events*/", "<font color=#000000><b><br>NonTechnical Events<br></b></font>").replace("/*Technical Events*/", "<font color=#000000><b>Technical Events<br></b></font>").replace("/*Workshop Events*/", "<font color=#000000><b><br>Workshop Events<br></b></font>").replace("/*Online Events*/", "<font color=#000000><b><br>Online Events<br></b></font>");
            eventdetails.setText(Html.fromHtml(output));
        }
            department.setText(sdepartment);


        if (sguest.length() != 0) {
            guest.setText(sguest);
        } else {
            cardGuest.setVisibility(View.GONE);
        }

       if (spronites == null || spronites.equalsIgnoreCase("No")){
           cardPronits.setVisibility(View.GONE);
       }
       if (stheme==null || stheme.equalsIgnoreCase("No")){
           cardTheme.setVisibility(View.GONE);
       }
       if (saccomadtation==null || saccomadtation.equalsIgnoreCase("No")){
           cardAco.setVisibility(View.GONE);
       }
       if (webevent.length()<=0){
           cardEventweb.setVisibility(View.GONE);
       }
       if (webcoll.length()<=0){
           cardCollegeweb.setVisibility(View.GONE);
       }
       if (insta.length()<=0){
           cardinsta.setVisibility(View.GONE);
       }

        // pronites.setText(spronites);

        if (stheme.length() != 0) {
            theme.setText(stheme);
        } else {
            cardTheme.setVisibility(View.GONE);
        }
        // theme.setText(stheme);
        if (saccomadtation.length() != 0) {
            accomadtation.setText(saccomadtation);
        } else {
            cardAco.setVisibility(View.GONE);
        }

        //  accomadtation.setText(saccomadtation);
        lastdate.setText(slastdate);
        entryfees.setText(sentryfees);
        howtoreach.setText(showtoreach);
        cpnam1.setText(scpnam1);
        cpno1.setText(scpno1);
        cpname2.setText(scpname2);
        cpno2.setText(scpno2);
        eventweb.setText(webevent);
        collegeweb.setText(webcoll);

        cardCollegeweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goWebActivity(webcoll);
            }
        });

        cardEventweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goWebActivity(webevent);
            }
        });

    }

    private void goWebActivity(String webcoll) {
        Bundle b = new Bundle();
        b.putString("url", webcoll);
        b.putString("data", "college");
        b.putString("title", "college");
        Intent i = new Intent(View_Details.this, WebActivity.class);
        i.putExtras(b);
        startActivity(i);

    }

    void updatedEvents(String coid, String status) {
        spotsDialog.show();
        Call<String> call = ApiUtil.getServiceClass().updateEventStatus(ApiUtil.UPDATE_STATUS + "?coid=" + coid + "&s=" + status);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("Response", response.body().toString());
                spotsDialog.dismiss();
                Toast.makeText(View_Details.this, "Updated", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(View_Details.this, Pending_Events.class);
                startActivity(in);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Controller.getprefer().equals(Controller.ADMIN)) {
            Intent g = getIntent();
            Bundle b = g.getExtras();
            assert b != null;
            if (b.getString("view").equals("pending")) {
                Intent i = new Intent(View_Details.this, Pending_Events.class);
                startActivity(i);
                finish();
            } else {
                finish();
            }

        } else {
            finish();
        }
    }



}
