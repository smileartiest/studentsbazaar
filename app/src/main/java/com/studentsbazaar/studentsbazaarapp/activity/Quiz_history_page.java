package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.Quiz_History_Adapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.TimeDate;
import com.studentsbazaar.studentsbazaarapp.helper.DateChecker;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Quiz_History;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quiz_history_page extends AppCompatActivity {

    List<Quiz_History> download_response = null;
    ConstraintLayout name_card, no_data, score_card;
    TextView total_score, correct_ans, wrong_ans;
    RecyclerView history_list;
    Quiz_History_Adapter quiz_history_adapter;
    int START_TIME = 9;
    int LOCAL_TIME = 19;
    int CURRENT_TIME;
    Calendar calander;
    Toolbar my_toolbar;
    SpotsDialog spotsDialog;
    TextView from_date, to_date;
    ImageView from_date_icon, to_date_icon;
    TextView search_btn, cancel_btn;
    private int mYear, mMonth, mDay;
    String day, esdate;
    FloatingActionButton download_btn;
    Calendar c1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_history);

        initialise();

        my_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LoadAllData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_history_page.this);
                builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                builder.setCornerRadius(20);
                builder.setTitle("Download");
                builder.setMessage("Are You sure, want to download this result ?");
                builder.addButton("download", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        downloadPDF();
                    }
                });
                builder.addButton("cancel", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    void LoadAllData() {
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getquizresult(ApiUtil.GET_QUIZ_RESULT + "?uid=" + Controller.getUID() + "&type=0");
        call.enqueue(new Callback<DownloadResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.d("Result", response.body().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    download_response = response.body().getQuiz_History();
                    Log.d("size ", String.valueOf(download_response.get(0).getUser_ID()));
                    if (download_response.size() == 0) {
                        name_card.setVisibility(View.GONE);
                        history_list.setVisibility(View.GONE);
                        score_card.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        spotsDialog.dismiss();
                    } else if (response.body().equals("0")) {
                        name_card.setVisibility(View.GONE);
                        history_list.setVisibility(View.GONE);
                        score_card.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        spotsDialog.dismiss();
                    } else {
                        calander = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
                        String time = simpleDateFormat.format(calander.getTime());
                        Log.d("Time", time);
                        CURRENT_TIME = Integer.valueOf(time);
                        Log.d("Today Date", new TimeDate().getDateYMD());
                        total_score.setText("Total Completed Question's . " + download_response.size());
                        int temp = 0;
                        int today = 0;
                        for (int i = 0; i < download_response.size(); i++) {
                            if (String.valueOf(download_response.get(i).getScore()).equals("1")) {
                                if (new TimeDate().getDateYMD().equals(download_response.get(i).getCreate_Date())) {
                                    if (START_TIME <= CURRENT_TIME && LOCAL_TIME > CURRENT_TIME) {
                                        today = today + 1;
                                    } else {
                                        today = 0;
                                        temp = temp + 1;
                                    }
                                } else {
                                    temp = temp + 1;
                                    Log.d("Matched", temp + " / " + download_response.get(i).getScore());
                                }
                            } else {
                                if (new TimeDate().getDateYMD().equals(download_response.get(i).getCreate_Date())) {
                                    if (START_TIME <= CURRENT_TIME && LOCAL_TIME > CURRENT_TIME) {
                                        today = today + 1;
                                    } else {
                                        today = 0;
                                        temp = temp + 1;
                                    }
                                } else {
                                    temp = temp + 0;
                                    Log.d("Matched", temp + " / " + download_response.get(i).getScore());
                                }
                            }
                        }
                        int wrongans = download_response.size() - temp - today;
                        correct_ans.setText(String.valueOf(temp));
                        wrong_ans.setText(String.valueOf(wrongans));

                        no_data.setVisibility(View.GONE);
                        name_card.setVisibility(View.VISIBLE);
                        history_list.setVisibility(View.VISIBLE);
                        score_card.setVisibility(View.VISIBLE);
                        quiz_history_adapter = new Quiz_History_Adapter(Quiz_history_page.this, download_response);
                        history_list.setAdapter(quiz_history_adapter);
                        spotsDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                Log.d("Error ", t.getMessage());
                spotsDialog.dismiss();
            }
        });
    }

    //Load Data's InBetween two date's
    void LoadDataFromTo(String from_txt, String to_txt) {
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getquizresult(ApiUtil.GET_QUIZ_RESULT + "?uid=" + Controller.getUID() + "&type=1&from=" + from_txt + "&to=" + to_txt);
        call.enqueue(new Callback<DownloadResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.d("Result", response.body().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    download_response = response.body().getQuiz_History();
                    Log.d("size ", String.valueOf(download_response.get(0).getUser_ID()));
                    if (download_response.size() == 0) {
                        name_card.setVisibility(View.GONE);
                        history_list.setVisibility(View.GONE);
                        score_card.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        spotsDialog.dismiss();
                    } else if (response.body().equals("0")) {
                        name_card.setVisibility(View.GONE);
                        history_list.setVisibility(View.GONE);
                        score_card.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        spotsDialog.dismiss();
                    } else {
                        calander = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
                        String time = simpleDateFormat.format(calander.getTime());
                        Log.d("Time", time);
                        CURRENT_TIME = Integer.valueOf(time);
                        Log.d("Today Date", new TimeDate().getDateYMD());
                        total_score.setText("Total Completed Question's . " + download_response.size());
                        int temp = 0;
                        int today = 0;
                        for (int i = 0; i < download_response.size(); i++) {
                            if (String.valueOf(download_response.get(i).getScore()).equals("1")) {
                                if (new TimeDate().getDateYMD().equals(download_response.get(i).getCreate_Date())) {
                                    if (START_TIME <= CURRENT_TIME && LOCAL_TIME > CURRENT_TIME) {
                                        today = today + 1;
                                    } else {
                                        today = 0;
                                        temp = temp + 1;
                                    }
                                } else {
                                    temp = temp + 1;
                                    Log.d("Matched", temp + " / " + download_response.get(i).getScore());
                                }
                            } else {
                                if (new TimeDate().getDateYMD().equals(download_response.get(i).getCreate_Date())) {
                                    if (START_TIME <= CURRENT_TIME && LOCAL_TIME > CURRENT_TIME) {
                                        today = today + 1;
                                    } else {
                                        today = 0;
                                        temp = temp + 1;
                                    }
                                } else {
                                    temp = temp + 0;
                                    Log.d("Matched", temp + " / " + download_response.get(i).getScore());
                                }
                            }
                        }
                        int wrongans = download_response.size() - temp - today;
                        correct_ans.setText(String.valueOf(temp));
                        wrong_ans.setText(String.valueOf(wrongans));

                        no_data.setVisibility(View.GONE);
                        name_card.setVisibility(View.VISIBLE);
                        history_list.setVisibility(View.VISIBLE);
                        score_card.setVisibility(View.VISIBLE);
                        quiz_history_adapter = new Quiz_History_Adapter(Quiz_history_page.this, download_response);
                        history_list.setAdapter(quiz_history_adapter);
                        spotsDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                Log.d("Error ", t.getMessage());
                spotsDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_filter) {
            Dialog window = new Dialog(Quiz_history_page.this);
            window.setContentView(R.layout.window_filter);
            window.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.show();
            window.setCanceledOnTouchOutside(false);

            from_date = window.findViewById(R.id.filter_from_date_txt);
            to_date = window.findViewById(R.id.filter_to_date_txt);
            from_date_icon = window.findViewById(R.id.filter_from_date);
            to_date_icon = window.findViewById(R.id.filter_to_date);
            search_btn = window.findViewById(R.id.filter_search_btn);
            cancel_btn = window.findViewById(R.id.filter_cancel_btn);

            from_date_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Quiz_history_page.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
                                        from_date.setText(esdate);
                                    } else {
                                        from_date.setText(esdate);
                                    }
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });

            to_date_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Quiz_history_page.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
                                        to_date.setText(esdate);
                                    } else {
                                        to_date.setText(esdate);
                                    }
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });

            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spotsDialog.show();
                    LoadDataFromTo(from_date.getText().toString(), to_date.getText().toString());
                    window.dismiss();
                }
            });

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    window.dismiss();
                }
            });

            window.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    window.dismiss();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    //initialise components
    private void initialise() {
        spotsDialog = new SpotsDialog(this);
        spotsDialog.show();

        my_toolbar = findViewById(R.id.quiz_hs_menu);
        name_card = findViewById(R.id.quiz_hs_name_card);
        no_data = findViewById(R.id.quiz_hs_nodata);
        history_list = findViewById(R.id.quiz_hs_list);
        score_card = findViewById(R.id.quiz_hs_score_card);
        total_score = findViewById(R.id.quiz_hs_score_t_qstn);
        correct_ans = findViewById(R.id.quiz_hs_score_c_ans);
        wrong_ans = findViewById(R.id.quiz_hs_score_w_ans);
        download_btn = findViewById(R.id.quiz_hs_download);

        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        my_toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        no_data.setVisibility(View.GONE);

        history_list.setHasFixedSize(true);
        history_list.setLayoutManager(new LinearLayoutManager(Quiz_history_page.this));
    }

    //download pdf
    void downloadPDF() {

        spotsDialog.show();

        c1 = Calendar.getInstance();

        Document document = new Document(PageSize.A4);

        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        String name = "Quiz_result_" + timeStamp;

        String outpath = Environment.getExternalStorageDirectory() + "/StudentBazaar/"+name+".pdf";

        try {
            PdfWriter.getInstance(document, new FileOutputStream(outpath));
            document.open();
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
            document.add(new Paragraph(Element.ALIGN_CENTER, "Quiz Result", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK)));
            document.add(new Paragraph("  "));
            document.add(new Paragraph(Element.ALIGN_CENTER, "Name    : "+new Controller(Quiz_history_page.this).getusername(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK)));
            document.add(new Paragraph("  "));
            document.add(new Paragraph(Element.ALIGN_CENTER, "User ID : "+new Controller(Quiz_history_page.this).getUID(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK)));
            document.add(new Paragraph("  "));
            document.add(new Paragraph(Paragraph.ALIGN_RIGHT, "Date & Time    :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK)));
            document.add(new Paragraph("  "));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph("  "));

            PdfPTable pdfPTable = new PdfPTable(6);
            pdfPTable.getRowHeight(40);

            PdfPCell cell = new PdfPCell(new Phrase("S.No"));
            pdfPTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Question No"));
            pdfPTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Correct Answer"));
            pdfPTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Submit Answer"));
            pdfPTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Attended Date"));
            pdfPTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Validation"));
            pdfPTable.addCell(cell);

            for (int i = 0; i < download_response.size(); i++) {
                pdfPTable.setFooterRows(1);
                pdfPTable.addCell(String.valueOf(i + 1));
                pdfPTable.addCell(String.valueOf(download_response.get(i).getQuiz_ID()));
                pdfPTable.addCell(download_response.get(i).getCorrect_Answer());
                pdfPTable.addCell(download_response.get(i).getSubmit_Answer());
                pdfPTable.addCell(download_response.get(i).getCreate_Date());
                if (download_response.get(i).getScore() == 1) {
                    pdfPTable.addCell("Correct");
                } else {
                    pdfPTable.addCell("Wrong");
                }
                Log.d("S.No : ", String.valueOf(i+1));
                Log.d("Validation : ",String.valueOf(download_response.get(i).getScore()));
            }
            document.add(pdfPTable);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.close();
            Log.d("Complete " , "complete");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    spotsDialog.dismiss();
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(Quiz_history_page.this);
                    builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
                    builder.setCornerRadius(20);
                    builder.setTitle("Successful");
                    builder.setMessage("complete your download , Please open StudentsBazaar folder ");
                    builder.addButton("ok", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            },3000);
        } catch (DocumentException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}