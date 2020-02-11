package com.studentsbazaar.studentsbazaarapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    EditText etPhone, etPassword;
    Button btSubmit;
    TextView tvRegister, tvVisitor;
    String stPhone, stPassword;
    SpotsDialog spotsDialog;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Controller(this);
        etPhone = findViewById(R.id.input_phone);
        etPassword = findViewById(R.id.input_password);
        btSubmit = findViewById(R.id.id_Submit);
        tvRegister = findViewById(R.id.id_Register);
        tvVisitor = findViewById(R.id.id_Visitor);
        spotsDialog = new SpotsDialog(this);
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

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stPhone = etPhone.getText().toString();
                stPassword = etPassword.getText().toString();

                if (stPhone.equals("9791004050") && stPassword.equals("uniqadmin")) {
                    Controller.addprefer(Controller.ADMIN);
                    spotsDialog.dismiss();
                    new Move_Show(MainActivity.this, HomeActivity.class);
                    finish();
                } else if (stPhone.equals("9360478319") && stPassword.equals("infozub")) {
                    spotsDialog.dismiss();
                    Controller.addprefer(Controller.INFOZUB);
                    new Move_Show(MainActivity.this, HomeActivity.class);
                    finish();
                } else if (stPhone.equals("7094120481") && stPassword.equals("memeuniqadmin")) {
                    spotsDialog.dismiss();
                    Controller.addprefer(Controller.MEMEACCEPT);
                    new Move_Show(MainActivity.this, HomeActivity.class);
                    finish();
                } else if (etPhone.getText().length() == 0 && etPassword.getText().length() == 0) {
                    Move_Show.showToast("All fields are Mandatory");
                } else {
                    spotsDialog.show();
                    Call<String> call = ApiUtil.getServiceClass().getLoginDetails(stPhone, stPassword);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                            if (response.body().equals("1")) {
                                spotsDialog.dismiss();
                                Controller.addprefer(Controller.REG);
                                new Move_Show(MainActivity.this, HomeActivity.class);
                                finish();
                                Move_Show.showToast("Login Success");
                            } else {
                                spotsDialog.dismiss();
                                Move_Show.showToast("Login Failed");
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }


            }

        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Move_Show(MainActivity.this, SignUp.class);
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
        finish();
    }
}
