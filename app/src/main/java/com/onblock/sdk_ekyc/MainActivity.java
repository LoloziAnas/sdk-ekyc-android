package com.onblock.sdk_ekyc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIdentityCard = findViewById(R.id.btn_identity_card);
        btnIdentityCard.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, IdentityActivity.class));

        });


    }


}