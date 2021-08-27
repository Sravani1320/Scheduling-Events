package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    Button btnadminlogin,btnteacherlogin,btnstudentlogin;
    TextView tvEng,tvFr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        btnstudentlogin=(Button)findViewById(R.id.btnstudentlogin);
        btnstudentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,StudentLoginActivity.class);
                startActivity(i);
            }
        });

        btnteacherlogin=(Button)findViewById(R.id.btnteacherlogin);
        btnteacherlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,TeacherLoginActivity.class);
                startActivity(i);
            }
        });

        btnadminlogin=(Button)findViewById(R.id.btnadminlogin);
        btnadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AdminLoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void restartActivity() {
        Intent intent = getIntent();
        startActivity(intent);
        finish();
    }
}