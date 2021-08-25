package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {

    CardView cdevents,cdstudents,cdteachers,cdcomments;
    Button btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnlogout=(Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        cdevents=(CardView)findViewById(R.id.cdevents);
        cdevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHomeActivity.this,AdminViewEventsActivity.class);
                startActivity(i);
            }
        });

        cdstudents=(CardView)findViewById(R.id.cdstudents);
        cdstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHomeActivity.this,ManageStudentsActivity.class);
                startActivity(i);
            }
        });

        cdteachers=(CardView)findViewById(R.id.cdteachers);
        cdteachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHomeActivity.this,ManageTeachersActivity.class);
                startActivity(i);
            }
        });
        cdcomments=(CardView)findViewById(R.id.cdcomments);
        cdcomments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminHomeActivity.this,AdminCommentsViewActivity.class);
                startActivity(i);
            }
        });

    }
}