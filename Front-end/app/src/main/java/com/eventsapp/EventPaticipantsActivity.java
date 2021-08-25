package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eventsapp.adapters.PaticipantsAdapter;
import com.eventsapp.adapters.StudentsAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.StudentPojo;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventPaticipantsActivity extends AppCompatActivity {

    List<StudentPojo> student;
    Button addstudent;
    ListView paticipants_list;
    ProgressDialog progress;
    EditText et_search;
    PaticipantsAdapter studentsAdapter;
    TextView tvpaticipants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_paticipants);
        getSupportActionBar().setTitle("Paticipants");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        paticipants_list=(ListView)findViewById(R.id.paticipants_list);
        getAllPaticipants(getIntent().getStringExtra("eid"));


    }

    public void getAllPaticipants(final String eid) {
        progress = new ProgressDialog(EventPaticipantsActivity.this);
        progress.setMessage("Data Loading....");
        progress.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<StudentPojo>> call = service.getpaticipants(eid);
        call.enqueue(new Callback<List<StudentPojo>>() {
            @Override
            public void onResponse(Call<List<StudentPojo>> call, Response<List<StudentPojo>> response) {
                progress.dismiss();
                if (response.body() == null) {
                    Toast.makeText(EventPaticipantsActivity.this, "No Students found", Toast.LENGTH_SHORT).show();
                } else {
                    student = response.body();
                    paticipants_list.setAdapter(new PaticipantsAdapter(student, EventPaticipantsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<StudentPojo>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(EventPaticipantsActivity.this, "Please contact admin !", Toast.LENGTH_SHORT).show();
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