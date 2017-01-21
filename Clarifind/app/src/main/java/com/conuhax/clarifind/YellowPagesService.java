package com.conuhax.clarifind;

import com.conuhax.clarifind.model.yellowpages.FindBusinessResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NSPACE on 1/21/2017.
 */

public interface YellowPagesService {

    @GET("/FindBusiness/")
//    Call<ResponseBody> fetchBusinesses(@Query("what") String what, @Query("where") String where, @Query("fmt") String fmt, @Query("apikey") String apikey, @Query("UID") String UID);
    Call<FindBusinessResponse> fetchBusinesses(@Query("what") String what, @Query("where") String where, @Query("fmt") String fmt, @Query("apikey") String apikey, @Query("UID") String UID);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.sandbox.yellowapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
