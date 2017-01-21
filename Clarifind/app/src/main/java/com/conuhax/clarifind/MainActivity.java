package com.conuhax.clarifind;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.conuhax.clarifind.Utilities.ImageManager;
import com.conuhax.clarifind.services.ClarifaiService;

import java.util.List;

import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    public void test()
    {

        ClarifaiService clarifaiService = ClarifaiService.getInstance();


        Bitmap image = ImageManager.getBitmapFromAsset(this,"hammer.jpg");
        int nb = image.getByteCount();
        clarifaiService.sendImage(image);
        
    }



}
