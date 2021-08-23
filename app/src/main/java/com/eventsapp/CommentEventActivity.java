package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentEventActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText etname,etmessage;
    String useremail;
    TextView submit,eventname;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_event);

        getSupportActionBar().setTitle("Write Comment");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        useremail = sharedPreferences.getString("user_name", "def-val");
        eventname=(TextView)findViewById(R.id.eventname);
        eventname.setText(getIntent().getStringExtra("name"));
        etname=(EditText) findViewById(R.id.etname);
        etmessage=(EditText)findViewById(R.id.etmessage);
        submit=(TextView) findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etname.getText().toString().isEmpty()){
                    Toast.makeText(CommentEventActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etmessage.getText().toString().isEmpty()){
                    Toast.makeText(CommentEventActivity.this, "Please Enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    String name = etname.getText().toString();
                    String msg = etmessage.getText().toString();
                    String email = useremail;
                    String eid = getIntent().getStringExtra("eid");
                    loading = new ProgressDialog(CommentEventActivity.this);
                    loading.setMessage("Processing");
                    loading.show();

                    ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                    Call<ResponseData> call = service.comment(name,msg,email,eid);
                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            loading.dismiss();
                            if (response.body().status.equals("true")) {
                                Toast.makeText(CommentEventActivity.this, response.body().message, Toast.LENGTH_LONG).show();
//                                Intent intent=new Intent(CommentEventActivity.this, ViewEventsActivity.class);
//                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(CommentEventActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            loading.dismiss();
                            Toast.makeText(CommentEventActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
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