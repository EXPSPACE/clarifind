package com.conuhax.clarifind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;

public class CameraActivity extends AppCompatActivity {

    private static final int CAPTURE_MEDIA = 368;
    private boolean showImagePicker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        launchCamera();
    }

    private void launchCamera() {
        new SandriosCamera(CameraActivity.this, CAPTURE_MEDIA)
                .setShowPicker(showImagePicker)
                .setVideoFileSize(15) //File Size in MB: Default is no limit
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_PHOTO) // default is CameraConfiguration.MEDIA_ACTION_BOTH
                .launchCamera();
    }
}
