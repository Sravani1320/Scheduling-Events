package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StudentLoginActivity extends AppCompatActivity {

    EditText etemail, etpass;
    Button btnlogin;
    TextView tvforgotpass, tvsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        btnlogin = findViewById(R.id.btnlogin);
        tvforgotpass = findViewById(R.id.tvforgotpass);
        tvsignup = findViewById(R.id.tvsignup);


    }
}