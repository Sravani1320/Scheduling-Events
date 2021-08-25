package com.eventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class EditEventActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://paytracker.ca/";
    EditText etcat,etname,etdate,etvenue,etdes;
    private Uri uri;
    Button btnadd,btnimageupload,btnupdateevent;
    ProgressDialog progress;
    private static final String TAG = StudentAddEventActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        etcat=(EditText)findViewById(R.id.etcat);
        etname=(EditText)findViewById(R.id.etname);
        etdate=(EditText)findViewById(R.id.etdate);
        etvenue=(EditText)findViewById(R.id.etvenue);
        etdes=(EditText)findViewById(R.id.etdes);


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

                progress = new ProgressDialog(EditEventActivity.this);
                progress.setMessage("Adding please wait");
                progress.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.updateevent(id,category,name,dat,venue,description);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progress.dismiss();
                        if (response.body().status.equals("true")) {
                            Toast.makeText(EditEventActivity.this, response.body().message, Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(EditEventActivity.this, StudentHomeActivity.class);
//                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(EditEventActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(EditEventActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}