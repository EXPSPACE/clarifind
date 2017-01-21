package com.conuhax.clarifind.services;

import android.graphics.Bitmap;
import android.util.Log;

import com.conuhax.clarifind.Authentication.Credential;
import com.conuhax.clarifind.model.clarifai.Keyword;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

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
                    ClarifaiOutput clarifaiOutput = c.get(0);


                    List<Keyword> keywordList = new ArrayList<>();
                    for(int i=0; i< clarifaiOutput.data().size();i++)
                    {
                        Concept current = (Concept) clarifaiOutput.data().get(i);
                        Log.e("name", current.name());
                        Log.e("value", String.valueOf(current.value()));

                        if(current.value()> 0.90)
                        {
                            Keyword newKeyword = new Keyword(current.name(),current.value());
                            keywordList.add(newKeyword);
                        }
                    }
                });





    }








}
