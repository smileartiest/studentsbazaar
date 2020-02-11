package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Events extends AppCompatActivity {
    RelativeLayout layout, parentlayout;
    TextView txtwebevent, txtwebcoll, title, category, sdate, edate, organizer, city, state, Discription, eventdetails, department, guest, pronites, theme, accomadtation, lastdate, entryfees, howtoreach, cpnam1, cpno1, cpname2, cpno2;
    EditText edwebevent, edwedcoll, edtitle, edcategory, edsdate, ededate, edorganizer, edcity, edstate, edDiscription, edeventdetails, eddepartment, edguest, edpronites, edtheme, edaccomadtation, edlastdate, edentryfees, edhowtoreach, edcpnam1, edcpno1, edcpname2, edcpno2;
    Button edit_btn, done_btn;
    ImageView head_poster, head_posteredit;
    RelativeLayout view_window, edit_window;
    List<Project_details> drawerResponseList = null;
    String stitle, sategory, ssdate, sedate, sorganizer, scity, sstate, sDiscription, seventdetails, sdepartment, sguest, spronites, stheme, saccomadtation, slastdate, sentryfees, showtoreach, scpnam1, scpno1, scpname2, scpno2;
    String eweb, cweb, eventid;
    SpotsDialog spotsDialog;
    LinearLayout layoutempty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__events);
        new Controller(this);
        spotsDialog = new SpotsDialog(this);
        layout = (RelativeLayout) findViewById(R.id.edit_window);
        parentlayout = (RelativeLayout) findViewById(R.id.view_window);
        layoutempty = (LinearLayout) findViewById(R.id.empty6);
        done_btn = (Button) findViewById(R.id.ed_done);
        edit_btn = (Button) findViewById(R.id.edit_btSubmit);
        title = (TextView) findViewById(R.id.edit_title);
        category = (TextView) findViewById(R.id.edit_category);
        sdate = (TextView) findViewById(R.id.edit_start_date);
        edate = (TextView) findViewById(R.id.edit_end_date);
        organizer = (TextView) findViewById(R.id.edit_organiser);
        city = (TextView) findViewById(R.id.edit_city);
        state = (TextView) findViewById(R.id.edit_state);
        Discription = (TextView) findViewById(R.id.edit_desc_content);
        eventdetails = (TextView) findViewById(R.id.edit_event_det_content);
        department = (TextView) findViewById(R.id.edit_dept_content);
        guest = (TextView) findViewById(R.id.edit_guest_content);
        pronites = (TextView) findViewById(R.id.edit_pronites_content);
        theme = (TextView) findViewById(R.id.edit_theme_content);
        accomadtation = (TextView) findViewById(R.id.edit_acco_content);
        lastdate = (TextView) findViewById(R.id.edit_lastDate_content);
        entryfees = (TextView) findViewById(R.id.edit_regFee_content);
        howtoreach = (TextView) findViewById(R.id.edit_reach_content);
        cpnam1 = (TextView) findViewById(R.id.edit_contact_name1);
        cpno1 = (TextView) findViewById(R.id.edit_contact_phone1);
        cpname2 = (TextView) findViewById(R.id.edit_contact_name2);
        cpno2 = (TextView) findViewById(R.id.edit_contact_phone2);
        head_poster = (ImageView) findViewById(R.id.edit_slider);
        view_window = (RelativeLayout) findViewById(R.id.view_window);
        edit_window = (RelativeLayout) findViewById(R.id.edit_window);
        head_posteredit = (ImageView) findViewById(R.id.edit1_slider);
        txtwebevent = (TextView) findViewById(R.id.edit_event_web);
        txtwebcoll = (TextView) findViewById(R.id.edit_college_web);
        edwebevent = (EditText) findViewById(R.id.ed_event_web);
        edwedcoll = (EditText) findViewById(R.id.ed_college_web);
        edtitle = (EditText) findViewById(R.id.ed_title);
        edcategory = (EditText) findViewById(R.id.ed_cat);
        edsdate = (EditText) findViewById(R.id.ed_startdate);
        ededate = (EditText) findViewById(R.id.ed_enddate);
        edorganizer = (EditText) findViewById(R.id.ed_organiser);
        edcity = (EditText) findViewById(R.id.ed_city);
        edstate = (EditText) findViewById(R.id.ed_state);
        edDiscription = (EditText) findViewById(R.id.ed_dis);
        edeventdetails = (EditText) findViewById(R.id.ed_evntdetails);
        eddepartment = (EditText) findViewById(R.id.ed_dept);
        edguest = (EditText) findViewById(R.id.ed_guest);
        edpronites = (EditText) findViewById(R.id.ed_pronites);
        edtheme = (EditText) findViewById(R.id.ed_theme);
        edaccomadtation = (EditText) findViewById(R.id.ed_acco);
        edlastdate = (EditText) findViewById(R.id.ed_lastdate);
        edentryfees = (EditText) findViewById(R.id.ed_fees);
        edhowtoreach = (EditText) findViewById(R.id.ed_howtoreach);
        edcpnam1 = (EditText) findViewById(R.id.ed_cpname1);
        edcpno1 = (EditText) findViewById(R.id.ed_ph1);
        edcpname2 = (EditText) findViewById(R.id.ed_cpname2);
        edcpno2 = (EditText) findViewById(R.id.ed_ph2);
        eventid = Controller.getUID();
        edit_window.setVisibility(View.GONE);
        layout.setVisibility(View.INVISIBLE);
        loadevents();
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_window.setVisibility(View.VISIBLE);
                view_window.setVisibility(View.INVISIBLE);
            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spotsDialog.show();
                stitle = edtitle.getText().toString();
                sategory = edcategory.getText().toString();
                ssdate = edsdate.getText().toString();
                sedate = ededate.getText().toString();
                sorganizer = edorganizer.getText().toString();
                scity = edcity.getText().toString();
                sstate = edstate.getText().toString();
                sDiscription = edDiscription.getText().toString();
                seventdetails = edeventdetails.getText().toString();
                sdepartment = eddepartment.getText().toString();
                sguest = edguest.getText().toString();
                spronites = edpronites.getText().toString();
                saccomadtation = edaccomadtation.getText().toString();
                slastdate = edlastdate.getText().toString();
                sentryfees = edentryfees.getText().toString();
                showtoreach = edhowtoreach.getText().toString();
                scpnam1 = edcpnam1.getText().toString();
                scpno1 = edcpno1.getText().toString();
                scpname2 = edcpname2.getText().toString();
                scpno2 = edcpno2.getText().toString();
                eweb = edwebevent.getText().toString();
                cweb = edwedcoll.getText().toString();


                Call<String> call = ApiUtil.getServiceClass().updateevents(stitle, sategory, ssdate, sedate, sdepartment, sDiscription, sstate, sorganizer, seventdetails, sDiscription, eweb, cweb, scpnam1, scpno1, scpname2, scpno2, sentryfees, spronites, saccomadtation, showtoreach, slastdate, sguest, eventid);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        Log.d("Response", response.body().toString());

                        spotsDialog.dismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Events.this);
                        builder.setTitle("Event Updated Success...");
                        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                layout.setVisibility(View.INVISIBLE);
                                parentlayout.setVisibility(View.VISIBLE);
                                loadevents();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("onFailure", call.toString());
                        //progressDialog.dismiss();
                    }
                });


            }
        });

    }

    void loadevents() {
        final SpotsDialog spotsDialog = new SpotsDialog(this);
        spotsDialog.show();
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.LOAD_STUDENTEVENT + "?eid=" + eventid);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {


                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.d("RESPONSE1", response.body().toString());
                    drawerResponseList = response.body().getProject_details();
                    Log.d("RESPONSE1", String.valueOf(drawerResponseList.size()));
                    if (drawerResponseList.size() == 0) {
                        spotsDialog.dismiss();
                        layoutempty.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.INVISIBLE);
                        parentlayout.setVisibility(View.INVISIBLE);

                    } else {
                        spotsDialog.dismiss();
                        layoutempty.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.INVISIBLE);
                        parentlayout.setVisibility(View.VISIBLE);
                        Glide.with(Edit_Events.this)
                                .load(drawerResponseList.get(0).getPoster())
                                .placeholder(R.drawable.load)
                                .into(head_poster);
                        Glide.with(Edit_Events.this)
                                .load(drawerResponseList.get(0).getPoster())
                                .placeholder(R.drawable.load)
                                .into(head_posteredit);
                        title.setText(drawerResponseList.get(0).getEvent_Title());
                        category.setText(drawerResponseList.get(0).getEvent_Type());
                        sdate.setText(drawerResponseList.get(0).getEvent_Start_Date());
                        edate.setText(drawerResponseList.get(0).getEvent_End_Date());
                        organizer.setText(drawerResponseList.get(0).getEvent_Organiser());
                        city.setText(drawerResponseList.get(0).getCollege_District());
                        state.setText(drawerResponseList.get(0).getCollege_State());
                        Discription.setText(drawerResponseList.get(0).getEvent_Discription());
                        String output = String.valueOf(drawerResponseList.get(0).getEvent_Details()).replace("/*NonTechnical Events*/", "<font color=#000000><b><br>NonTechnical Events<br></b></font>").replace("/*Technical Events*/", "<font color=#000000><b>Technical Events<br></b></font>").replace("/*Workshop Events*/", "<font color=#000000><b><br>Workshop Events<br></b></font>").replace("/*Online Events*/", "<font color=#000000><b><br>Online Events<br></b></font>");
                        eventdetails.setText(Html.fromHtml(output));
                        department.setText(drawerResponseList.get(0).getDept());
                        guest.setText(drawerResponseList.get(0).getEvent_guest());
                        pronites.setText(drawerResponseList.get(0).getEvent_pro_nites());
                        theme.setText(drawerResponseList.get(0).getEvent_Name());
                        accomadtation.setText(drawerResponseList.get(0).getEvent_accomodations());
                        lastdate.setText(drawerResponseList.get(0).getLast_date_registration());
                        entryfees.setText(drawerResponseList.get(0).getEntry_Fees());
                        howtoreach.setText(drawerResponseList.get(0).getEvent_how_to_reach());
                        cpnam1.setText(drawerResponseList.get(0).getContact_Person1_Name());
                        cpno1.setText(drawerResponseList.get(0).getContact_Person1_No());
                        cpname2.setText(drawerResponseList.get(0).getContact_Person2_Name());
                        cpno2.setText(drawerResponseList.get(0).getContact_Person2_No());
                        txtwebevent.setText(drawerResponseList.get(0).getEvent_Website());
                        txtwebcoll.setText(drawerResponseList.get(0).getCollege_Website());

                        edtitle.setText(drawerResponseList.get(0).getEvent_Title());
                        edcategory.setText(drawerResponseList.get(0).getEvent_Type());
                        edsdate.setText(drawerResponseList.get(0).getEvent_Start_Date());
                        ededate.setText(drawerResponseList.get(0).getEvent_End_Date());
                        edorganizer.setText(drawerResponseList.get(0).getEvent_Organiser());
                        edcity.setText(drawerResponseList.get(0).getCollege_District());
                        edstate.setText(drawerResponseList.get(0).getCollege_State());
                        edDiscription.setText(drawerResponseList.get(0).getEvent_Discription());
                        String output1 = String.valueOf(drawerResponseList.get(0).getEvent_Details()).replace("/*NonTechnical Events*/", "<font color=#000000><b><br>NonTechnical Events<br></b></font>").replace("/*Technical Events*/", "<font color=#000000><b>Technical Events<br></b></font>").replace("/*Workshop Events*/", "<font color=#000000><b><br>Workshop Events<br></b></font>").replace("/*Online Events*/", "<font color=#000000><b><br>Online Events<br></b></font>");
                        edeventdetails.setText(Html.fromHtml(output1));
                        eddepartment.setText(drawerResponseList.get(0).getDept());
                        edguest.setText(drawerResponseList.get(0).getEvent_guest());
                        edpronites.setText(drawerResponseList.get(0).getEvent_pro_nites());
                        edtheme.setText(drawerResponseList.get(0).getEvent_Name());
                        edaccomadtation.setText(drawerResponseList.get(0).getEvent_accomodations());
                        edlastdate.setText(drawerResponseList.get(0).getLast_date_registration());
                        edentryfees.setText(drawerResponseList.get(0).getEntry_Fees());
                        edhowtoreach.setText(drawerResponseList.get(0).getEvent_how_to_reach());
                        edcpnam1.setText(drawerResponseList.get(0).getContact_Person1_Name());
                        edcpno1.setText(drawerResponseList.get(0).getContact_Person1_No());
                        edcpname2.setText(drawerResponseList.get(0).getContact_Person2_Name());
                        edcpno2.setText(drawerResponseList.get(0).getContact_Person2_No());
                        edwebevent.setText(drawerResponseList.get(0).getEvent_Website());
                        edwedcoll.setText(drawerResponseList.get(0).getCollege_Website());


                    }
                }

            }

            @Override
            public void onFailure(Call<DownloadResponse

                    > call, Throwable t) {
                //showErrorMessage();

                Log.d("RESPONSE3", "err" + t.getMessage());
            }
        });
    }

}
