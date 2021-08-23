package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.eventsapp.adapters.AdminCommentAdapter;
import com.eventsapp.adapters.StudentsAdapter;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.CommentsPojo;
import com.eventsapp.model.StudentPojo;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCommentsViewActivity extends AppCompatActivity {

    List<CommentsPojo> comment;
    Button addstudent;
    ListView comments_list;
    ProgressDialog progress;
    EditText et_search;
    AdminCommentAdapter studentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_comments_view);
        getSupportActionBar().setTitle("View Comments");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        comments_list=(ListView)findViewById(R.id.comments_list);

        getAllComments();

    }

    public void getAllComments() {
        progress = new ProgressDialog(AdminCommentsViewActivity.this);
        progress.setMessage("Comments Data Loading....");
        progress.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<CommentsPojo>> call = service.getadmincomments();
        call.enqueue(new Callback<List<CommentsPojo>>() {
            @Override
            public void onResponse(Call<List<CommentsPojo>> call, Response<List<CommentsPojo>> response) {
                progress.dismiss();
                if (response.body() == null) {
                    Toast.makeText(AdminCommentsViewActivity.this, "No Students found", Toast.LENGTH_SHORT).show();
                } else {
                    comment = response.body();
                    comments_list.setAdapter(new AdminCommentAdapter(comment, AdminCommentsViewActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<CommentsPojo>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(AdminCommentsViewActivity.this, "Please contact admin !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}