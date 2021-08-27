package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherEditEventActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://paytracker.ca/";
    EditText etcat,etname,etdate,etvenue,etdes;
    private Uri uri;
    Button btnadd,btnimageupload,btnupdateevent;
    ProgressDialog progress;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;

    private static final String TAG = TeacherEditEventActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit_event);
        etcat=(EditText)findViewById(R.id.etcat);
        etname=(EditText)findViewById(R.id.etname);
        etdate=(EditText)findViewById(R.id.etdate);
        etvenue=(EditText)findViewById(R.id.etvenue);
        etdes=(EditText)findViewById(R.id.etdes);

        etdate.setFocusable(false);
        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventDate();
            }
        });
        etcat.setText(getIntent().getStringExtra("category"));
        etname.setText(getIntent().getStringExtra("name"));
        etdate.setText(getIntent().getStringExtra("dat"));
        etvenue.setText(getIntent().getStringExtra("venue"));
        etdes.setText(getIntent().getStringExtra("description"));


        btnupdateevent=(Button) findViewById(R.id.btnupdateevent);
        btnupdateevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=getIntent().getStringExtra("eid");
                String category=etcat.getText().toString();
                String name=etname.getText().toString();
                String dat=etdate.getText().toString();
                String venue=etvenue.getText().toString();
                String description=etdes.getText().toString();

                progress = new ProgressDialog(TeacherEditEventActivity.this);
                progress.setMessage("Updating please wait");
                progress.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.updateevent(id,category,name,dat,venue,description);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progress.dismiss();
                        if (response.body().status.equals("true")) {
                            Toast.makeText(TeacherEditEventActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TeacherEditEventActivity.this, TeacherHomeActivity.class);
                            startActivity(intent);
                            //  finish();

                        } else {
                            Toast.makeText(TeacherEditEventActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(TeacherEditEventActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    public void eventDate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(TeacherEditEventActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";
                        etdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}