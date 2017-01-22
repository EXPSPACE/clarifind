package com.conuhax.clarifind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.conuhax.clarifind.model.yellowpages.FindBusinessResponse;
import com.conuhax.clarifind.services.YellowPagesService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.conuhax.clarifind.services.YellowPagesService.retrofit;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

//        Intent intent = getIntent();
//        String coordinates = intent.getStringExtra(MainActivity.COORD_MESSAGE);
//
//        YellowPagesService yellowPagesService = retrofit.create(YellowPagesService.class);
//        Log.i("ANY",coordinates);
//        Call<FindBusinessResponse> call = yellowPagesService.fetchBusinesses("car",coordinates,"JSON","rcqm8a36gxb284um4sy5yzhx","127.0.0.1");
//
//        call.enqueue(new Callback<FindBusinessResponse>() {
//            @Override
//            public void onResponse(Call<FindBusinessResponse> call, Response<FindBusinessResponse> response) {
//                final TextView textView = (TextView) findViewById(R.id.yellow_response);
//                textView.setText(response.body().toString());
//            }
//            @Override
//            public void onFailure(Call<FindBusinessResponse> call, Throwable t) {
//                final TextView textView = (TextView) findViewById(R.id.yellow_response);
//                textView.setText("Something went wrong: " + t.getMessage());
//            }
//        });
    }

    /*Put back in main  without the button
    public void loadResultPage(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        String coord = String.valueOf( "cZ" +String.valueOf(mLastLocation.getLongitude()+"," + mLastLocation.getLatitude()));
        intent.putExtra(COORD_MESSAGE,coord);
        startActivity(intent);
    }
    */
}
