package com.example.communitychatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.text.TextUtils;

import com.example.communitychatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    private EditText login_email;
    private EditText login_password;
    private TextView sign_in;
    private TextView tv2;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        tv2 = findViewById(R.id.textView3);
        login_email = findViewById(R.id.login_email);
        sign_in=findViewById(R.id.sign_in);
        login_password = findViewById(R.id.login_password);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "enter valid data!", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    login_email.setError("invalid email");
                    Toast.makeText(LoginActivity.this, "invalid email!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    login_password.setError("invalid password!");
                    Toast.makeText(LoginActivity.this, "invalid password!", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, home.class));

                            } else {
                                Toast.makeText(LoginActivity.this, "error in login!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }}
