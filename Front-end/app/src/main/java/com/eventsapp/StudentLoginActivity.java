package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentLoginActivity extends BaseActivity {
    ProgressDialog progress;
    EditText etemail, etpass;
    Button btnstudentlogin;
    TextView tvforgotpass, tvsignup;
    TextView tvEng,tvFr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        btnstudentlogin = findViewById(R.id.btnstudentlogin);
        tvforgotpass = findViewById(R.id.tvforgotpass);
        tvforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s=new Intent(StudentLoginActivity.this,StudentForgotPasswordActivity.class);
                startActivity(s);

            }
        });

        tvEng = (TextView) findViewById(R.id.tvEng);
        tvFr = (TextView) findViewById(R.id.tvFr);
        tvEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                restartActivity();
            }
        });
        tvFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("fr");
                restartActivity();
            }
        });
       // tvsignup = findViewById(R.id.tvsignup);


        btnstudentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etemail.getText().toString().isEmpty()){
                    Toast.makeText(StudentLoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etpass.getText().toString().isEmpty()){
                    Toast.makeText(StudentLoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    //progreess bar loading
                    progress= new ProgressDialog(StudentLoginActivity.this);
                    progress.setTitle("Please wait,Data is being submit...");
                    progress.show();
                    // api call code


                    ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
                    Call<ResponseData> call = apiService.studentlogin(etemail.getText().toString(),etpass.getText().toString());



                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            progress.dismiss();
                            if (response.body().status.equals("true")) {
                                SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor et = sharedPreferences.edit();
                                et.putString("user_name", etemail.getText().toString());
                                et.putString("type", "student");
                                et.commit();
                                startActivity(new Intent(StudentLoginActivity.this, StudentHomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(StudentLoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            progress.dismiss();
                            Toast.makeText(StudentLoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void restartActivity() {
        Intent intent = getIntent();
        startActivity(intent);
        finish();
    }
}