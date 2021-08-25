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

import com.eventsapp.AdminEditStudentActivity;
import com.eventsapp.AdminHomeActivity;
import com.eventsapp.R;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.CommentsPojo;
import com.eventsapp.model.ResponseData;
import com.eventsapp.model.StudentPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsAdapter extends BaseAdapter {
    List<CommentsPojo> comment, search;
    Context con;
    ProgressDialog progressDialog;

    public CommentsAdapter(List<CommentsPojo> comment, Context con) {
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
        View c = obj.inflate(R.layout.child_comment, null);

        TextView tvname = (TextView) c.findViewById(R.id.tvname);
        tvname.setText(comment.get(pos).getName());

        TextView tvmsg = (TextView) c.findViewById(R.id.tvmsg);
        tvmsg.setText(comment.get(pos).getMsg());



        return c;
    }
}