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
import com.eventsapp.adapters.TeachersAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.StudentPojo;
import com.eventsapp.model.TeacherPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageTeachersActivity extends AppCompatActivity {

    List<TeacherPojo> teacher;
    Button addteacher;
    ListView teachers_list;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_teachers);
        getSupportActionBar().setTitle("Manage Teachers");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        teachers_list=(ListView)findViewById(R.id.teachers_list);
        addteacher=(Button)findViewById(R.id.addteacher);
        addteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ManageTeachersActivity.this,TeacherRegistrationActivity.class);
                startActivity(i);
            }
        });

        getAllTeachers();
    }

    public void getAllTeachers() {
        progress = new ProgressDialog(ManageTeachersActivity.this);
        progress.setMessage("Teachers Data Loading....");
        progress.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<TeacherPojo>> call = service.getteachers();
        call.enqueue(new Callback<List<TeacherPojo>>() {
            @Override
            public void onResponse(Call<List<TeacherPojo>> call, Response<List<TeacherPojo>> response) {
                progress.dismiss();
                if (response.body() == null) {
                    Toast.makeText(ManageTeachersActivity.this, "No Teachers found", Toast.LENGTH_SHORT).show();
                } else {
                    teacher = response.body();
                    teachers_list.setAdapter(new TeachersAdapter(teacher, ManageTeachersActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<TeacherPojo>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ManageTeachersActivity.this, "Please contact admin !", Toast.LENGTH_SHORT).show();
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