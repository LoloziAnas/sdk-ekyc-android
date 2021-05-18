package com.onblock.sdk_ekyc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        final Python python = Python.getInstance();

        PyObject pyScript = python.getModule("pythonScript");
        /*PyObject pyObject = pyScript.callAttr("Sum",5,8);
        String str  = pyObject.toString();

        Toast.makeText(this, str, Toast.LENGTH_LONG).show();*/

        Button btnIdentityCard = findViewById(R.id.btn_identity_card);
        //btnIdentityCard.setText(str);
        btnIdentityCard.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, IdentityActivity.class));

        });


    }


}