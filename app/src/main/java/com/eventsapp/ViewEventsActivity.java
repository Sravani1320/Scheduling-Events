package com.eventsapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eventsapp.adapters.StudentsAdapter;
import com.eventsapp.adapters.ViewEventsAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.EventsPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewEventsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewEventsAdapter event;
    List<EventsPojo> list;
    ProgressDialog loading;
    EditText et_searchevent;

    String text;
    StudentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        getSupportActionBar().setTitle("Events");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerEvents);


        et_searchevent=(EditText)findViewById(R.id.et_searchevent);


        et_searchevent.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                text = et_searchevent.getText().toString().toLowerCase(Locale.getDefault());
                event.searchEvent(text);
              //  Toast.makeText(ViewEventsActivity.this, text, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter

        loading = new ProgressDialog(ViewEventsActivity.this);
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
                    Toast.makeText(ViewEventsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    list = response.body();
                  //  events_list.setAdapter(new AdminViewEventsAdapter(eventsPojo, AdminViewEventsActivity.this));

                    event=new ViewEventsAdapter(ViewEventsActivity.this,list);
                    recyclerView.setAdapter(event);


                }
            }
            @Override
            public void onFailure(Call<List<EventsPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(ViewEventsActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}