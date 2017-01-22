package com.conuhax.clarifind.services;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.conuhax.clarifind.Authentication.Credential;
import com.conuhax.clarifind.MainActivity;
import com.conuhax.clarifind.R;
import com.conuhax.clarifind.ResultActivity;
import com.conuhax.clarifind.model.clarifai.Keyword;
import com.conuhax.clarifind.model.yellowpages.FindBusinessResponse;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.conuhax.clarifind.services.YellowPagesService.retrofit;

/**
 * Created by michal wozniak on 1/21/2017.
 */

public class ClarifaiService {

    private static ClarifaiService service;
    private static ClarifaiClient client;
    public final static String COORD_MESSAGE = "com.example.myfirstapp.COORDINATE";
    public final static String WORD_MESSAGE = "com.example.myfirstapp.KEYWORD";

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
     * @param mainActivity
     * @return
     */
    public void sendImage(Bitmap image, LinearLayout linearLayoutKeywords, MainActivity mainActivity)
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

                        if(current.value() > 0.90) {
                            Keyword newKeyword = new Keyword(current.name(), current.value());
                            keywordList.add(newKeyword);
                        }

                    }

                    for (int i = 0; i < keywordList.size() && i<5; i++) {
                        Keyword word = keywordList.get(i);

                        Log.e("SIZE", String.valueOf(keywordList.size()));

                        Log.e("name", word.getName());
                        Log.e("value", String.valueOf(word.getValue()));

                        mainActivity.runOnUiThread(() ->
                        {
                            // This code will always run on the UI thread, therefore is safe to modify UI


                            Button btn = new Button(mainActivity);

                            btn.setText(word.getName());
                            btn.setOnClickListener(v -> {

                                Intent intent = new Intent(mainActivity, ResultActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                String coord = String.valueOf( "cZ" +String.valueOf(mainActivity.getmLastLocation().getLongitude()+"," + mainActivity.getmLastLocation().getLatitude()));
                                intent.putExtra(COORD_MESSAGE,coord);
                                intent.putExtra(WORD_MESSAGE,word.getName());
                                mainActivity.startActivity(intent);
                                mainActivity.finish();

                            });


                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            LinearLayout linearLayout = (LinearLayout) mainActivity.findViewById(R.id.LayoutKeywords);
                            linearLayout.addView(btn,lp);

                        });


                    }
                });





    }








}
