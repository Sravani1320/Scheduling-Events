package com.example.cegepapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cegepapplication.data.model.StudentModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudents extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        list = (ListView) findViewById(R.id.list);
 FirebaseDatabase.getInstance().getReference().child("User_Registration").orderByChild("status").equalTo("notaccepted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<StudentModel> studentModels = new ArrayList<>();
                List<String> ids=new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    StudentModel studentModel = snapshot1.getValue(StudentModel.class);
                    ids.add(snapshot1.getKey());
                    studentModels.add(studentModel);
                }
                AdminUsersAdapter adminUsersAdapter=new AdminUsersAdapter(studentModels, ids, new AdminUsersAdapter.AccceptListener() {
                    @Override
                    public void acceptStudent(String id) {

                        FirebaseDatabase.getInstance().getReference().child("User_Registration").child(id)
                                .child("status").setValue("accepted").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("test1234", "changed");
                                }
                            }
                        });

                    }
                });
                list.setAdapter(adminUsersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}