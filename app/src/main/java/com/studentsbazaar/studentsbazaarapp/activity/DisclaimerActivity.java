package com.studentsbazaar.studentsbazaarapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class DisclaimerActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disclaimer_page);
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
        new Move_Show(DisclaimerActivity.this,HomeActivity.class);
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
