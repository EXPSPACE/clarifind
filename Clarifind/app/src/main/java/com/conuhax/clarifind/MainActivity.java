package com.conuhax.clarifind;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.conuhax.clarifind.Utilities.ImageManager;
import com.conuhax.clarifind.model.yellowpages.FindBusinessResponse;
import com.conuhax.clarifind.services.ClarifaiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.conuhax.clarifind.YellowPagesService.retrofit;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);

            return;
        }

        test();
    }

    public void test()
    {

        ClarifaiService clarifaiService = ClarifaiService.getInstance();


        Bitmap image = ImageManager.getBitmapFromAsset(this,"hammer.jpg");
        int nb = image.getByteCount();
        clarifaiService.sendImage(image);
        
    }

    /** Called when the user clicks the Camera button */
    public void openCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void onLocationSearch(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    public void onYellowPageQuery(View view) {
        YellowPagesService yellowPagesService = retrofit.create(YellowPagesService.class);
        Call<FindBusinessResponse> call = yellowPagesService.fetchBusinesses("car","montreal","JSON","rcqm8a36gxb284um4sy5yzhx","127.0.0.1");

        call.enqueue(new Callback<FindBusinessResponse>() {
            @Override
            public void onResponse(Call<FindBusinessResponse> call, Response<FindBusinessResponse> response) {
                final TextView textView = (TextView) findViewById(R.id.yellow_page_response);
                textView.setText(response.body().toString());
            }
            @Override
            public void onFailure(Call<FindBusinessResponse> call, Throwable t) {
                final TextView textView = (TextView) findViewById(R.id.yellow_page_response);
                textView.setText("Something went wrong: " + t.getMessage());
            }
        });
    }
}
