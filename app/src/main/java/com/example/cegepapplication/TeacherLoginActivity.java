package com.example.cegepapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cegepapplication.data.model.StudentModel;
import com.example.cegepapplication.data.model.TeacherModel;
import com.example.cegepapplication.ui.login.TeacherRegistration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherLoginActivity extends AppCompatActivity {
    Button btnLogin;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference RootRef;

    EditText e_email;
    EditText e_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Teacher Login");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        e_email=findViewById(R.id.e_email);
        e_password=findViewById(R.id.e_password);
        btnLogin=findViewById(R.id.btnLogin);;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherLogin();
            }
        });
        TextView forgot=findViewById(R.id.tv_forget_pass);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(TeacherLoginActivity.this,forgotPasword.class);
                startActivity(i);
            }
        });
        Button btnRegistration=findViewById(R.id.btnRegistration);;
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TeacherLoginActivity.this, TeacherRegistration.class);
                startActivity(i);
            }
        });
    }

    private void teacherLogin() {
        String email = e_email.getText().toString().trim();
        String pass = e_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your Username...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {

            AllowAccessToLogin(email, pass);
        }
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

    private void AllowAccessToLogin( String email,  String password) {
        Toast.makeText(TeacherLoginActivity.this, email + "123" + password, Toast.LENGTH_LONG).show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(TeacherLoginActivity.this, UserHomeActivity.class);
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teacher_Registration");
                            String uId = user.getUid();
                            reference.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    TeacherModel teacherModel = snapshot.getValue(TeacherModel.class);

                                    if (teacherModel != null) {
                                        String uname = teacherModel.getuName();
                                        SharedPreferences sp = getSharedPreferences("AA", 0);
                                        SharedPreferences.Editor et = sp.edit();
                                        et.putString("uname", uname);
                                        et.commit();
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(TeacherLoginActivity.this, "failed", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            Toast.makeText(TeacherLoginActivity.this, "user Login  failed", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

    }
}