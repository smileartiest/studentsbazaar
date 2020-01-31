package com.studentsbazaar.studentsbazaarapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_Details extends AppCompatActivity {
    TextView title, category, sdate, edate, organizer, city, state, Discription, eventdetails, department, guest, pronites, theme, accomadtation, lastdate, entryfees, howtoreach, cpnam1, cpno1, cpname2, cpno2, eventweb, collegeweb;
    Button submit, edit, register_now;
    ImageView head_poster;
    String stitle, sategory, ssdate, sedate, sorganizer, scity, sstate, sDiscription, seventdetails, sdepartment, sguest, spronites, stheme, saccomadtation, slastdate, sentryfees, showtoreach, scpnam1, scpno1, scpname2, scpno2;
    String posterurl, coid, webevent, webcoll,weburl;
    SharedPreferences spUserDetails;
    SpotsDialog spotsDialog;
    Typeface tf_regular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view__details);
        tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), "caviar.ttf");
        submit = (Button) findViewById(R.id.head_btSubmit);
        edit = (Button) findViewById(R.id.head_Update);
        title = (TextView) findViewById(R.id.head_title);
        category = (TextView) findViewById(R.id.head_category);
        sdate = (TextView) findViewById(R.id.head_start_date);
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
        cpnam1 = (TextView) findViewById(R.id.head_contact_name1);
        cpno1 = (TextView) findViewById(R.id.head_contact_phone1);
        cpname2 = (TextView) findViewById(R.id.head_contact_name2);
        cpno2 = (TextView) findViewById(R.id.head_contact_phone2);
        head_poster = (ImageView) findViewById(R.id.head_slider);
        eventweb = (TextView) findViewById(R.id.head_event_web);
        collegeweb = (TextView) findViewById(R.id.head_college_web);
        register_now = (Button) findViewById(R.id.register_now);

        submit.setTypeface(tf_regular);
        edit.setTypeface(tf_regular);
        title.setTypeface(tf_regular);
        category.setTypeface(tf_regular);
        sdate.setTypeface(tf_regular);
        edate.setTypeface(tf_regular);
        organizer.setTypeface(tf_regular);
        city.setTypeface(tf_regular);
        state.setTypeface(tf_regular);
        Discription.setTypeface(tf_regular);
        eventdetails.setTypeface(tf_regular);
        department.setTypeface(tf_regular);
        guest.setTypeface(tf_regular);
        pronites.setTypeface(tf_regular);
        theme.setTypeface(tf_regular);
        accomadtation.setTypeface(tf_regular);
        lastdate.setTypeface(tf_regular);
        entryfees.setTypeface(tf_regular);
        howtoreach.setTypeface(tf_regular);
        cpnam1.setTypeface(tf_regular);
        cpno1.setTypeface(tf_regular);
        cpname2.setTypeface(tf_regular);
        cpno2.setTypeface(tf_regular);
        eventweb.setTypeface(tf_regular);
        collegeweb.setTypeface(tf_regular);
        register_now.setTypeface(tf_regular);

        spotsDialog = new SpotsDialog(this);
        spUserDetails = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
       /* if (spUserDetails.getString("PREFER", null).equals("PREF")) {
            submit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        } else {
            submit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        }*/
        getviewDetails();


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

                if (webevent.contains("http://") || webevent.contains("https://")){

                    weburl =webevent;
                }else{
                    weburl ="http://"+webevent;
                }
                Bundle bundle = new Bundle();
                bundle.putString("url",weburl);
                bundle.putString("data","reg url");
                bundle.putString("title","reg title");
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
        sategory = sharedPreferences.getString("cat", null);
        ssdate = sharedPreferences.getString("sdate", null);
        sedate = sharedPreferences.getString("edate", null);
        sorganizer = sharedPreferences.getString("organiser", null);
        scity = sharedPreferences.getString("city", null);
        sstate = sharedPreferences.getString("state", null);
        sDiscription = sharedPreferences.getString("dis", null);
        seventdetails = sharedPreferences.getString("Eventdetails", null);
        sdepartment = sharedPreferences.getString("dept", null);
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
        if (spUserDetails.getString("PREFER", null).equals("MORE")) {
            submit.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            register_now.setVisibility(View.VISIBLE);
        }else if(spUserDetails.getString("PREFER", null).equals("PREF")){
            submit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            register_now.setVisibility(View.GONE);
        }
        Glide.with(View_Details.this)
                .load(posterurl)
                .placeholder(R.drawable.load)
                .error(R.drawable.load)
                .into(head_poster);
        title.setText(stitle);
        category.setText(sategory);
        sdate.setText(ssdate);
        edate.setText(sedate);
        organizer.setText(sorganizer);
        city.setText(scity);
        state.setText(sstate);
        Discription.setText(sDiscription);
        eventdetails.setText(seventdetails);
        department.setText(sdepartment);
        guest.setText(sguest);
        pronites.setText(spronites);
        theme.setText(stheme);
        accomadtation.setText(saccomadtation);
        lastdate.setText(slastdate);
        entryfees.setText(sentryfees);
        howtoreach.setText(showtoreach);
        cpnam1.setText(scpnam1);
        cpno1.setText(scpno1);
        cpname2.setText(scpname2);
        cpno2.setText(scpno2);
        eventweb.setText(webevent);
        collegeweb.setText(webcoll);

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
                Intent in = new Intent(View_Details.this,Pending_Events.class);
                startActivity(in);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}
