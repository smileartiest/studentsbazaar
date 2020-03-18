package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etPhone, etPassword;
    Button btSubmit;
    TextView tvRegister, tvVisitor, tvforgot;
    String stPhone, stPassword;
    SpotsDialog spotsDialog;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Controller(this);
        etPhone = findViewById(R.id.input_phone);
        etPassword = findViewById(R.id.uiedonpass);
        btSubmit = findViewById(R.id.id_Submit);
        tvRegister = findViewById(R.id.id_Register);
        tvVisitor = findViewById(R.id.id_Visitor);
        tvforgot = findViewById(R.id.uitvforgot);
        spotsDialog = new SpotsDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etPhone.requestFocus();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayforgotdialog();
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
                                Move_Show.showToast("Incorrect Username or password");
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
        new Move_Show(MainActivity.this,HomeActivity.class);
        finish();
    }

    private void displayforgotdialog() {
        Dialog d = new Dialog(MainActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.forgot_password);
        d.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        EditText emailphone = (EditText) d.findViewById(R.id.uiedforgotmailphone);
        EditText password = (EditText) d.findViewById(R.id.uiedpassword);
        EditText confirmpass = (EditText) d.findViewById(R.id.uiedonpass);
        Button changebtn = (Button) d.findViewById(R.id.uibtnconfrim);
        Button cancelbtn =(Button)d.findViewById(R.id.uibtnupdatecancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(confirmpass.getText().toString())) {
                    spotsDialog.show();
                    Call<String> call = ApiUtil.getServiceClass().updatepassword(Controller.getUID(), emailphone.getText().toString().trim(), confirmpass.getText().toString().trim());
                   call.enqueue(new Callback<String>() {
                       @Override
                       public void onResponse(Call<String> call, Response<String> response) {
                           Log.d("Logres",Controller.getUID());
                           spotsDialog.dismiss();
                           if (response.body().equals("1")) {
                               Move_Show.showToast("Password Updated Success");
                               d.dismiss();
                               new Move_Show(MainActivity.this, HomeActivity.class);
                               Controller.addprefer(Controller.REG);
                               finish();
                           } else if (response.body().equals("2")){
                               Move_Show.showToast("Incorrect Mobile no or mail id");
                           }else {
                               Move_Show.showToast("Invalid user details");
                           }


                       }

                       @Override
                       public void onFailure(Call<String> call, Throwable t) {

                       }
                   });
                } else {
                    Move_Show.showToast("Please check your password");
                }
            }
        });
        d.show();

    }

}
