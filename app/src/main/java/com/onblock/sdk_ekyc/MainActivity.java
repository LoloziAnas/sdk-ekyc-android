package com.onblock.sdk_ekyc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private View mainLayout;
    private View loadingLayout;
    private View imageLayout;
    private Button btnIdentityCard, btnPassportCard;
    private TextView tvResult;
    private ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TesseractOCR tessRecognizer = new TesseractOCR(this, "eng");
        //tessRecognizer.doOCR(this, Bitmap.createBitmap(""));

        mainLayout = findViewById(R.id.main_layout);
        loadingLayout = findViewById(R.id.loading_layout);
        imageLayout = findViewById(R.id.image_layout);
        ivPhoto = findViewById(R.id.view_photo);
        tvResult = findViewById(R.id.text_result);

        btnIdentityCard = findViewById(R.id.btn_identity_card);
        btnPassportCard = findViewById(R.id.btn_passport_card);
        //btnIdentityCard.setText(str);
        btnIdentityCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CaptureActivity.class)));
    }

}