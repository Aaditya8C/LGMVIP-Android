package com.example.snapchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {

    ImageButton open;
//    Button open;



    private final static int IMAGE_CAPTURE = 33;
    FirebaseVisionImage image;
    FirebaseVisionFaceDetector faceDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);
        open = findViewById(R.id.button);


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1);
                }
                else
                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(intent.resolveActivity(getPackageManager()) != null)
                    {
                        startActivityForResult(intent,IMAGE_CAPTURE);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Image has not been captured due to some error!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extra = data.getExtras();
            Bitmap bt = (Bitmap) extra.get("data");
            detectFace(bt);
        }
    }

    private void detectFace(Bitmap bt) {
        FirebaseVisionFaceDetectorOptions op = new FirebaseVisionFaceDetectorOptions.Builder()
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS).build();

        try{
            image = FirebaseVisionImage.fromBitmap(bt);
            faceDetector = FirebaseVision.getInstance().getVisionFaceDetector(op);

        }catch (Exception e){
            System.out.println(e);
        }

        //Creating face detector model
        faceDetector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            @Override
            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                String result = "";
                int i = 1;
                for(FirebaseVisionFace face:firebaseVisionFaces)
                {
                    result = result.concat("\nFace Number "+i+": ")
                            .concat("\nSmile Percentage: "+ face.getSmilingProbability() * 100 + "%")
                            .concat("\nLeft Eye Open Probability: " + face.getLeftEyeOpenProbability() * 100 + "%")
                            .concat("\nRight Eye Open Probability: " + face.getRightEyeOpenProbability() * 100 + "%")
                            .concat("\nLandmarks: " + face.getLandmark(100));
//                            .concat("\nAll Contours " + face.getContour());
//                            .concat(("\nPerformance Mode "+ face.getHeadEulerAngleZ()));
                    i++;
                }

                if(firebaseVisionFaces.size() == 0)
                {
                    Toast.makeText(MainActivity.this, "No face has been detected", Toast.LENGTH_SHORT).show();
                }
                else{
                    Bundle bundle = new Bundle();
                    bundle.putString(LCOFaceDetection.RESULT_TEXT,result);
                    DialogFragment df = new ResultDialog();
                    df.setArguments(bundle);
                    df.setCancelable(true);
                    df.show(getSupportFragmentManager(),LCOFaceDetection.RESULT_DIALOGUE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Some Unrecognized error have been occurred!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}