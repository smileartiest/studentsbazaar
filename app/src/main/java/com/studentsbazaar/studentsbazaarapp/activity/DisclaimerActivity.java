package com.studentsbazaar.studentsbazaarapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.studentsbazaar.studentsbazaarapp.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class DisclaimerActivity extends AppCompatActivity {

    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        textView=(TextView)findViewById(R.id.textView40);
        textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }
}
