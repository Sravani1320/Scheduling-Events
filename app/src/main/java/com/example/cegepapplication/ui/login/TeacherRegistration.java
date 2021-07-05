package com.example.cegepapplication.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cegepapplication.R;
import com.example.cegepapplication.data.model.StudentModel;
import com.example.cegepapplication.data.model.TeacherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherRegistration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);
        final EditText et_uname = findViewById(R.id.et_fullname);
        final EditText et_password = findViewById(R.id.et_password);
        final EditText et_email = findViewById(R.id.et_email);
        final Button registerButton = findViewById(R.id.btnRegistration);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String pass = et_password.getText().toString();

                String uname = et_uname.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(TeacherRegistration.this, "Please write your email...", Toast.LENGTH_SHORT).show();
                    return;

                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(TeacherRegistration.this, "Please Choose your password...", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(uname)) {
                    Toast.makeText(TeacherRegistration.this, "Please write your uname...", Toast.LENGTH_SHORT).show();
                    return;
                } else {


                    ValidatepEmail(uname, email, pass);
                }
            }
        });
    }
    private void ValidatepEmail(final String uname, final String email, final String pass) {

        TeacherModel teacherModel = new TeacherModel();
        teacherModel.setuEmail(email);
        teacherModel.setuPassword(pass);
        teacherModel.setuName(uname);


        Toast.makeText(TeacherRegistration.this, "method sucessfully", Toast.LENGTH_LONG).show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(TeacherRegistration.this, "Teacher registered sucessfully", Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference("Teacher_Registration").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(teacherModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(TeacherRegistration.this, "Teacher created sucessfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TeacherRegistration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                } else {
                    Toast.makeText(TeacherRegistration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}