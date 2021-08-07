package com.eventsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEditTeacherActivity extends AppCompatActivity {
    EditText etname, etemail, etphone, etpass;
    Button btnupdateteacher;
    TextView tvstudentlogin;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_teacher);
        getSupportActionBar().setTitle("Teacher Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etname =(EditText)findViewById(R.id.etname);
        etemail = (EditText)findViewById(R.id.etemail);
        etphone = (EditText)findViewById(R.id.etphone);
        etpass = (EditText)findViewById(R.id.etpass);

                etname.setText(getIntent().getStringExtra("name"));
                etemail.setText(getIntent().getStringExtra("email"));
                etphone.setText(getIntent().getStringExtra("phone"));
                etpass.setText(getIntent().getStringExtra("pass"));

        btnupdateteacher=(Button)findViewById(R.id.btnupdateteacher);
        btnupdateteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etname.getText().toString();
                String email=etemail.getText().toString();
                String phone=etphone.getText().toString();
                String pass=etpass.getText().toString();

                progress = new ProgressDialog(AdminEditTeacherActivity.this);
                progress.setMessage("updating please wait");
                progress.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.teacherupdateprofile(name,email,phone,pass);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progress.dismiss();
                        if (response.body().status.equals("true")) {
                            Toast.makeText(AdminEditTeacherActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AdminEditTeacherActivity.this, ManageTeachersActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(AdminEditTeacherActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(AdminEditTeacherActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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