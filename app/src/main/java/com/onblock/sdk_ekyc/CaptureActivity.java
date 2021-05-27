package com.onblock.sdk_ekyc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.onblock.sdk_ekyc.camera.CameraSource;
import com.onblock.sdk_ekyc.camera.CameraSourcePreview;
import com.onblock.sdk_ekyc.graphic.GraphicOverlay;
import com.onblock.sdk_ekyc.mrz.TextRecognitionProcessor;

import java.io.IOException;

public class CaptureActivity extends AppCompatActivity implements  TextRecognitionProcessor.ResultListener{

    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;

    private static  final String TAG = CaptureActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);


        preview = findViewById(R.id.camera_source_preview);
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = findViewById(R.id.graphics_overlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }
        createCameraSource();
        startCameraSource();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        startCameraSource();
    }

    /** Stops the camera. */
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private void createCameraSource() {

        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
            cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
        }

        //cameraSource.setMachineLearningFrameProcessor(new TextRecognitionProcessor(docType, this));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

   /* @Override
    public void onSuccess(MRZInfo mrzInfo) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MRZ_RESULT, mrzInfo);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }*/

    private void toValidationActivity(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome = new Intent(CaptureActivity.this,InfoValidationActivity.class);
                CaptureActivity.this.startActivity(mInHome);
                CaptureActivity.this.finish();
            }
        }, 3000);
    }


    @Override
    public void onSuccess() {
        Intent returnIntent = new Intent();
        //returnIntent.putExtra();
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onError(Exception exception) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}