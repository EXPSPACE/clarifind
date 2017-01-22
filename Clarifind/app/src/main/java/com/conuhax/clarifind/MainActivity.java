package com.conuhax.clarifind;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conuhax.clarifind.Authentication.Credential;
import com.conuhax.clarifind.model.clarifai.Keyword;
import com.conuhax.clarifind.model.yellowpages.FindBusinessResponse;
import com.conuhax.clarifind.services.ClarifaiService;
import com.conuhax.clarifind.services.YellowPagesService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.handle;
import static com.conuhax.clarifind.services.YellowPagesService.retrofit;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final int CAPTURE_MEDIA = 368;
    private boolean showImagePicker = true;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    public final static String COORD_MESSAGE = "com.example.myfirstapp.COORDINATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);

            return;
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    //Connects to the location service
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    //For the location Service
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);


        if (mLastLocation != null) {
            String coord = String.valueOf(mLastLocation.getLatitude()) + "  " + String.valueOf(mLastLocation.getLongitude());
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
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


            //send to clarifai
            ClarifaiService clarifaiService = ClarifaiService.getInstance();
            LinearLayout layoutKeywords = (LinearLayout) findViewById(R.id.LayoutKeywords);
            clarifaiService.sendImage(myBitmap, layoutKeywords,this);



            Log.e("File", "" + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH));
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }


    /** Called when the user clicks the Camera button */
    public void openCamera(View view) {
        LinearLayout welcomeLayout = (LinearLayout)this.findViewById(R.id.Welcome);
        LinearLayout keywordLayout = (LinearLayout)this.findViewById(R.id.LayoutKeywords);
        //welcomeLayout.setVisibility(LinearLayout.GONE);
        //keywordLayout.setVisibility(LinearLayout.VISIBLE);

        launchCamera();

    }

    public void onLocationSearch(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onYellowPageQuery(View view) {
        YellowPagesService yellowPagesService = retrofit.create(YellowPagesService.class);
        Call<FindBusinessResponse> call = yellowPagesService.fetchBusinesses("car","montreal","JSON", Credential.YELLOW_PAGES_KEY,"127.0.0.1");

        call.enqueue(new Callback<FindBusinessResponse>() {
            @Override
            public void onResponse(Call<FindBusinessResponse> call, Response<FindBusinessResponse> response) {
//                final TextView textView = (TextView) findViewById(R.id.yellow_page_response);
//                textView.setText(response.body().toString());
            }
            @Override
            public void onFailure(Call<FindBusinessResponse> call, Throwable t) {
//                final TextView textView = (TextView) findViewById(R.id.yellow_page_response);
//                textView.setText("Something went wrong: " + t.getMessage());
            }
        });
    }

    public void onClickMapActivity(View view) {
        Intent startMapActivity = new Intent(this,MapActivity.class);
        startActivity(startMapActivity);
    }

    private void launchCamera() {
        new SandriosCamera(this, CAPTURE_MEDIA)
                .setShowPicker(showImagePicker)
                .setVideoFileSize(15) //File Size in MB: Default is no limit
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_PHOTO) // default is CameraConfiguration.MEDIA_ACTION_BOTH
                .launchCamera();
    }
}
