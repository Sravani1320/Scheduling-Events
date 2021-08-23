package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.eventsapp.adapters.AdminViewEventsAdapter;
import com.eventsapp.adapters.UpcomingEventsAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.EventsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingEventsActivity extends AppCompatActivity {
    List<EventsPojo> eventsPojo;
    ListView events_list;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_events);
        getSupportActionBar().setTitle("Upcoming Events List");

        events_list = (ListView) findViewById(R.id.events_list);
        eventsPojo = new ArrayList<>();
        getEvents();
    }

    public void getEvents() {
        loading = new ProgressDialog(UpComingEventsActivity.this);
        loading.setMessage("Loading");
        loading.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<EventsPojo>> call= apiService.upcoming();
        call.enqueue(new Callback<List<EventsPojo>>() {
            @Override
            public void onResponse(Call<List<EventsPojo>> call, Response<List<EventsPojo>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(UpComingEventsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    eventsPojo = response.body();
                    events_list.setAdapter(new UpcomingEventsAdapter(eventsPojo, UpComingEventsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<EventsPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(UpComingEventsActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}