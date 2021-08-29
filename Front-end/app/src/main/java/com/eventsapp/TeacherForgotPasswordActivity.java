package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherForgotPasswordActivity extends AppCompatActivity {
    EditText textEmailAddress;
    Button btngetpassword;
    SharedPreferences sharedPreferences;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_forgot_password);
        getSupportActionBar().setTitle("Retrive Password");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        email=sharedPreferences.getString("user_name","");

        btngetpassword=(Button)findViewById(R.id.btngetpassword);
        textEmailAddress=(EditText)findViewById(R.id.textEmailAddress);
        btngetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textEmailAddress.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter your Email",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {

                    ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                    Call<ResponseData> call = service.tforgotPassword(textEmailAddress.getText().toString());


                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.body().status.equals("true")) {
                                Intent s=new Intent(TeacherForgotPasswordActivity.this,TeacherLoginActivity.class);
                                startActivity(s);
                                Toast.makeText(TeacherForgotPasswordActivity.this, response.body().message.toString(), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(TeacherForgotPasswordActivity.this, response.body().message.toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            Toast.makeText(TeacherForgotPasswordActivity.this, t.toString(), Toast.LENGTH_LONG).show();

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