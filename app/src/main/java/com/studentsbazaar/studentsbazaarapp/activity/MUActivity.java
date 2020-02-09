package com.studentsbazaar.studentsbazaarapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.studentsbazaar.studentsbazaarapp.R;

public class MUActivity extends AppCompatActivity {

    CardView cardResults,cardQuestions;
    TextView tvTitle;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mu);

        cardResults = (CardView)findViewById(R.id.cardresult);
        cardQuestions = (CardView)findViewById(R.id.cardQuestions);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        tf = Typeface.createFromAsset(getAssets(),"caviar.ttf");
        tvTitle.setTypeface(tf);

        cardResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("url", "http://results.unom.ac.in/nov2019/");
                b.putString("title", "RESULTS");
                b.putString("data", "RESULTS-MU");
                Intent i = new Intent(MUActivity.this,WebActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        cardQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("url", "http://egovernance.unom.ac.in/qpregular/question_main.asp");
                b.putString("title", "RESULTS-MU");
                b.putString("data", "QUESTION PAPERS");
                Intent i = new Intent(MUActivity.this,WebActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }


}
