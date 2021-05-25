package com.onblock.sdk_ekyc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TesseractOCR tessRecognizer = new TesseractOCR(this, "eng");
        //tessRecognizer.doOCR(this, Bitmap.createBitmap(""));

        Button btnIdentityCard = findViewById(R.id.btn_identity_card);
        Button btnPassportCard = findViewById(R.id.btn_passport_card);
        //btnIdentityCard.setText(str);
        btnIdentityCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CaptureActivity.class)));
    }

}