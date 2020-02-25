package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.helper.DateChecker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddEvent2 extends AppCompatActivity {

    FloatingActionButton next;
    EditText title, catagory, orgnniser, conducted, evendiscrition;
    AutoCompleteTextView city, state;
    TextView startdate, enddate, departmentlist;
    ImageView adddepartment;
    CheckBox catworkshop, cattechfest, catculfest, catsympo, catconference, catmanagefest, catothers;
    Button catdone, catcancel;
    Dialog d, catd;
    ToggleButton cse, ece, it, eee, civl, chemical, agri, medical, pharm, arts, biotech, mba, mca, commerce, law, biomedical, mech, aeronoutical, aerospace, design, fashion, media, bba;
    TextView cancel, done, txtcat;
    ArrayList<String> catlist = new ArrayList<>();
    ArrayList<String> deptlist = new ArrayList<>();

    private int mYear, mMonth, mDay;
    String day;
    String disevent, conby, etitle, ecat, eorg, ecity, estae, esdate, eedate, edpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event2);
        title = findViewById(R.id.add2_eventtitle);
        catagory = findViewById(R.id.add2_eventcatagory);
        orgnniser = findViewById(R.id.add2_eventorganiser);
        city = findViewById(R.id.add2_eventcity);
        state = findViewById(R.id.add2_eventstate);
        conducted = findViewById(R.id.add3_conductedby);
        startdate = findViewById(R.id.add2_eventstartdate);
        enddate = findViewById(R.id.add2_eventenddate);
        departmentlist = findViewById(R.id.add2_eventdepartment);
        adddepartment = findViewById(R.id.add2_departmentaddicon);
        evendiscrition = findViewById(R.id.add2_discrition);
        txtcat = findViewById(R.id.textView9);
        catagory.setShowSoftInputOnFocus(false);
        next = findViewById(R.id.add2_nextbtn);
        title.requestFocus();

        if (departmentlist.getText().length() == 0) {
            departmentlist.setText("No Department Selected");
        }
        catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddepart();
            }
        });

        catagory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                adddepart();
            }
        });
        conducted.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                catd.cancel();
            }
        });


    }

    void adddepart() {
        catd = new Dialog(AddEvent2.this);
        catd.setContentView(R.layout.cat_degin);
        catworkshop = (CheckBox) catd.findViewById(R.id.catworkshop);
        cattechfest = (CheckBox) catd.findViewById(R.id.cattechfest);
        catculfest = (CheckBox) catd.findViewById(R.id.catculfest);
        catsympo = (CheckBox) catd.findViewById(R.id.catsympo);
        catconference = (CheckBox) catd.findViewById(R.id.catconf);
        catmanagefest = (CheckBox) catd.findViewById(R.id.catmanage);
        catothers = (CheckBox) catd.findViewById(R.id.catothers);
        catdone = catd.findViewById(R.id.catdone);
        catcancel = catd.findViewById(R.id.catcancel);

        catcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catd.dismiss();

            }
        });

        catdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (catworkshop.isChecked()) {
                    catlist.add("Workshop ");
                }
                if (cattechfest.isChecked()) {
                    catlist.add("Tech fest ");
                }
                if (catothers.isChecked()) {
                    catlist.add("Others ");
                }
                if (catculfest.isChecked()) {
                    catlist.add("Cultural fest ");
                }
                if (catsympo.isChecked()) {
                    catlist.add("Symposium ");
                }
                if (catconference.isChecked()) {
                    catlist.add("Conference ");
                }
                if (catmanagefest.isChecked()) {
                    catlist.add("Management fest ");
                }
                catagory.setText(String.valueOf(catlist));
                catd.dismiss();
                conducted.requestFocus();

            }
        });
        catd.show();

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
                                if (dayOfMonth < 10) {

                                    day = "0" + dayOfMonth;
                                } else {
                                    day = String.valueOf(dayOfMonth);
                                }
                                esdate = year + "-" + (monthOfYear + 1) + "-" + day;
                                Date today = new Date();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateToStr = format.format(today);
                                DateChecker dateChecker = new DateChecker();
                                if (dateChecker.checkPrevDate(dateToStr, esdate)) {
                                    startdate.setText(esdate);
                                    enddate.setText(esdate);
                                } else {
                                    Move_Show.showToast("StartDate must be after CurrentDate");
                                    //startdate.setText(esdate);
                                }

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
                                if (dayOfMonth < 10) {

                                    day = "0" + dayOfMonth;
                                } else {
                                    day = String.valueOf(dayOfMonth);
                                }
                                eedate = year + "-" + (monthOfYear + 1) + "-" + day;


                                DateChecker dateChecker = new DateChecker();
                                if (dateChecker.checkPrevDate(esdate, eedate)) {
                                    enddate.setText(eedate);
                                } else {
                                    Move_Show.showToast("EndDate must be after StartDate");
                                    enddate.setText(esdate);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });

        adddepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deptlist.clear();
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
                done = d.findViewById(R.id.d_done);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deptlist.clear();
                        cse.setBackgroundResource(R.drawable.button);
                        ece.setBackgroundResource(R.drawable.button);
                        it.setBackgroundResource(R.drawable.button);
                        eee.setBackgroundResource(R.drawable.button);
                        civl.setBackgroundResource(R.drawable.button);
                        chemical.setBackgroundResource(R.drawable.button);
                        agri.setBackgroundResource(R.drawable.button);
                        medical.setBackgroundResource(R.drawable.button);
                        pharm.setBackgroundResource(R.drawable.button);
                        arts.setBackgroundResource(R.drawable.button);
                        biotech.setBackgroundResource(R.drawable.button);
                        mba.setBackgroundResource(R.drawable.button);
                        mca.setBackgroundResource(R.drawable.button);
                        commerce.setBackgroundResource(R.drawable.button);
                        law.setBackgroundResource(R.drawable.button);
                        biomedical.setBackgroundResource(R.drawable.button);
                        mech.setBackgroundResource(R.drawable.button);
                        aeronoutical.setBackgroundResource(R.drawable.button);
                        aerospace.setBackgroundResource(R.drawable.button);
                        design.setBackgroundResource(R.drawable.button);
                        fashion.setBackgroundResource(R.drawable.button);
                        media.setBackgroundResource(R.drawable.button);
                        bba.setBackgroundResource(R.drawable.button);

                    }
                });
                cse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cse.getText().toString().equals("Added")) {
                            deptlist.add("cse");
                            cse.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("cse");
                            cse.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                ece.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ece.getText().toString().equals("Added")) {
                            deptlist.add("ece");
                            ece.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("ece");
                            ece.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                it.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (it.getText().toString().equals("Added")) {
                            deptlist.add("it");
                            it.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("it");
                            it.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                eee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (eee.getText().toString().equals("Added")) {
                            deptlist.add("eee");
                            eee.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("eee");
                            eee.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                civl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (civl.getText().toString().equals("Added")) {
                            deptlist.add("civl");
                            civl.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("civl");
                            civl.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                chemical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chemical.getText().toString().equals("Added")) {
                            deptlist.add("chemical");
                            chemical.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("chemical");
                            chemical.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                agri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (agri.getText().toString().equals("Added")) {
                            deptlist.add("agri");
                            agri.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("agri");
                            agri.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                medical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (medical.getText().toString().equals("Added")) {
                            deptlist.add("medical");
                            medical.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("medical");
                            medical.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                pharm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pharm.getText().toString().equals("Added")) {
                            deptlist.add("pharm");
                            pharm.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("pharm");
                            pharm.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                arts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (arts.getText().toString().equals("Added")) {
                            deptlist.add("arts");
                            arts.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("arts");
                            arts.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                biotech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (biotech.getText().toString().equals("Added")) {
                            deptlist.add("biotech");
                            biotech.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("biotech");
                            biotech.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                mba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mba.getText().toString().equals("Added")) {
                            deptlist.add("mba");
                            mba.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("mba");
                            mba.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                mca.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mca.getText().toString().equals("Added")) {
                            deptlist.add("mca");
                            mca.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("mca");
                            mca.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                commerce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (commerce.getText().toString().equals("Added")) {
                            deptlist.add("commerce");
                            commerce.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("commerce");
                            commerce.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                law.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (law.getText().toString().equals("Added")) {
                            deptlist.add("law");
                            law.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("law");
                            law.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                biomedical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (biomedical.getText().toString().equals("Added")) {
                            deptlist.add("biomedical");
                            biomedical.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("biomedical");
                            biomedical.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                mech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mech.getText().toString().equals("Added")) {
                            deptlist.add("mech");
                            mech.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("mech");
                            mech.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                aeronoutical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aeronoutical.getText().toString().equals("Added")) {
                            deptlist.add("aeronoutical");
                            aeronoutical.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("aeronoutical");
                            aeronoutical.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                aerospace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aerospace.getText().toString().equals("Added")) {
                            deptlist.add("aerospace");
                            aerospace.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("aerospace");
                            aerospace.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                design.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (design.getText().toString().equals("Added")) {
                            deptlist.add("design");
                            design.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("design");
                            design.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                fashion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fashion.getText().toString().equals("Added")) {
                            deptlist.add("fashion");
                            fashion.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("fashion");
                            fashion.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                media.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (media.getText().toString().equals("Added")) {
                            deptlist.add("media");
                            media.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("media");
                            media.setBackgroundResource(R.drawable.button);
                        }
                    }
                });

                bba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bba.getText().toString().equals("Added")) {
                            deptlist.add("bba");
                            bba.setBackgroundResource(R.drawable.button2);
                        }else{
                            deptlist.remove("bba");
                            bba.setBackgroundResource(R.drawable.button);
                        }
                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
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
                etitle = title.getText().toString();
                ecat = catagory.getText().toString();
                eorg = orgnniser.getText().toString();
                ecity = city.getText().toString();
                estae = state.getText().toString();
                edpt = departmentlist.getText().toString();
                conby = conducted.getText().toString();
                disevent = evendiscrition.getText().toString();
                if (etitle.isEmpty()) {
                    Move_Show.showToast("Enter Event Title");
                } else if (disevent.isEmpty()) {
                    Move_Show.showToast("Enter Event Description");
                } else if (ecat.isEmpty()) {
                    Move_Show.showToast("Tap to select event Category");
                } else if (conby.isEmpty()) {
                    Move_Show.showToast("Enter Department");
                } else if (eorg.isEmpty()) {
                    Move_Show.showToast("Enter Organiser Name");
                } else if (startdate.getText().toString().equals("choose")) {
                    Move_Show.showToast("Please set Event Start Date");
                } else if (enddate.getText().toString().equals("choose")) {
                    Move_Show.showToast("Please set Event end Date");
                } else if (ecity.isEmpty()) {
                    Move_Show.showToast("Enter City Name");
                } else if (estae.isEmpty()) {
                    Move_Show.showToast("Enter State Name");
                } else if (departmentlist.getText().toString().equals("No Department Selected")) {
                    Move_Show.showToast("Please Select  Eligible Departments");
                } else {
                    SharedPreferences sf = getSharedPreferences("event", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sf.edit();
                    ed.putString("etitle", etitle);
                    ed.putString("ecat", ecat);
                    ed.putString("conduct", conby);
                    ed.putString("eorg", eorg);
                    ed.putString("ecity", ecity);
                    ed.putString("estate", estae);
                    ed.putString("edpt", edpt);
                    ed.putString("esdate", startdate.getText().toString().trim());
                    ed.putString("eedate", enddate.getText().toString().trim());
                    ed.putString("edis", disevent);
                    ed.apply();
                    new Move_Show(AddEvent2.this, AddEvent.class);
                }
            }
        });

    }

}
