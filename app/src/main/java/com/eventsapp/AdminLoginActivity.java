package com.eventsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoginActivity extends AppCompatActivity {

    Button btnadminlogin;
    EditText etadminemail,etadminpass;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etadminpass=(EditText)findViewById(R.id.etadminpass);
        etadminemail=(EditText)findViewById(R.id.etadminemail);

        btnadminlogin=(Button)findViewById(R.id.btnadminlogin);
        btnadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etadminemail.getText().toString().isEmpty()){
                    Toast.makeText(AdminLoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etadminpass.getText().toString().isEmpty()){
                    Toast.makeText(AdminLoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    //progreess bar loading
                    progress= new ProgressDialog(AdminLoginActivity.this);
                    progress.setTitle("Please wait,Data is being submit...");
                    progress.show();
                    // api call code
                    ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
                    Call<ResponseData> call = apiService.adminlogin(etadminemail.getText().toString(),etadminpass.getText().toString());
                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            progress.dismiss();
                            if (response.body().status.equals("true")) {
                                startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(AdminLoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            progress.dismiss();
                            Toast.makeText(AdminLoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


    }
}