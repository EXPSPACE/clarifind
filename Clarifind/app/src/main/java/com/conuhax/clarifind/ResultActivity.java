package com.conuhax.clarifind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.conuhax.clarifind.model.LocationAdapter;
import com.conuhax.clarifind.model.yellowpages.FindBusinessResponse;
import com.conuhax.clarifind.model.yellowpages.Listings;
import com.conuhax.clarifind.services.ClarifaiService;
import com.conuhax.clarifind.services.YellowPagesService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.conuhax.clarifind.services.YellowPagesService.retrofit;

public class ResultActivity extends AppCompatActivity {

    public static FindBusinessResponse businessResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String coordinates = intent.getStringExtra(ClarifaiService.COORD_MESSAGE);
        String keyword = intent.getStringExtra(ClarifaiService.WORD_MESSAGE);

        TextView searchTitle = (TextView) findViewById(R.id.search_title);
        searchTitle.setText("Yellow page search results for : " + keyword);


        // Construct the data source
        ArrayList<Listings> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
        LocationAdapter adapter = new LocationAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.locationList);
        listView.setAdapter(adapter);


        YellowPagesService yellowPagesService = retrofit.create(YellowPagesService.class);
        Log.i("ANY",coordinates);
        Call<FindBusinessResponse> call = yellowPagesService.fetchBusinesses(keyword,coordinates,"JSON","rcqm8a36gxb284um4sy5yzhx","127.0.0.1");

        call.enqueue(new Callback<FindBusinessResponse>() {
            @Override
            public void onResponse(Call<FindBusinessResponse> call, Response<FindBusinessResponse> response) {
                businessResponse = response.body();
                adapter.addAll(businessResponse.listings);

            }
            @Override
            public void onFailure(Call<FindBusinessResponse> call, Throwable t) {

            }
        });
    }

    /*Put back in main  without the button
    public void loadResultPage(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        String coord = String.valueOf( "cZ" +String.valueOf(mLastLocation.getLongitude()+"," + mLastLocation.getLatitude()));
        intent.putExtra(COORD_MESSAGE,coord);
        startActivity(intent);
    }
    */

    public void onClickMapActivity(View view) {
        Intent startMapActivity = new Intent(this,MapActivity.class);
        startActivity(startMapActivity);
    }

}
