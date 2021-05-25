package com.onblock.sdk_ekyc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class CameraViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
        if (savedInstanceState == null) {

        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome = new Intent(CameraViewActivity.this,InfoValidationActivity.class);
                CameraViewActivity.this.startActivity(mInHome);
                CameraViewActivity.this.finish();
            }
        }, 3000);
    }
}