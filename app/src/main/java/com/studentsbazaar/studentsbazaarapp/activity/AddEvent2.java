package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.helper.DateChecker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddEvent2 extends AppCompatActivity {

    FloatingActionButton next;
    TextInputLayout title,catagory, orgnniser, conducted, evendiscrition,departmentlist, city, state,startdate, enddate;
    CheckBox catworkshop, cattechfest, catculfest, catsympo, catconference, catmanagefest, catothers;
    TextView catdone, catcancel;
    Dialog d, catd;
    TextView cse, ece, it, eee, civl, chemical, agri, medical, pharm, arts, biotech, mba, mca, commerce, law, biomedical, mech, aeronoutical, aerospace, design, fashion, media, bba;
    TextView done, txtcat , cat_click , s_date_click , e_date_click , department_click;
    ImageView cancel;
    StringBuilder catlist = new StringBuilder();
    ArrayList<String> deptlist = new ArrayList<>();
    private int mYear, mMonth, mDay;
    String day;
    String disevent, conby, etitle, ecat, eorg, ecity, estae, esdate, eedate, edpt;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event2);

        mToolbar = findViewById(R.id.add2_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.add2_eventtitle);
        catagory = findViewById(R.id.add2_eventcatagory);
        orgnniser = findViewById(R.id.add2_eventorganiser);
        city = findViewById(R.id.add2_eventcity);
        state = findViewById(R.id.add2_eventstate);
        conducted = findViewById(R.id.add3_conductedby);
        startdate = findViewById(R.id.add2_eventstartdate);
        enddate = findViewById(R.id.add2_eventenddate);
        departmentlist = findViewById(R.id.add2_eventdepartment);
        evendiscrition = findViewById(R.id.add2_discrition);
        cat_click = findViewById(R.id.add2_eventcatagory_click);
        s_date_click = findViewById(R.id.add2_eventstartdate_click);
        e_date_click = findViewById(R.id.add2_eventenddate_click);
        department_click = findViewById(R.id.add2_eventdepartment_click);
        txtcat = findViewById(R.id.textView9);
        next = findViewById(R.id.add2_nextbtn);
        title.requestFocus();

        cat_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddepart();
            }
        });
        conducted.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                catd.cancel();
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    void adddepart() {
        catd = new Dialog(AddEvent2.this);
        catd.setContentView(R.layout.cat_degin);
        catd.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT);
        catd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    catlist.append("Workshop ,");
                }
                if (cattechfest.isChecked()) {
                    catlist.append("Tech fest ,");
                }
                if (catothers.isChecked()) {
                    catlist.append("Others ,");
                }
                if (catculfest.isChecked()) {
                    catlist.append("Cultural fest ,");
                }
                if (catsympo.isChecked()) {
                    catlist.append("Symposium ,");
                }
                if (catconference.isChecked()) {
                    catlist.append("Conference ,");
                }
                if (catmanagefest.isChecked()) {
                    catlist.append("Management fest ,");
                }
                catagory.getEditText().setText(catlist.toString());
                catd.dismiss();
                conducted.requestFocus();

            }
        });
        catd.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        s_date_click.setOnClickListener(new View.OnClickListener() {
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
                                    startdate.getEditText().setText(esdate);
                                    enddate.getEditText().setText(esdate);
                                } else {
                                    Move_Show.showToast("StartDate must be after CurrentDate");
                                    startdate.getEditText().setText(esdate);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        e_date_click.setOnClickListener(new View.OnClickListener() {
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
                                    enddate.getEditText().setText(eedate);
                                } else {
                                    Move_Show.showToast("EndDate must be after StartDate");
                                    enddate.getEditText().setText(esdate);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });

        department_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new Dialog(AddEvent2.this);
                d.setContentView(R.layout.departmentlist_box);
                d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                        d.cancel();
                    }
                });
                cse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("cse")){
                            cse.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("cse");
                        }else{
                            cse.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("cse");
                        }
                    }
                });

                ece.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("ece")){
                            ece.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("ece");
                        }else{
                            ece.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("ece");
                        }
                    }
                });

                it.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("it")){
                            it.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("it");
                        }else{
                            it.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("it");
                        }
                    }
                });

                eee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("eee")){
                            eee.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("eee");
                        }else{
                            eee.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("eee");
                        }
                    }
                });

                civl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("civl")){
                            civl.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("civl");
                        }else{
                            civl.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("civl");
                        }
                    }
                });

                chemical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("chemical")){
                            chemical.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("chemical");
                        }else{
                            chemical.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("chemical");
                        }
                    }
                });

                agri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("agri")){
                            agri.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("agri");
                        }else{
                            agri.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("agri");
                        }
                    }
                });

                medical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("medical")){
                            medical.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("medical");
                        }else{
                            medical.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("medical");
                        }
                    }
                });

                pharm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("pharm")){
                            pharm.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("pharm");
                        }else{
                            pharm.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("pharm");
                        }
                    }
                });

                arts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("arts")){
                            arts.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("arts");
                        }else{
                            arts.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("arts");
                        }
                    }
                });

                biotech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("biotech")){
                            biotech.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("biotech");
                        }else {
                            biotech.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("biotech");
                        }
                    }
                });

                mba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("mba")){
                            mba.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("mba");
                        }else {
                            mba.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("mba");
                        }
                    }
                });

                mca.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("mca")){
                            mca.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("mca");
                        }else {
                            mca.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("mca");
                        }
                    }
                });

                commerce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("commerce")){
                            commerce.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("commerce");
                        }else {
                            commerce.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("commerce");
                        }
                    }
                });

                law.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("law")){
                            law.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("law");
                        }else {
                            law.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("law");
                        }
                    }
                });

                biomedical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("biomedical")){
                            biomedical.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("biomedical");
                        }else {
                            biomedical.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("biomedical");
                        }
                    }
                });

                mech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("mech")){
                            mech.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("mech");
                        }else {
                            mech.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("mech");
                        }
                    }
                });

                aeronoutical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("aeronoutical")){
                            aeronoutical.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("aeronoutical");
                        }else {
                            aeronoutical.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("aeronoutical");
                        }
                    }
                });

                aerospace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("aerospace")){
                            aerospace.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("aerospace");
                        }else {
                            aerospace.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("aerospace");
                        }
                    }
                });

                design.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("design")){
                            design.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("design");
                        }else {
                            design.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("design");
                        }
                    }
                });

                fashion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("fashion")){
                            fashion.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("fashion");
                        }else {
                            fashion.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("fashion");
                        }
                    }
                });

                media.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("media")){
                            media.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("media");
                        }else {
                            media.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("media");
                        }
                    }
                });

                bba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deptlist.contains("bba")){
                            bba.setBackgroundResource(R.drawable.border_gray);
                            deptlist.remove("bba");
                        }else {
                            bba.setBackgroundResource(R.drawable.border_green);
                            deptlist.add("bba");
                        }
                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        StringBuilder dplist_new = new StringBuilder();
                        for(int i = 0 ; i < deptlist.size() ; i++){
                            dplist_new.append(deptlist.get(i)+" , ");
                        }
                        departmentlist.getEditText().setText(dplist_new.toString());
                        d.dismiss();
                    }
                });

                d.show();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etitle = title.getEditText().getText().toString();
                ecat = catagory.getEditText().getText().toString();
                eorg = orgnniser.getEditText().getText().toString();
                ecity = city.getEditText().getText().toString();
                estae = state.getEditText().getText().toString();
                edpt = departmentlist.getEditText().getText().toString();
                conby = conducted.getEditText().getText().toString();
                disevent = evendiscrition.getEditText().getText().toString();
                if (etitle.isEmpty()) {
                    title.getEditText().setError("Enter Event Title");
                    title.getEditText().setFocusable(true);
                } else if (disevent.isEmpty()) {
                    evendiscrition.getEditText().setError("Enter Event Description");
                    evendiscrition.getEditText().setFocusable(true);
                } else if (ecat.isEmpty()) {
                    catagory.getEditText().setError("Tap to select event Category");
                    catagory.getEditText().setFocusable(true);
                } else if (conby.isEmpty()) {
                    conducted.getEditText().setError("Enter college / club name");
                    conducted.getEditText().setFocusable(true);
                } else if (eorg.isEmpty()) {
                    orgnniser.getEditText().setError("Enter Organiser Name");
                    orgnniser.getEditText().setFocusable(true);
                } else if (startdate.getEditText().getText().toString().equals("choose")) {
                    startdate.getEditText().setError("Please set Event Start Date");
                    startdate.getEditText().setFocusable(true);
                } else if (enddate.getEditText().getText().toString().equals("choose")) {
                    enddate.getEditText().setError("Please set Event end Date");
                    enddate.getEditText().setFocusable(true);
                } else if (ecity.isEmpty()) {
                    city.getEditText().setError("Enter City Name");
                    city.getEditText().setFocusable(true);
                } else if (estae.isEmpty()) {
                    state.getEditText().setError("Enter State Name");
                    state.getEditText().setFocusable(true);
                } else if (departmentlist.getEditText().getText().toString().equals("tap here")) {
                    departmentlist.getEditText().setError("Please click Select  Eligible Departments");
                    departmentlist.getEditText().setFocusable(true);
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
                    ed.putString("esdate", startdate.getEditText().getText().toString().trim());
                    ed.putString("eedate", enddate.getEditText().getText().toString().trim());
                    ed.putString("edis", disevent);
                    ed.apply();
                    new Move_Show(AddEvent2.this, AddEvent.class);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
