package com.eventsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eventsapp.R;
import com.eventsapp.model.StudentPojo;
import com.eventsapp.model.TeacherPojo;

import java.util.List;

public class TeachersAdapter extends BaseAdapter {
    List<TeacherPojo> teacher;
    Context con;


    public TeachersAdapter(List<TeacherPojo> teacher, Context con) {
        this.teacher = teacher;
        this.con = con;
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

            }
        });

        ImageView deleteteacher = (ImageView) teachers.findViewById(R.id.deleteteacher);
        deleteteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return teachers;
    }

}