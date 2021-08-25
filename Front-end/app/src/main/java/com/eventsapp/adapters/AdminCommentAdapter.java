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

import com.eventsapp.AdminHomeActivity;
import com.eventsapp.R;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.CommentsPojo;
import com.eventsapp.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCommentAdapter extends BaseAdapter {
    List<CommentsPojo> comment, search;
    Context con;
    ProgressDialog progressDialog;

    public AdminCommentAdapter(List<CommentsPojo> comment, Context con) {
        this.comment = comment;
        this.con = con;
    }

    @Override
    public int getCount() {
        return comment.size();
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
        View c = obj.inflate(R.layout.child_admincomment, null);

        TextView tvname = (TextView) c.findViewById(R.id.tvname);
        tvname.setText(comment.get(pos).getName());

        TextView tvmsg = (TextView) c.findViewById(R.id.tvmsg);
        tvmsg.setText(comment.get(pos).getMsg());


        ImageView deletecomment = (ImageView) c.findViewById(R.id.deletecomment);
        deletecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Deleting Data");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.deletecomment(comment.get(pos).getCid());
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

        return c;
    }
}