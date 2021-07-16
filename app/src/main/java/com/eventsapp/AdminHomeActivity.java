package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHomeActivity extends AppCompatActivity {

    CardView cdevents,cdstudents,cdteachers,cdcomments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        cdevents=(CardView)findViewById(R.id.cdevents);
        cdevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

            }
        });

    }
}