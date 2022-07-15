package com.example.snapchat;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class LCOFaceDetection extends Application {
    public final static String RESULT_TEXT = "RESULT_TEXT";
    public final static String RESULT_DIALOGUE = "RESULT_DIALOGUE";


    public void onCreate()
    {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
