package com.example.communitychatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.communitychatapp.R;
import com.example.communitychatapp.ModelClass.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.onActivityResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import androidx.annotation.NonNull;
import android.text.TextUtils;


import android.os.Bundle;
public class RegistrationActivity extends AppCompatActivity {
    private EditText reg_name,reg_email,reg_password,cnf_password;
    private TextView sign_in,sign_up;
    private TextView tv2;
    private ImageView profile_image;
    FirebaseAuth auth;
    Uri imageUri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
        reg_name = findViewById(R.id.reg_name);
        profile_image=findViewById(R.id.profile_image);
        reg_email = findViewById(R.id.reg_email);
        reg_password = findViewById(R.id.reg_password);
        cnf_password = findViewById(R.id.cnf_password);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = reg_name.getText().toString();
                String password = reg_password.getText().toString();
                String email = reg_email.getText().toString();
                String conf_password = cnf_password.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(conf_password)) {
                    Toast.makeText(RegistrationActivity.this, "enter valid data!", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    reg_email.setError("enter valid email");
                    Toast.makeText(RegistrationActivity.this, "enter valid  email!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(conf_password)) {
                    reg_email.setError("enter valid email");
                    Toast.makeText(RegistrationActivity.this, "password does not match!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    reg_password.setError("password length must be  6");
                    Toast.makeText(RegistrationActivity.this, "password length must be 6!", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
                                StorageReference storagereference = storage.getReference().child("upload").child(auth.getUid());
                                if (imageUri != null) {
                                    storagereference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                storagereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageuri = uri.toString();
                                                        Users users = new Users(auth.getUid(), name, email, imageuri, "hey!i am using this app!");
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    startActivity(new Intent(RegistrationActivity.this, home.class));
                                                                } else {
                                                                    Toast.makeText(RegistrationActivity.this, "error in creating user!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }


                                                });

                                            }
                                        }
                                    });
                                } else {
                                    imageuri = "https://firebasestorage.googleapis.com/v0/b/communitychatapp-fb146.appspot.com/o/profile.jpg?alt=media&token=88e74cf2-7498-482a-8878-4dfc7f1931e8";
                                    Users users = new Users(auth.getUid(), name, email, imageuri, "hey! i am using this app!");
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(RegistrationActivity.this, home.class));
                                            } else {
                                                Toast.makeText(RegistrationActivity.this, "error in creating user!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });


                        }
                    }});
        profile_image.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                //Launch activity to get result
                someActivityResultLauncher.launch(intent);
            }
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new ActivityResultCallback<ActivityResult>() {
                    //@Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() ==RESULT_OK) {
                            //Intent data = result.getData();
                            imageUri = result.getData().getData();
                            profile_image.setImageURI(imageUri);
                            UploadImageToFirebase(imageUri);


                        }
                    }


                });
            private void UploadImageToFirebase(Uri image){
                StorageReference fileRef = storage.getReference().child("upload").child(auth.getUid());
                fileRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(RegistrationActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
        }});
        sign_in.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

            }
        });
    }}