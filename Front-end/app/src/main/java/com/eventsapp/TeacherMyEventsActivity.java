package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.eventsapp.adapters.StudentMyEventAdapter;
import com.eventsapp.adapters.TeacherMyEventsAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.EventsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherMyEventsActivity extends AppCompatActivity {

    List<EventsPojo> eventsPojo;
    ListView events_list;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_my_events);
        getSupportActionBar().setTitle("Events List");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        events_list = (ListView) findViewById(R.id.events_list);
        eventsPojo = new ArrayList<>();
        getEvents();
    }

    public void getEvents() {
        loading = new ProgressDialog(TeacherMyEventsActivity.this);
        loading.setMessage("Loading");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");

        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<EventsPojo>> call= apiService.getmystudentevents(email);


        call.enqueue(new Callback<List<EventsPojo>>() {
            @Override
            public void onResponse(Call<List<EventsPojo>> call, Response<List<EventsPojo>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(TeacherMyEventsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    eventsPojo = response.body();
                    events_list.setAdapter(new TeacherMyEventsAdapter(eventsPojo, TeacherMyEventsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<EventsPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(TeacherMyEventsActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
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