package com.onblock.sdk_ekyc.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.fragment.app.Fragment;

import com.onblock.sdk_ekyc.R;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private SurfaceView mSurfaceView;

    private  final static String TAG = CameraPreview.class.getSimpleName();
    private final static String ORIENTATION = "orientation";
    private final static String PORTRAIT = "portrait";
    private final static String LANDSCAPE = "landscape";

    public CameraPreview(Context context, Camera camera, SurfaceView surfaceView) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);

        mSurfaceView = surfaceView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint  = new Paint();
        paint.setStyle(Paint.Style.STROKE);
    }

    public void surfaceCreated(SurfaceHolder holder) {

        Camera.Parameters parameters = mCamera.getParameters();
        //change the orientation of the camera
        if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
            parameters.set(ORIENTATION, PORTRAIT);
            mCamera.setDisplayOrientation(90);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            parameters.setRotation(90);
        }else {
            parameters.set(ORIENTATION, LANDSCAPE);
            mCamera.setDisplayOrientation(0);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

            parameters.setRotation(0);
        }
        mCamera.setParameters(parameters);

        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        mCamera.stopPreview();
        mCamera.release();

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

}
