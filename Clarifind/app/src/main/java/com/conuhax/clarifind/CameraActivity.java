package com.conuhax.clarifind;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;

import java.io.IOException;

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

            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            ExifInterface exif = null;

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap myBitmap = BitmapFactory.decodeFile(picPath,bmOptions);
            myBitmap = Bitmap.createScaledBitmap(myBitmap,1000,1000,true);
            int orientation;

            try {
                exif = new ExifInterface(picPath);
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

                if (orientation == 6) {
                    matrix.postRotate(0);
                }
                else if (orientation == 1) {
                    matrix.postRotate(270);
                }
                
                myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true); // rotating bitmap
            } catch (IOException e) {
                e.printStackTrace();
            }


            //rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, 1000, 1000, matrix, true);
            imageView.setImageBitmap(myBitmap);





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
