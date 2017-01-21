package com.conuhax.clarifind.services;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.conuhax.clarifind.Authentication.Credential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

import static android.R.attr.bitmap;

/**
 * Created by michal wozniak on 1/21/2017.
 */

public class ClarifaiService {

    private static ClarifaiService service;
    private static ClarifaiClient client;

    private ClarifaiService(){}

    public static ClarifaiService getInstance()
    {
        if(service == null)
        {
            service =  new ClarifaiService();
            client = new ClarifaiBuilder(Credential.CLIENT_ID, Credential.CLIENT_SECRET).buildSync();
        }
        return service;
    }


    /**
     * send the bytes of one image and receive back predictions from the general model
     *
     * @param image
     * @return
     */
    public void sendImage(Bitmap image)
    {
        Log.d("sendImage", "processed");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        client.getDefaultModels().generalModel().predict()
                .withInputs(
                        ClarifaiInput.forImage(ClarifaiImage.of(byteArray))
                )
                .executeAsync(c -> {
                   String output =  c.get(0).toString();

                    Log.e("test",output);

                });



    }








}
