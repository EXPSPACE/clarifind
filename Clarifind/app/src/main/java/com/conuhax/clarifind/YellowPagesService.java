package com.conuhax.clarifind;

import com.conuhax.clarifind.model.yellowpages.FindBusinessResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NSPACE on 1/21/2017.
 */

public interface YellowPagesService {

    @GET("/FindBusiness/?what={what}&where={where}&fmt={json|xml}&apikey={xxxxxxxxxxxxxxxxxxxxxxxx}&UID={unique identifier}")
    Call<FindBusinessResponse> fetchBusinesses(@Query("what") String what, @Query("where") String where, @Query("fmt") String fmt, @Query("apikey") String apikey, @Query("UID") String UID);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.yellowapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}