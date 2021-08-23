package com.eventsapp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eventsapp.AdminEditStudentActivity;
import com.eventsapp.AdminHomeActivity;
import com.eventsapp.R;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;
import com.eventsapp.model.StudentPojo;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaticipantsAdapter extends BaseAdapter {
    List<StudentPojo> student,search;
    Context con;
    ProgressDialog progressDialog;

    public PaticipantsAdapter(List<StudentPojo> student, Context con) {
        this.student = student;
        this.search=student;
        this.con = con;
    }

    @Override
    public int getCount() {
        return student.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View students = obj.inflate(R.layout.child_paticipants, null);

        TextView tvname = (TextView) students.findViewById(R.id.tvname);
        tvname.setText(student.get(pos).getEmail());

        return students;
    }

}