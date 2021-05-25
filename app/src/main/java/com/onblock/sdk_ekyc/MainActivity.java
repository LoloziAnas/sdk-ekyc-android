package com.onblock.sdk_ekyc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;

import com.onblock.sdk_ekyc.ocr.TesseractOCR;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TesseractOCR tessRecognizer = new TesseractOCR(this, "eng");
        //tessRecognizer.doOCR(this, Bitmap.createBitmap(""));

        Button btnIdentityCard = findViewById(R.id.btn_identity_card);
        //btnIdentityCard.setText(str);
        btnIdentityCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CaptureActivity.class)));
    }

}