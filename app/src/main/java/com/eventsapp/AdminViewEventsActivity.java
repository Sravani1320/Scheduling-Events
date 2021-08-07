package com.eventsapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eventsapp.adapters.AdminViewEventsAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.EventsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewEventsActivity extends AppCompatActivity {

    List<EventsPojo> eventsPojo;
    ListView events_list;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_events);
        getSupportActionBar().setTitle("Events List");

        events_list = (ListView) findViewById(R.id.events_list);
        eventsPojo = new ArrayList<>();
        getEvents();
    }

    public void getEvents() {
        loading = new ProgressDialog(AdminViewEventsActivity.this);
        loading.setMessage("Loading");
        loading.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
         Call<List<EventsPojo>> call= apiService.getadminevents();
        call.enqueue(new Callback<List<EventsPojo>>() {
            @Override
            public void onResponse(Call<List<EventsPojo>> call, Response<List<EventsPojo>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(AdminViewEventsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    eventsPojo = response.body();
                    events_list.setAdapter(new AdminViewEventsAdapter(eventsPojo, AdminViewEventsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<EventsPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AdminViewEventsActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}