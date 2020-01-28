package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEvent3 extends AppCompatActivity {

    EditText eventweb, collegeweb, fgust, fnits, eventheam, accomation, contactname1, contactno1, contactname2, contactno2, lastdate, regfees, howtoreach, sponser;
    SpotsDialog progressDialog;
    Button complete;
    String webevent, webcollege;
    String elist, epost, etitle, ecat, eorg, ecity, estae, esdate, eedate, edpt, efg, enits, etheam, eacc, conname1, conno1, conname2, conno2, eldate, eregf, ehreach, esponser;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event3);
         sharedPreferences = getSharedPreferences("DEV_ID", MODE_PRIVATE);
         editor=sharedPreferences.edit();
        fgust = findViewById(R.id.add3_fgusest);
        fnits = findViewById(R.id.add3_fnits);
        eventheam = findViewById(R.id.add3_etheam);
        accomation = findViewById(R.id.add3_accomodation);
        contactname1 = findViewById(R.id.add3_contactdetails);
        contactno1 = (EditText) findViewById(R.id.add3_contactnumber);
        contactname2 = (EditText) findViewById(R.id.add3_contactdetails2);
        contactno2 = (EditText) findViewById(R.id.add3_contactnumber2);
        lastdate = findViewById(R.id.add3_lastdate);
        regfees = findViewById(R.id.add3_regfees);
        howtoreach = findViewById(R.id.add3_howtoreach);
        sponser = findViewById(R.id.add3_sponser);
        complete = findViewById(R.id.add3_complete);
        eventweb = (EditText) findViewById(R.id.add3_eventweb);
        collegeweb = (EditText) findViewById(R.id.add3_collegeweb);

    }

    @Override
    protected void onResume() {
        super.onResume();

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new SpotsDialog(AddEvent3.this, R.style.Custom);
                progressDialog.show();
                SharedPreferences sf = getSharedPreferences("event", MODE_PRIVATE);
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
                efg = fgust.getText().toString();
                enits = fnits.getText().toString();
                etheam = eventheam.getText().toString();
                eacc = accomation.getText().toString();
                conname1 = contactname1.getText().toString();
                conno1 = contactno1.getText().toString();
                conname2 = contactname2.getText().toString();
                conno2 = contactno2.getText().toString();
                eldate = lastdate.getText().toString();
                eregf = regfees.getText().toString();
                ehreach = howtoreach.getText().toString();
                esponser = sponser.getText().toString();
                webevent = eventweb.getText().toString();
                webcollege = collegeweb.getText().toString();
                Log.d("alldatas", "onClick: " + elist.toString());
                String Event_Title = etitle;
                String Event_Type = ecat;
                String Event_Name = etitle;
                String Event_Start_Date = esdate;
                String Event_End_Date = eedate;
                String College_Name = "Arunai";
                String Degree = "BE";
                String Dept = edpt;
                String College_Address = ecity + " , " + estae;
                String College_District = ecity;
                String College_State = estae;
                String Event_organizer = eorg;
                String Event_Details = elist;
                String Event_Discription = "conducted by uniqtechnologies";
                String Event_Website = webevent;
                String College_Website = webcollege;
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
                String Event_status = "fine";
                String Comments = "Testing by uniq...";
                Call<String> call = ApiUtil.getServiceClass().insertUser(sharedPreferences.getString("uid",null),Event_Title, Event_Type, Event_Name,Event_Start_Date,Event_End_Date,College_Name,Degree,Dept,College_Address,College_District,College_State,Event_organizer,Event_Details,Event_Discription,Event_Website,College_Website,Contact_Person1_Name,Contact_Person1_No,Contact_Person2_Name,Contact_Person2_No,Poster,Entry_Fees,Accepted,Event_Lat,Event_Long,Event_guest,Event_pro_nites,Event_accomodations,Event_how_to_reach,Event_sponsors,Last_date_registration,Event_status,Comments);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        Log.d("Response", response.body().toString());
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("7092229994", null, "Dear Student :  Your Request Approved...", null, null);
                        progressDialog.dismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(AddEvent3.this);
                        builder.setTitle("Congrats...Your Request Send...");
                        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(AddEvent3.this,EventActivity.class);
                                startActivity(intent);

                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("onFailure", call.toString());
                        progressDialog.dismiss();
                    }
                });

            }
        });

    }
}
