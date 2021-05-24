package com.onblock.sdk_ekyc.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.onblock.sdk_ekyc.R;
import com.onblock.sdk_ekyc.camera.CameraPreview;

import java.io.IOException;



public class CameraFragment extends Fragment  {


    public static final String TAG = "CameraFragment";
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;


    private final static String ORIENTATION = "orientation";
    private final static String PORTRAIT = "portrait";
    private final static String LANDSCAPE = "landscape";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.camera_preview_layout, container, false);
        mSurfaceView = view.findViewById(R.id.sv_camera);
        mSurfaceHolder = mSurfaceView.getHolder();

        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        Camera camera = getCameraInstance();
        //get an instance of CameraPreview
        CameraPreview cameraPreview = new CameraPreview(getContext(), camera,mSurfaceView );
        FrameLayout preview = view.findViewById(R.id.camera_preview);
        preview.addView(cameraPreview);
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission Granted");
        } else {
            //shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}