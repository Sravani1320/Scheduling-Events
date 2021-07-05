package com.example.cegepapplication.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cegepapplication.R;
import com.example.cegepapplication.data.model.StudentModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                    Toast.makeText(RegisterActivity.this, "Please write your email...", Toast.LENGTH_SHORT).show();
                    return;

                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(RegisterActivity.this, "Please Choose your password...", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(uname)) {
                    Toast.makeText(RegisterActivity.this, "Please write your uname...", Toast.LENGTH_SHORT).show();
                    return;
                } else {


                    ValidatepEmail(uname, email, pass);
                }
            }
        });
    }
    private void ValidatepEmail(final String uname, final String email, final String pass) {

        StudentModel studentModel = new StudentModel();
        studentModel.setuEmail(email);
        studentModel.setuPassword(pass);
        studentModel.setuName(uname);
        studentModel.setStatus("notaccepted");

        Toast.makeText(RegisterActivity.this, "method sucessfully", Toast.LENGTH_LONG).show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "user registered sucessfully", Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference("User_Registration").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(studentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, "user created sucessfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}