package com.example.communitychatapp.text.AsyncTaskCallback;

import com.example.communitychatapp.text.ImageSteganography;

/**
 * This the callback interface for TextEncoding AsyncTask.
 */

public interface TextEncodingCallback {

    void onStartTextEncoding();

    void onCompleteTextEncoding(ImageSteganography result);

}
