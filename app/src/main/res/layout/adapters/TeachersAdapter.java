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

import com.eventsapp.AdminEditTeacherActivity;
import com.eventsapp.AdminHomeActivity;
import com.eventsapp.R;
import com.eventsapp.api.ApiService;
import com.eventsapp.api.RetroClient;
import com.eventsapp.model.ResponseData;
import com.eventsapp.model.TeacherPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeachersAdapter extends BaseAdapter {
    List<TeacherPojo> teacher,search;
    Context con;
    ProgressDialog progressDialog;

    public TeachersAdapter(List<TeacherPojo> teacher, Context con) {
        this.teacher = teacher;
        this.con = con;
        this.search=teacher;
        this.teacher = new ArrayList<TeacherPojo>();
        this.teacher.addAll(teacher);
    }

    @Override
    public int getCount() {
        return teacher.size();
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
        View teachers = obj.inflate(R.layout.child_teachers, null);

        TextView tvname = (TextView) teachers.findViewById(R.id.tvname);
        tvname.setText(teacher.get(pos).getName());

        ImageView editteacher = (ImageView) teachers.findViewById(R.id.editteacher);
        editteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(con, AdminEditTeacherActivity.class);
                intent.putExtra("tid",teacher.get(pos).getTid());
                intent.putExtra("name",teacher.get(pos).getName());
                intent.putExtra("email",teacher.get(pos).getEmail());
                intent.putExtra("phone",teacher.get(pos).getPhone());
                intent.putExtra("pass",teacher.get(pos).getPass());
                con.startActivity(intent);
            }
        });

        ImageView deleteteacher = (ImageView) teachers.findViewById(R.id.deleteteacher);
        deleteteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Deleting Data");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.deleteteacher(teacher.get(pos).getTid());
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

        return teachers;
    }

    public void searchTeacher(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        teacher.clear();
        if (charText.length() == 0) {
            teacher.addAll(search);
        } else {
            for (TeacherPojo s : search) {
                if (s.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacher.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }

}