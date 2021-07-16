package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnadminlogin,btnteacherlogin,btnstudentlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}