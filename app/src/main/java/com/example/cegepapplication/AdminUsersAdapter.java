package com.example.cegepapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cegepapplication.data.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class AdminUsersAdapter extends BaseAdapter {
    List<StudentModel> usersPojoList=new ArrayList<>();
    List<String> ids=new ArrayList<>();
    AccceptListener accceptListener;
    interface  AccceptListener{
        public void acceptStudent(String id);
    }
AdminUsersAdapter(List<StudentModel> usersPojoList,  List<String> ids,AccceptListener accceptListener){
    this.usersPojoList=usersPojoList;
    this.ids=ids;
    this.accceptListener=accceptListener;

}
    @Override
    public int getCount() {
        return usersPojoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminuserlist,null);
        TextView name=v.findViewById(R.id.name);
        TextView email=v.findViewById(R.id.email);
        name.setText(usersPojoList.get(position).getuName());
        email.setText(usersPojoList.get(position).getuEmail());
        v.findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accceptListener.acceptStudent(ids.get(position));
            }
        });
        return v;
    }
}
