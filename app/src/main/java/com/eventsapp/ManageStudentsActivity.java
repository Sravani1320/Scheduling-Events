package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.eventsapp.adapters.StudentsAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.StudentPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageStudentsActivity extends AppCompatActivity {

    List<StudentPojo> student;
    Button addstudent;
    ListView students_list;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);
        getSupportActionBar().setTitle("Manage Students");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        students_list=(ListView)findViewById(R.id.students_list);
        addstudent=(Button)findViewById(R.id.addstudent);
        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageStudentsActivity.this,StudentRegistrationActivity.class);
                startActivity(i);
            }
        });

        getAllStudents();
    }

    public void getAllStudents() {
        progress = new ProgressDialog(ManageStudentsActivity.this);
        progress.setMessage("Students Data Loading....");
        progress.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<StudentPojo>> call = service.getstudents();
        call.enqueue(new Callback<List<StudentPojo>>() {
            @Override
            public void onResponse(Call<List<StudentPojo>> call, Response<List<StudentPojo>> response) {
                progress.dismiss();
                 if (response.body() == null) {
                    Toast.makeText(ManageStudentsActivity.this, "No Students found", Toast.LENGTH_SHORT).show();
                } else {
                     student = response.body();
                     students_list.setAdapter(new StudentsAdapter(student, ManageStudentsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<StudentPojo>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ManageStudentsActivity.this, "Please contact admin !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}