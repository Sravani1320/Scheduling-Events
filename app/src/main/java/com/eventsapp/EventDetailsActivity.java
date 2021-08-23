package com.eventsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eventsapp.adapters.CommentsAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.CommentsPojo;
import com.eventsapp.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends AppCompatActivity {

    TextView tvdescription,tveventname,tvdate,tvvenue,tvjoinevent,tvcommentevent,tvshareevent;
    ImageView eventimage;
    String url= "http://paytracker.ca/events/";
    String useremail;
    TextView submit,eventname,tcount,tvpaticipants;
    ProgressDialog loading;
    SharedPreferences sharedPreferences;
    ListView events_comments;
    List<CommentsPojo> cpojo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getSupportActionBar().setTitle("Event Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        useremail = sharedPreferences.getString("user_name", "def-val");
        tveventname=(TextView)findViewById(R.id.tveventname);
        tvdate=(TextView)findViewById(R.id.tvdate);
        tvvenue=(TextView)findViewById(R.id.tvvenue);
        tvdescription=(TextView)findViewById(R.id.tvdescription);
        tvcommentevent=(TextView)findViewById(R.id.tvcommentevent);
        tvshareevent=(TextView)findViewById(R.id.tvshareevent);
        tvshareevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent =new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Greetings From Event App\n"+"---------------------------------------\n"+"Event Name : "+tveventname.getText().toString()+"\n"+"Venue : "+tvvenue.getText().toString()+"\n"+"Date : "+tvdate.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
            }
        });
        tcount=(TextView)findViewById(R.id.tcount);

        events_comments=(ListView)findViewById(R.id.events_comments);
        cpojo = new ArrayList<>();
        getComments(getIntent().getStringExtra("eid"));
        tvpaticipants=(TextView)findViewById(R.id.tvpaticipants);
        tvpaticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetailsActivity.this, EventPaticipantsActivity.class);
                intent.putExtra("eid",getIntent().getStringExtra("eid"));
                startActivity(intent);
            }
        });

        getPeopleCount(getIntent().getStringExtra("eid"));
        tvcommentevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetailsActivity.this, CommentEventActivity.class);
                intent.putExtra("eid",getIntent().getStringExtra("eid"));
                intent.putExtra("hname",getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });

        tvjoinevent=(TextView)findViewById(R.id.tvjoinevent);
        tvjoinevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = useremail;
                String eid = getIntent().getStringExtra("eid");
                loading = new ProgressDialog(EventDetailsActivity.this);
                loading.setMessage("Processing");
                loading.show();

                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.joinevent(email,eid);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        loading.dismiss();
                        if (response.body().status.equals("true")) {
                            Toast.makeText(EventDetailsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
//                                Intent intent=new Intent(CommentEventActivity.this, ViewEventsActivity.class);
//                                startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(EventDetailsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(EventDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });



            }
        });

        eventimage=(ImageView)findViewById(R.id.eventimage);
        Glide.with(getApplicationContext()).load(url+getIntent().getStringExtra("image")).into(eventimage);

        tveventname.setText(getIntent().getStringExtra("name"));
        tvdate.setText("Date : "+getIntent().getStringExtra("dat"));
        tvvenue.setText("Venue : "+getIntent().getStringExtra("venue"));
        tvdescription.setText(getIntent().getStringExtra("description"));
    }


    public void getPeopleCount(final String id)
    {
        loading= new ProgressDialog(EventDetailsActivity.this);
        loading.setTitle("Please wait,Data is being submit...");
        loading.show();
        // api call code
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.getcount(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                loading.dismiss();
                if (response.body().status.equals("true")) {
                    tcount.setText(response.body().message);
                } else {
                    tcount.setText(response.body().message);
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(EventDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getComments(final String eid) {
        loading = new ProgressDialog(EventDetailsActivity.this);
        loading.setMessage("Loading....");
       // loading.show();
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<CommentsPojo>> call = service.getcomments(eid);
        call.enqueue(new Callback<List<CommentsPojo>>() {
            @Override
            public void onResponse(Call<List<CommentsPojo>> call, Response<List<CommentsPojo>> response) {
                loading.dismiss();
                // Toast.makeText(HotelDetailsActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    loading.dismiss();
                    Toast.makeText(EventDetailsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    loading.dismiss();
                    cpojo = response.body();
                    events_comments.setAdapter(new CommentsAdapter(cpojo, EventDetailsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<CommentsPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(EventDetailsActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
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