package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;

public class MUActivity extends AppCompatActivity {

    CardView cardResults,cardQuestions;
    TextView tvTitle;
    Toolbar toolbar;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munive_page);

        cardResults = (CardView)findViewById(R.id.cardresult);
        cardQuestions = (CardView)findViewById(R.id.cardQuestions);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        tf = Typeface.createFromAsset(getAssets(),"caviar.ttf");
        tvTitle.setTypeface(tf);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.SRC_ATOP);
        }

        cardResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("url", "http://results.unom.ac.in/nov2019/");
                b.putString("title", "Results");
                b.putString("data", "Results-MU");
                Intent i = new Intent(MUActivity.this,WebActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        cardQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("url", "https://egovernance.unom.ac.in/qpregular/question_main.asp");
                b.putString("title", "Results-MU");
                b.putString("data", "Question papers");
                Intent i = new Intent(MUActivity.this,WebActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

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
        new Move_Show(MUActivity.this,HomeActivity.class);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_placement_menu, menu);
        menu.findItem(R.id.item1).setVisible(false);
        menu.findItem(R.id.item2).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.profile).setVisible(false);
        menu.findItem(R.id.shareitem).setVisible(false);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.shareitem:
                try {
                    new Monitor(this).sharetowhatsapp();
                } catch (Exception e) {

                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
