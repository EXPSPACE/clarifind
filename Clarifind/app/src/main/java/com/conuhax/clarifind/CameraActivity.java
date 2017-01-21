package com.conuhax.clarifind;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            String s = "File " + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH);
            String picPath = data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH);


            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(picPath,bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap,1000,1000,true);
            imageView.setImageBitmap(bitmap);

            Log.e("File", "" + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH));
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }

    private void launchCamera() {
        new SandriosCamera(CameraActivity.this, CAPTURE_MEDIA)
                .setShowPicker(showImagePicker)
                .setVideoFileSize(15) //File Size in MB: Default is no limit
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_PHOTO) // default is CameraConfiguration.MEDIA_ACTION_BOTH
                .launchCamera();
    }


}
