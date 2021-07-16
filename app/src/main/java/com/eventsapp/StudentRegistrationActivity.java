package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class StudentRegistrationActivity extends AppCompatActivity {

    EditText etname, etemail, etphone, etpass;
    Button btnregister;
    TextView tvstudentlogin;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        getSupportActionBar().setTitle("Add Student Information");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etname =(EditText)findViewById(R.id.etname);
        etemail = (EditText)findViewById(R.id.etemail);
        etphone = (EditText)findViewById(R.id.etphone);
        etpass = (EditText)findViewById(R.id.etpass);
        btnregister = (Button)findViewById(R.id.btnregister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etname.getText().toString().isEmpty()){
                    Toast.makeText(StudentRegistrationActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etemail.getText().toString().isEmpty()){
                    Toast.makeText(StudentRegistrationActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etphone.getText().toString().isEmpty()){
                    Toast.makeText(StudentRegistrationActivity.this, "Enter Phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etpass.getText().toString().isEmpty()){
                    Toast.makeText(StudentRegistrationActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    progress = new ProgressDialog(StudentRegistrationActivity.this);
                    progress.setMessage("Adding please wait");
                    progress.show();
                    ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                    Call<ResponseData> call = service.studentadd(etname.getText().toString(),etemail.getText().toString(),etphone.getText().toString(),etpass.getText().toString());
                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            progress.dismiss();
                            if (response.body().status.equals("true")) {
                                Toast.makeText(StudentRegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(StudentRegistrationActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(StudentRegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            progress.dismiss();
                            Toast.makeText(StudentRegistrationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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