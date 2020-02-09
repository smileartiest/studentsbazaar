package com.studentsbazaar.studentsbazaarapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.studentsbazaar.studentsbazaarapp.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class DisclaimerActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        textView=(TextView)findViewById(R.id.textView40);
        textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
