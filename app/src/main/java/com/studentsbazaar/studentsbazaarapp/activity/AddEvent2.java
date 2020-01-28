package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentsbazaar.studentsbazaarapp.R;


import java.util.ArrayList;
import java.util.Calendar;

public class AddEvent2 extends AppCompatActivity {

    FloatingActionButton next;
    EditText title,catagory,orgnniser;
    AutoCompleteTextView city,state;
    TextView startdate,enddate,departmentlist;
    ImageView adddepartment;

    Dialog d;
    Button cse,ece,it,eee,civl,chemical,agri,medical,pharm,arts,biotech,mba,mca,commerce,law,biomedical,mech,aeronoutical,aerospace,design,fashion,media,bba;
    TextView cancel;

    ArrayList<String> deptlist = new ArrayList<>();

    private int mYear, mMonth, mDay;

    String etitle,ecat,eorg,ecity,estae,esdate,eedate,edpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event2);

        title = findViewById(R.id.add2_eventtitle);
        catagory = findViewById(R.id.add2_eventcatagory);
        orgnniser = findViewById(R.id.add2_eventorganiser);
        city = findViewById(R.id.add2_eventcity);
        state = findViewById(R.id.add2_eventstate);
        startdate = findViewById(R.id.add2_eventstartdate);
        enddate = findViewById(R.id.add2_eventenddate);
        departmentlist = findViewById(R.id.add2_eventdepartment);
        adddepartment = findViewById(R.id.add2_departmentaddicon);
        next = findViewById(R.id.add2_nextbtn);

        if(departmentlist.getText().length()==0)
        {
            departmentlist.setText("no department choose");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent2.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                esdate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                startdate.setText(esdate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent2.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                eedate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                                enddate.setText(eedate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        adddepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new Dialog(AddEvent2.this);
                d.setContentView(R.layout.departmentlist_box);

                cse = d.findViewById(R.id.cse);
                ece = d.findViewById(R.id.ece);
                it = d.findViewById(R.id.it);
                eee = d.findViewById(R.id.eee);
                civl = d.findViewById(R.id.civl);
                chemical = d.findViewById(R.id.chemical);
                agri = d.findViewById(R.id.agri);
                medical = d.findViewById(R.id.medical);
                pharm = d.findViewById(R.id.pharmacy);
                arts = d.findViewById(R.id.arts);
                biotech = d.findViewById(R.id.biotech);
                mba = d.findViewById(R.id.mba);
                mca = d.findViewById(R.id.mca);
                commerce = d.findViewById(R.id.commerce);
                law = d.findViewById(R.id.law);
                biomedical = d.findViewById(R.id.biomedi);
                mech = d.findViewById(R.id.mech);
                aeronoutical = d.findViewById(R.id.aeronoutical);
                aerospace = d.findViewById(R.id.aerospace);
                design = d.findViewById(R.id.design);
                fashion = d.findViewById(R.id.fashion);
                media = d.findViewById(R.id.media);
                bba = d.findViewById(R.id.bba);
                cancel = d.findViewById(R.id.cancel);


                cse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("cse");
                        cse.setEnabled(false);
                    }
                });

                ece.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("ece");
                        ece.setEnabled(false);
                    }
                });

                it.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("it");
                        it.setEnabled(false);
                    }
                });

                eee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("eee");
                        eee.setEnabled(false);
                    }
                });

                civl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("civl");
                        civl.setEnabled(false);
                    }
                });

                chemical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("chemical");
                        chemical.setEnabled(false);
                    }
                });

                agri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("agriculture");
                        agri.setEnabled(false);
                    }
                });

                medical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("medical");
                        medical.setEnabled(false);
                    }
                });

                pharm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("Pharmacy");
                        pharm.setEnabled(false);
                    }
                });

                arts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("Arts");
                        arts.setEnabled(false);
                    }
                });

                biotech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("bio technology");
                        biotech.setEnabled(false);
                    }
                });

                mba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("MBA");
                        mba.setEnabled(false);
                    }
                });

                mca.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("MCA");
                        mca.setEnabled(false);
                    }
                });

                commerce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("commerce");
                        commerce.setEnabled(false);
                    }
                });

                law.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("Law");
                        law.setEnabled(false);
                    }
                });

                biomedical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("bio medical");
                        biomedical.setEnabled(false);
                    }
                });

                mech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("mechanics");
                        mech.setEnabled(false);
                    }
                });

                aeronoutical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("aeronoutical");
                        aeronoutical.setEnabled(false);
                    }
                });

                aerospace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("aerspace");
                        aerospace.setEnabled(false);
                    }
                });

                design.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("Design");
                        design.setEnabled(false);
                    }
                });

                fashion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("fashion");
                        fashion.setEnabled(false);
                    }
                });

                media.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("media");
                        media.setEnabled(false);
                    }
                });

                bba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deptlist.add("BBA");
                        bba.setEnabled(false);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        departmentlist.setText(deptlist.toString());

                        d.dismiss();
                    }
                });

                d.show();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.getText().length()!=0)
                {
                    if(catagory.getText().length()!=0)
                    {
                        if(orgnniser.getText().length()!=0)
                        {
                            if(city.getText().length()!=0)
                            {
                                if(state.getText().length()!=0)
                                {
                                    if(departmentlist.getText().length()!=0)
                                    {
                                        etitle = title.getText().toString();
                                        ecat = catagory.getText().toString();
                                        eorg = orgnniser.getText().toString();
                                        ecity = city.getText().toString();
                                        estae = state.getText().toString();
                                        edpt = departmentlist.getText().toString();

                                        SharedPreferences sf = getSharedPreferences("event" ,MODE_PRIVATE);
                                        SharedPreferences.Editor ed = sf.edit();
                                        ed.putString("etitle" ,etitle);
                                        ed.putString("ecat" , ecat);
                                        ed.putString("eorg",eorg);
                                        ed.putString("ecity",ecity);
                                        ed.putString("estate",estae);
                                        ed.putString("edpt",edpt);
                                        ed.putString("esdate" , esdate);
                                        ed.putString("eedate" , eedate);
                                        ed.commit();

                                        startActivity(new Intent(getApplicationContext() , AddEvent3.class));
                                        finish();
                                    }
                                    else
                                    {
                                        departmentlist.setError("please fill");
                                    }
                                }
                                else
                                {
                                    state.setError("please fill");
                                }
                            }
                            else
                            {
                                city.setError("please fill");
                            }
                        }
                        else
                        {
                            orgnniser.setError("please fill");
                        }
                    }
                    else
                    {
                        catagory.setError("please fill");
                    }
                }
                else
                {
                    title.setError("please fill");
                }
            }
        });

    }

}
