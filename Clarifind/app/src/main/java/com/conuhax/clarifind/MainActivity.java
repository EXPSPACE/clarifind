package com.conuhax.clarifind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.conuhax.clarifind.model.yellowpages.FindBusinessResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.conuhax.clarifind.YellowPagesService.retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Camera button */
    public void openCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
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
