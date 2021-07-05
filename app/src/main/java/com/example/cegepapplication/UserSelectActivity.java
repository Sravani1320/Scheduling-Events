package com.example.cegepapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSelectActivity extends AppCompatActivity implements View.OnClickListener {
    Button student, teacher, admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

        student = (Button) findViewById(R.id.student);
        teacher = (Button) findViewById(R.id.teacher);
        admin = (Button) findViewById(R.id.admin);

        student.setOnClickListener(this);
        teacher.setOnClickListener(this);
        admin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == student) {
            startActivity(new Intent(UserSelectActivity.this, StudentLoginActivity.class));
        } else if (v == teacher) {
            startActivity(new Intent(UserSelectActivity.this, TeacherLoginActivity.class));

        } else if (v == admin) {
            startActivity(new Intent(UserSelectActivity.this, AdminLoginActivity.class));

        }

    }
}