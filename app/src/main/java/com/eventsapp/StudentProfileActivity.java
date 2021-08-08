package com.eventsapp;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;
import com.eventsapp.model.StudentPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProfileActivity extends AppCompatActivity {

    EditText etname, etemail, etphone, etpass;
    Button btnupdatestudent;
    TextView tvstudentlogin;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etname =(EditText)findViewById(R.id.etname);
        etemail = (EditText)findViewById(R.id.etemail);
        etphone = (EditText)findViewById(R.id.etphone);
        etpass = (EditText)findViewById(R.id.etpass);

        progress = new ProgressDialog(StudentProfileActivity.this);
        progress.setMessage("Loading please wait");
        progress.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<StudentPojo>> reg= service.studentprofile(email);
        reg.enqueue(new Callback<List<StudentPojo>>() {
            @Override
            public void onResponse(Call<List<StudentPojo>> call, Response<List<StudentPojo>> response) {
                progress.dismiss();
                etname.setText(response.body().get(0).getName());
                etemail.setText(response.body().get(0).getEmail());
                etphone.setText(response.body().get(0).getPhone());
                etpass.setText(response.body().get(0).getPass());

            }
            @Override
            public void onFailure(Call<List<StudentPojo>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(StudentProfileActivity.this,"fail",Toast.LENGTH_LONG).show();
            }
        });

        btnupdatestudent=(Button)findViewById(R.id.btnupdatestudent);
        btnupdatestudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etname.getText().toString();
                String email=etemail.getText().toString();
                String phone=etphone.getText().toString();
                String pass=etpass.getText().toString();

                progress = new ProgressDialog(StudentProfileActivity.this);
                progress.setMessage("updating please wait");
                progress.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.studentupdateprofile(name,email,phone,pass);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progress.dismiss();
                        if (response.body().status.equals("true")) {
                            Toast.makeText(StudentProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(StudentProfileActivity.this, StudentHomeActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(StudentProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(StudentProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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