package com.example.snapchat;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

//class DialogFragment{
//
//}

public class ResultDialog extends DialogFragment{

    TextView info;
    Button dialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_resultdialog,container,false);

        String resultInfo = "";

        info = view.findViewById(R.id.textView1);
        dialog = view.findViewById(R.id.button1);


        Bundle bundle = getArguments();
        resultInfo = bundle.getString(LCOFaceDetection.RESULT_TEXT);
        info.setText(resultInfo);
        
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
