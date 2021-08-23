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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eventsapp.AdminHomeActivity;
import com.eventsapp.EditEventActivity;
import com.eventsapp.EventDetailsActivity;
import com.eventsapp.R;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.EventsPojo;
import com.eventsapp.model.ResponseData;
import com.eventsapp.model.StudentPojo;
import com.eventsapp.model.TeacherPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewEventsAdapter extends RecyclerView.Adapter<ViewEventsAdapter.MyViewHolder> {

    Context context;
    List<EventsPojo> list,search;
    String url= "http://paytracker.ca/events/";
    public ViewEventsAdapter(Context context, List<EventsPojo> list) {
        this.context = context;
        this.list = list;
        this.search=list;
        this.list = new ArrayList<EventsPojo>();
        this.list.addAll(list);
    }

    @NonNull
    @Override
    public ViewEventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.child_event,parent,false);
        return new ViewEventsAdapter.MyViewHolder(v);
    }
    String ema;
    @Override
    public void onBindViewHolder(@NonNull ViewEventsAdapter.MyViewHolder holder, int position) {

        holder.tveventname.setText(list.get(position).getName());
        Glide.with(context)
                .load(url+list.get(position).getImage())
                .into(holder.ImgV);


        holder.cdeventdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("eid",list.get(position).getEid());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("category",list.get(position).getCategory());
                intent.putExtra("email",list.get(position).getEmail());
                intent.putExtra("venue",list.get(position).getVenue());
                intent.putExtra("dat",list.get(position).getDat());
                intent.putExtra("description",list.get(position).getDescription());
                intent.putExtra("image",list.get(position).getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tveventname;
        ImageView ImgV;
        CardView cdeventdetails;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgV = itemView.findViewById(R.id.eventimage);
            tveventname=itemView.findViewById(R.id.tveventname);
            cdeventdetails=itemView.findViewById(R.id.cdeventdetails);
        }
    }


    public void searchEvent(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(search);
        } else {
            for (EventsPojo s : search) {
                if (s.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }
}