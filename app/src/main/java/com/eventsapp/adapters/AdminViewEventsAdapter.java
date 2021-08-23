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
import com.eventsapp.R;
import com.eventsapp.StudentHomeActivity;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.EventsPojo;
import com.eventsapp.model.ResponseData;
import com.eventsapp.model.StudentPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewEventsAdapter extends BaseAdapter {
    List<EventsPojo> event;
    Context con;
    String url= "http://paytracker.ca/events/";
    ProgressDialog progressDialog;

    public AdminViewEventsAdapter(List<EventsPojo> event, Context con) {
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
        View events = obj.inflate(R.layout.child_adminevent, null);

        ImageView image=(ImageView)events.findViewById(R.id.image);
        Glide.with(con).load(url+event.get(pos).getImage()).into(image);

        TextView tveventname = (TextView) events.findViewById(R.id.tveventname);
        tveventname.setText(event.get(pos).getName());

        ImageView imgedit = (ImageView) events.findViewById(R.id.imgedit);
        imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(con, EditEventActivity.class);
                intent.putExtra("eid",event.get(pos).getEid());
                intent.putExtra("category",event.get(pos).getCategory());
                intent.putExtra("name",event.get(pos).getName());
                intent.putExtra("dat",event.get(pos).getDat());
                intent.putExtra("venue",event.get(pos).getVenue());
                intent.putExtra("description",event.get(pos).getDescription());
                intent.putExtra("image",event.get(pos).getImage());
                con.startActivity(intent);
            }
        });

        ImageView imgdelete = (ImageView) events.findViewById(R.id.imgdelete);
        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Deleting Data");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.deleteevent(event.get(pos).getEid());
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progressDialog.dismiss();
                        if(response.body()==null){
                            Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent=new Intent(con, AdminHomeActivity.class);
                            con.startActivity(intent);
                            Toast.makeText(con," Deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(con, "Server went wrong Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return events;
    }

}