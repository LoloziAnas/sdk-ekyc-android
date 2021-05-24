package com.onblock.sdk_ekyc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.onblock.sdk_ekyc.fragment.CameraFragment;

public class CameraViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, CameraFragment.class, null)
                    .commit();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent mInHome = new Intent(CameraViewActivity.this,InfoValidationActivity.class);
                CameraViewActivity.this.startActivity(mInHome);
                CameraViewActivity.this.finish();
            }
        }, 3000);
    }
}