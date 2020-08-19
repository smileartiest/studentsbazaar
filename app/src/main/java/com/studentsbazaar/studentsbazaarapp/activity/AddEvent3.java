package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEvent3 extends AppCompatActivity {

    TextInputLayout eventweb, collegeweb, regfees, einstagram, ecomments, fgust, fnits, eventheam, accomation, contactname1, contactno1, contactname2, contactno2, lastdate, howtoreach, sponser;
    SpotsDialog progressDialog;
    Button complete;
    String webevent, webcollege;
    String einsta, ecommts, edis, econby, elist, epost, etitle, ecat, eorg, ecity, estae, esdate, eedate, edpt, efg, enits, etheam, eacc, conname1, conno1, conname2, conno2, eldate, eregf, ehreach, esponser;
    SharedPreferences sf;
    SharedPreferences.Editor editor;
    Toolbar my_toolbar;
    ImageView add_date_icon;
    private int mYear, mMonth, mDay;
    String day;
    WebView webView;
    String[] urlformat = {"http://", "https://"};
    String[] feesarray = {"No Entry Fees"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event3);
        new Controller(this);

        my_toolbar = findViewById(R.id.add3_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        my_toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.SRC_ATOP);

        sf = getSharedPreferences("event", MODE_PRIVATE);
        fgust = findViewById(R.id.add3_fgusest);
        fnits = findViewById(R.id.add3_fnits);
        eventheam = findViewById(R.id.add3_etheam);
        accomation = findViewById(R.id.add3_accomodation);
        contactname1 = findViewById(R.id.add3_contactdetails);
        contactno1 = findViewById(R.id.add3_contactnumber);
        contactname2 = findViewById(R.id.add3_contactdetails2);
        contactno2 = findViewById(R.id.add3_contactnumber2);
        ecomments = findViewById(R.id.add3_comments);
        lastdate = findViewById(R.id.add3_lastdate);
        regfees = findViewById(R.id.add3_regfees);
        howtoreach = findViewById(R.id.add3_howtoreach);
        sponser = findViewById(R.id.add3_sponser);
        complete = findViewById(R.id.button2);
        einstagram = findViewById(R.id.add3_instagram);
        eventweb = findViewById(R.id.add3_eventweb);
        collegeweb = findViewById(R.id.add3_collegeweb);
        add_date_icon = findViewById(R.id.add3_lastdate_add_icon);
        lastdate.getEditText().setText(sf.getString("esdate", "none"));
        fgust.requestFocus();
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddEvent3.this, android.R.layout.simple_list_item_1, urlformat);
        eventweb.setAdapter(arrayAdapter);
        eventweb.setThreshold(0);
        collegeweb.setAdapter(arrayAdapter);
        collegeweb.setThreshold(0);
        ArrayAdapter<String> feesadapter = new ArrayAdapter<>(AddEvent3.this, android.R.layout.simple_list_item_1, feesarray);
        regfees.setAdapter(feesadapter);
        regfees.setThreshold(0);
        regfees.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                regfees.showDropDown();
                return false;

            }
        });
        eventweb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                eventweb.showDropDown();
                return false;

            }
        });
        collegeweb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                collegeweb.showDropDown();
                return false;
            }
        });*/

        my_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        add_date_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent3.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if (dayOfMonth < 10) {
                                    day = "0" + dayOfMonth;
                                } else {
                                    day = String.valueOf(dayOfMonth);
                                }
                                esdate = year + "-" + (monthOfYear + 1) + "-" + day;
                                lastdate.getEditText().setText(esdate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new SpotsDialog(AddEvent3.this);

                epost = sf.getString("epost", "none");
                elist = sf.getString("elist", "none");
                etitle = sf.getString("etitle", "none");
                ecat = sf.getString("ecat", "none");
                eorg = sf.getString("eorg", "none");
                ecity = sf.getString("ecity", "none");
                estae = sf.getString("estate", "none");
                edpt = sf.getString("edpt", "none");
                esdate = sf.getString("esdate", "none");
                eedate = sf.getString("eedate", "none");
                econby = sf.getString("conduct", "none");
                edis = sf.getString("edis", null);
                efg = fgust.getEditText().getText().toString();
                enits = fnits.getEditText().getText().toString();
                etheam = eventheam.getEditText().getText().toString();
                eacc = accomation.getEditText().getText().toString();
                conname1 = contactname1.getEditText().getText().toString();
                conno1 = contactno1.getEditText().getText().toString();
                conname2 = contactname2.getEditText().getText().toString();
                conno2 = contactno2.getEditText().getText().toString();
                eldate = lastdate.getEditText().getText().toString();
                eregf = regfees.getEditText().getText().toString();
                ehreach = howtoreach.getEditText().getText().toString();
                esponser = sponser.getEditText().getText().toString();
                webevent = eventweb.getEditText().getText().toString();
                webcollege = collegeweb.getEditText().getText().toString();
                ecommts = ecomments.getEditText().getText().toString();
                einsta = einstagram.getEditText().getText().toString();
                String Event_Title = etitle;
                String Event_Type = ecat;
                String Event_Name = etitle;
                String Event_Start_Date = esdate;
                String Event_End_Date = eedate;
                String Conductedby = econby;
                String Degree = "BE";
                String Dept = edpt;
                String College_Address = ecity + " , " + estae;
                String College_District = ecity;
                String College_State = estae;
                String Event_organizer = eorg;
                String Event_Details = elist;
                String Event_Discription = edis;
                String Event_Website = webevent;
                String College_Website = webcollege;
                String Event_Instagram = einsta;
                String Contact_Person1_Name = conname1;
                String Contact_Person1_No = conno1;
                String Contact_Person2_Name = conname2;
                String Contact_Person2_No = conno2;
                String Poster = epost;
                String Entry_Fees = eregf;
                String Accepted = "0";
                String Event_Lat = "0";
                String Event_Long = "0";
                String Event_guest = efg;
                String Event_pro_nites = enits;
                String Event_accomodations = eacc;
                String Event_how_to_reach = ehreach;
                String Event_sponsors = esponser;
                String Last_date_registration = eldate;
                String Event_status = "0";
                String Comments = ecommts;
                if (Event_guest.isEmpty()) {
                    Move_Show.showToast("Enter Event Guest Name");
                } else if (Event_accomodations.isEmpty()) {
                    Move_Show.showToast("Enter Event Accommodation");
                } else if (Contact_Person1_Name.isEmpty()) {
                    Move_Show.showToast("Enter Contact Person");
                } else if (Contact_Person1_No.length() < 10) {
                    Move_Show.showToast("Please enter Valid 10 Digit Number");
                } else if (Contact_Person2_Name.isEmpty()) {
                    Move_Show.showToast("Enter Contact Person");
                } else if (Contact_Person2_No.length() < 10) {
                    Move_Show.showToast("Please enter Valid 10 Digit Number");
                } else if (Last_date_registration.isEmpty()) {
                    Move_Show.showToast("Enter Last date for registration");
                } else if (Entry_Fees.isEmpty()) {
                    Move_Show.showToast("Enter Entry Fees");
                } else if (Event_how_to_reach.isEmpty()) {
                    Move_Show.showToast("Please Enter Landmark/Route");
                } else {
                    progressDialog.show();
                    Log.d("eventdetails", Event_Details);
                    Log.d("alldatas", "onClick: " + Event_Title + " " + Event_Type + " " + Event_Name + " " + Event_Start_Date + " " + Event_End_Date + " " + Conductedby + " " + Degree + " " + Dept + " " + College_Address + " " + College_District + " " + College_State + " " + Event_organizer + " " + Event_Details + Event_Discription + " " + Event_Website + " " + College_Website + " " + Contact_Person1_Name + " " + Contact_Person1_No + " " + Contact_Person2_Name + " " + Contact_Person2_No + " " + " " + Entry_Fees + " " + Accepted + " " + Event_Lat + " " + Event_Long + " " + Event_guest + " " + Event_pro_nites + " " + Event_accomodations + " " + Event_how_to_reach + " " + Event_sponsors + " " + Last_date_registration + " " + Event_status);
                    Call<String> call = ApiUtil.getServiceClass().insertUser(Controller.getUID(), Event_Title, Event_Type, Event_Name, Event_Start_Date, Event_End_Date, Conductedby, Degree, Dept, College_Address, College_District, College_State, Event_organizer, Event_Details, Event_Discription, Event_Website, College_Website, Event_Instagram, Contact_Person1_Name, Contact_Person1_No, Contact_Person2_Name, Contact_Person2_No, Poster, Entry_Fees, Accepted, Event_Lat, Event_Long, Event_guest, Event_pro_nites, Event_accomodations, Event_how_to_reach, Event_sponsors, Last_date_registration, Event_status, Comments);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            Log.d("Response", response.body().toString());

                            if (response.body().equals("1")) {

                                getAlertwindow("Thank you...\nYour Request has been Sent...\nAdmin will reach you soon..!!", response.body());
                                progressDialog.dismiss();

                            } else {
                                getAlertwindow("Sorry\n,Your Request Couldn't Send...Please try again..!!", response.body());
                            }


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("onFailure", call.toString());
                            progressDialog.dismiss();
                        }
                    });

                }
            }
        });

    }

    void getAlertwindow(String message, final String res) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent3.this);
        builder.setTitle(message);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (res.equals("1")) {
                    new Move_Show(AddEvent3.this, EventActivity.class);
                    finish();
                } else {
                    new Move_Show(AddEvent3.this, AddEvent2.class);
                    finish();
                }

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
