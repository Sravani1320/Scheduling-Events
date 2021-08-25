package com.eventsapp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eventsapp.AdminHomeActivity;
import com.eventsapp.EditEventActivity;
import com.eventsapp.EventDetailsActivity;
import com.eventsapp.R;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.EventsPojo;
import com.eventsapp.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingEventsAdapter extends BaseAdapter {
    List<EventsPojo> event;
    Context con;
    String url= "http://paytracker.ca/events/";
    ProgressDialog progressDialog;

    public UpcomingEventsAdapter(List<EventsPojo> event, Context con) {
        this.event = event;
        this.con = con;
    }

    @Override
    public int getCount() {
        return event.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View events = obj.inflate(R.layout.child_upcoming, null);

        ImageView image=(ImageView)events.findViewById(R.id.image);
        Glide.with(con).load(url+event.get(pos).getImage()).into(image);

        TextView tveventname = (TextView) events.findViewById(R.id.tveventname);
        tveventname.setText(event.get(pos).getName());

        TextView tvmoredetails = (TextView) events.findViewById(R.id.tvmoredetails);
        tvmoredetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(con, EventDetailsActivity.class);
                intent.putExtra("eid",event.get(pos).getEid());
                intent.putExtra("name",event.get(pos).getName());
                intent.putExtra("category",event.get(pos).getCategory());
                intent.putExtra("email",event.get(pos).getEmail());
                intent.putExtra("venue",event.get(pos).getVenue());
                intent.putExtra("dat",event.get(pos).getDat());
                intent.putExtra("description",event.get(pos).getDescription());
                intent.putExtra("image",event.get(pos).getImage());
                con.startActivity(intent);
            }
        });



        return events;
    }

}