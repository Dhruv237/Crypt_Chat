package com.example.communitychatapp;

import android.content.Intent;
import java.util.concurrent.ExecutionException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.bumptech.glide.Glide;
import android.graphics.Bitmap;
import android.content.Context;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import java.io.File;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import com.example.communitychatapp.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.example.communitychatapp.text.AsyncTaskCallback.TextDecodingCallback;
import com.example.communitychatapp.text.ImageSteganography;
import com.example.communitychatapp.text.TextDecoding;

import java.io.IOException;

public class Decode extends AppCompatActivity implements TextDecodingCallback {

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "Decode Class";
    //Initializing the UI components
    private TextView textView;
    private ImageView imageView;
    private EditText message;
    private EditText secret_key;
    private Uri filepath;
    String imageUri;
    FirebaseStorage storage;
    StorageReference storageRef;
    Context context;
    //Bitmap
    private Bitmap original_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);

        //Instantiation of UI components
        textView = findViewById(R.id.whether_decoded);

        imageView = findViewById(R.id.imageview);

        message = findViewById(R.id.message);
        secret_key = findViewById(R.id.secret_key);
        storage = FirebaseStorage.getInstance();


        Button choose_image_button = findViewById(R.id.choose_image_button);
        Button decode_button = findViewById(R.id.decode_button);
        // Intent intent = getIntent();


        //Choose Image Button
     /*   choose_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageChooser();
            }
        });*/

        //Decode Button
        decode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (filepath != null) {
                Intent intent = getIntent();
                imageUri = intent.getStringExtra("imageUri");
                filepath = Uri.parse(imageUri);
                storageRef = storage.getReference().child(filepath.getLastPathSegment());
                try {
                    File localFile = File.createTempFile("original_image", "jpg");
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // File downloaded successfully, decode it into a Bitmap
                            Glide.with(Decode.this)
                                    .asBitmap()
                                    .load(uri.toString())
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            imageView.setImageBitmap(resource);

                                            // ...

                                            ImageSteganography imageSteganography = new ImageSteganography(secret_key.getText().toString(),
                                                    resource);

                                            //Making the TextDecoding object
                                            TextDecoding textDecoding = new TextDecoding(Decode.this, Decode.this);

                                            //Execute Task
                                            textDecoding.execute(imageSteganography);
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                            // Called when the image load is cleared.
                                        }
                                    });

                            // ...

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        //@Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors that occur during the download
                            // ...
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  }

                //Making the ImageSteganography object

            }
        });

    }



    /*private void ImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }*/

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Image set to imageView
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // filepath = data.getData();
            Intent intent = getIntent();
            imageUri=intent.getStringExtra("imageUri");
            filepath = imageUri;
            storageRef = storage.getReference().child(filepath);
            try {
                File localFile = File.createTempFile("original_image", "jpg");
                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // File downloaded successfully, decode it into a Bitmap
                        Bitmap original_image = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                        // Use the originalImage as needed
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors that occur during the download
                        // ...
                    }
                });
            } catch (IOException e) {
                Log.d(TAG, "Error : " + e);
            }
        }

    }*/

    @Override
    public void onStartTextEncoding() {
        //Whatever you want to do by the start of textDecoding
    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography result) {

        //By the end of textDecoding

        if (result != null) {
            if (!result.isDecoded())
                textView.setText("No message found");
            else {
                if (!result.isSecretKeyWrong()) {
                    textView.setText("Decoded");
                    message.setText("" + result.getMessage());
                } else {
                    textView.setText("Wrong secret key");
                }
            }
        } else {
            textView.setText("Select Image First");
        }


    }
}
