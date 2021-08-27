package com.eventsapp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eventsapp.AdminEditStudentActivity;
import com.eventsapp.AdminHomeActivity;
import com.eventsapp.EditCommentActivity;
import com.eventsapp.EditEventActivity;
import com.eventsapp.R;
import com.eventsapp.Utils;
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
    SharedPreferences sharedPreferences;
    String email;

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

        sharedPreferences = con.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("user_name", "def-val");


        TextView tvname = (TextView) c.findViewById(R.id.tvname);
        tvname.setText(comment.get(pos).getName());

        TextView tvmsg = (TextView) c.findViewById(R.id.tvmsg);
        tvmsg.setText(comment.get(pos).getMsg());


        ImageView editcomment=(ImageView)c.findViewById(R.id.editcomment);

        if(email.equals(comment.get(pos).getEmail()))
        {
            editcomment.setVisibility(View.VISIBLE);
        }
        editcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(con, EditCommentActivity.class);
                intent.putExtra("cid",comment.get(pos).getCid());
                intent.putExtra("name",comment.get(pos).getName());
                intent.putExtra("email",comment.get(pos).getEmail());
                intent.putExtra("msg",comment.get(pos).getMsg());

                con.startActivity(intent);
            }
        });


        return c;
    }
}