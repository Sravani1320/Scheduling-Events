package com.eventsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eventsapp.api.ApiService;
import com.eventsapp.model.ResponseData;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherAddEventActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://paytracker.ca/";
    EditText etcat,etname,etdate,etvenue,etdes;
    private Uri uri;
    Button btnadd,btnimageupload;
    ProgressDialog progress;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    private static final String TAG = StudentAddEventActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_event);
        getSupportActionBar().setTitle("Add Event");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        btnimageupload=(Button) findViewById(R.id.btnimageupload);
        btnimageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });

        btnadd=(Button)findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etcat.getText().toString().isEmpty()) {
                    Toast.makeText(TeacherAddEventActivity.this, "Enter Category", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etname.getText().toString().isEmpty()) {
                    Toast.makeText(TeacherAddEventActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etdate.getText().toString().isEmpty()) {
                    Toast.makeText(TeacherAddEventActivity.this, "Enter Date", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etvenue.getText().toString().isEmpty()) {
                    Toast.makeText(TeacherAddEventActivity.this, "Enter Venue", Toast.LENGTH_SHORT).show();
                    return;
                }else if (etdes.getText().toString().isEmpty()) {
                    Toast.makeText(TeacherAddEventActivity.this, "Enter Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    addEvent();
                }

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, TeacherAddEventActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, TeacherAddEventActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, TeacherAddEventActivity.this);
            file = new File(filePath);
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }

    private void addEvent() {
        progress = new ProgressDialog(TeacherAddEventActivity.this);
        progress.setTitle("Loading");
        progress.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        Map<String, String> map = new HashMap<>();
        map.put("category", etcat.getText().toString());
        map.put("name", etname.getText().toString());
        map.put("venue", etvenue.getText().toString());
        map.put("description", etdes.getText().toString());
        map.put("email", email);
        map.put("dat", etdate.getText().toString());
        Toast.makeText(TeacherAddEventActivity.this, email, Toast.LENGTH_LONG).show();

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService addevent = retrofit.create(ApiService.class);
        Call<ResponseData> fileUpload = addevent.addevent(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progress.dismiss();
                Toast.makeText(TeacherAddEventActivity.this, "EventAdded successfully. ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(TeacherAddEventActivity.this,TeacherHomeActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(TeacherAddEventActivity.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void eventDate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(TeacherAddEventActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        etdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
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