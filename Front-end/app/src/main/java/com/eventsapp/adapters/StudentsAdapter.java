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
import com.eventsapp.EditEventActivity;
import com.eventsapp.R;
import com.eventsapp.TeacherHomeActivity;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;
import com.eventsapp.model.StudentPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsAdapter extends BaseAdapter {
    List<StudentPojo> student,search;
    Context con;
    ProgressDialog progressDialog;

    public StudentsAdapter(List<StudentPojo> student, Context con) {

        this.student = student;
        this.con = con;
        this.search=student;
        this.student = new ArrayList<StudentPojo>();
        this.student.addAll(student);
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
        View students = obj.inflate(R.layout.child_students, null);

        TextView tvname = (TextView) students.findViewById(R.id.tvname);
        tvname.setText(student.get(pos).getName());

        ImageView editstudent = (ImageView) students.findViewById(R.id.editstudent);
        editstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(con, AdminEditStudentActivity.class);
                intent.putExtra("sid",student.get(pos).getSid());
                intent.putExtra("name",student.get(pos).getName());
                intent.putExtra("email",student.get(pos).getEmail());
                intent.putExtra("phone",student.get(pos).getPhone());
                intent.putExtra("pass",student.get(pos).getPass());
                con.startActivity(intent);
            }
        });

        ImageView deletestudent = (ImageView) students.findViewById(R.id.deletestudent);
        deletestudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Deleting Data");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.deletestudent(student.get(pos).getSid());
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progressDialog.dismiss();
                        if(response.body()==null){
                            Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent=new Intent(con, AdminHomeActivity.class);
                            con.startActivity(intent);
                            Toast.makeText(con," Deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(con, "Server went wrong Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return students;
    }


    public void searchStudent(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        student.clear();
        if (charText.length() == 0) {
            student.addAll(search);
        } else {
            for (StudentPojo s : search) {
                if (s.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    student.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }

}