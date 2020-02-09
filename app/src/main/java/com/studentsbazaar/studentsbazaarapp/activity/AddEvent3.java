package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.net.URL;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEvent3 extends AppCompatActivity {

    EditText ecomments, fgust, fnits, eventheam, accomation, contactname1, contactno1, contactname2, contactno2, lastdate, regfees, howtoreach, sponser;
    SpotsDialog progressDialog;
    Button complete;
    AutoCompleteTextView eventweb, collegeweb;
    String webevent, webcollege;
    private int mYear, mMonth, mDay;
    String ecommts, edis, econby, elist, epost, etitle, ecat, eorg, ecity, estae, esdate, eedate, edpt, efg, enits, etheam, eacc, conname1, conno1, conname2, conno2, eldate, eregf, ehreach, esponser;
    SharedPreferences sharedPreferences, sf;
    SharedPreferences.Editor editor;
    String[] urlformat = {"http://", "https://"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event3);
        sharedPreferences = getSharedPreferences("USER_DETAILS", MODE_PRIVATE);
        sf = getSharedPreferences("event", MODE_PRIVATE);
        Log.d("USER_DETAILS", "onClick: " + sharedPreferences.getString("UID", null));
        fgust = findViewById(R.id.add3_fgusest);
        fnits = findViewById(R.id.add3_fnits);
        eventheam = findViewById(R.id.add3_etheam);
        accomation = findViewById(R.id.add3_accomodation);
        contactname1 = findViewById(R.id.add3_contactdetails);
        contactno1 = (EditText) findViewById(R.id.add3_contactnumber);
        contactname2 = (EditText) findViewById(R.id.add3_contactdetails2);
        contactno2 = (EditText) findViewById(R.id.add3_contactnumber2);
        ecomments = (EditText) findViewById(R.id.add3_comments);
        lastdate = findViewById(R.id.add3_lastdate);
        regfees = findViewById(R.id.add3_regfees);
        howtoreach = findViewById(R.id.add3_howtoreach);
        sponser = findViewById(R.id.add3_sponser);
        complete = findViewById(R.id.button2);
        eventweb = (AutoCompleteTextView) findViewById(R.id.add3_eventweb);
        collegeweb = (AutoCompleteTextView) findViewById(R.id.add3_collegeweb);
        lastdate.setText(sf.getString("esdate", "none"));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddEvent3.this, android.R.layout.simple_list_item_1, urlformat);
        eventweb.setAdapter(arrayAdapter);
        eventweb.setThreshold(0);
        collegeweb.setAdapter(arrayAdapter);
        collegeweb.setThreshold(0);
//        lastdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent3.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                eedate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//
//                                lastdate.setText(eedate);
//
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });
        eventweb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                eventweb.showDropDown();
                if (isValid(eventweb.getText().toString())) {

                } else if (eventweb.getText().toString().length() == 0) {

                } else {
                    eventweb.setError("Invalid url format");
                }
            }
        });

        collegeweb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                collegeweb.showDropDown();
                if (isValid(collegeweb.getText().toString())) {

                } else if (collegeweb.getText().toString().length() == 0) {

                } else {
                    collegeweb.setError("Invalid url format");
                }
            }
        });

    }

    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

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
                ecommts = ecomments.getText().toString();
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
                if (Contact_Person1_Name.isEmpty() && Contact_Person1_No.isEmpty() && Contact_Person2_Name.isEmpty() && Contact_Person2_No.isEmpty() && Last_date_registration.isEmpty() && Event_Website.isEmpty() && College_Website.isEmpty() && Entry_Fees.isEmpty()) {
                    Toast.makeText(AddEvent3.this, "All Fields are mandatory...", Toast.LENGTH_SHORT).show();

                } else if (contactno1.getText().toString().length() < 10) {
                    contactno1.setError("Please fill valid 10 Digit");
                } else if (contactno2.getText().toString().length() < 10) {
                    contactno2.setError("Please fill valid 10 Digit");
                } else {
                    progressDialog.show();
                    Log.d("eventdetails",Event_Details);
                    Log.d("alldatas", "onClick: " + Event_Title + " " + Event_Type + " " + Event_Name + " " + Event_Start_Date + " " + Event_End_Date + " " + Conductedby + " " + Degree + " " + Dept + " " + College_Address + " " + College_District + " " + College_State + " " + Event_organizer + " " + Event_Details + Event_Discription + " " + Event_Website + " " + College_Website + " " + Contact_Person1_Name + " " + Contact_Person1_No + " " + Contact_Person2_Name + " " + Contact_Person2_No + " " + " " + Entry_Fees + " " + Accepted + " " + Event_Lat + " " + Event_Long + " " + Event_guest + " " + Event_pro_nites + " " + Event_accomodations + " " + Event_how_to_reach + " " + Event_sponsors + " " + Last_date_registration + " " + Event_status);
                    Call<String> call = ApiUtil.getServiceClass().insertUser(sharedPreferences.getString("UID", null), Event_Title, Event_Type, Event_Name, Event_Start_Date, Event_End_Date, Conductedby, Degree, Dept, College_Address, College_District, College_State, Event_organizer, Event_Details, Event_Discription, Event_Website, College_Website, Contact_Person1_Name, Contact_Person1_No, Contact_Person2_Name, Contact_Person2_No, Poster, Entry_Fees, Accepted, Event_Lat, Event_Long, Event_guest, Event_pro_nites, Event_accomodations, Event_how_to_reach, Event_sponsors, Last_date_registration, Event_status, Comments);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            Log.d("Response", response.body().toString());

                            if (response.body().equals("1")) {
                                getAlertwindow("Thank you...\nYour Request has been Sent...\nAdmin will reach you soon...", response.body());
                                progressDialog.dismiss();

                            } else {
                                getAlertwindow("Sorry\n,Your Request Couldn't Send...Please try again...", response.body());
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
                    Intent intent = new Intent(AddEvent3.this, EventActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(AddEvent3.this, AddEvent2.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
