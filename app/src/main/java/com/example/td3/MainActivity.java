package com.example.td3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://raw.githubusercontent.com/arnaudlapy/TD3/master/";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(Constants.KEP_APP_ESIEA, Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Discographie> discographieList = getDataFromCache();

        if(discographieList != null){
            showList(discographieList);
        }else{
            makeApiCall();
        }
    }

    private List<Discographie> getDataFromCache() {

        String jsonDiscographie = sharedPreferences.getString(Constants.KEY_DISC_LIST, null);

        if(jsonDiscographie == null){
            return null;
        }else{
            Type listType = new TypeToken<List<Discographie>>(){}.getType();
            return gson.fromJson(jsonDiscographie, listType);
        }
    }

    private void showList(List<Discographie> discographieList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(discographieList);
        recyclerView.setAdapter(mAdapter);

    }

    private void makeApiCall(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DiscographieApi DiscoApi = retrofit.create(DiscographieApi.class);
        Call<RestDiscographieResponse> call = DiscoApi.getDiscographieResponse();
        call.enqueue(new Callback<RestDiscographieResponse>() {

            @Override
            public void onResponse(Call<RestDiscographieResponse> call, Response<RestDiscographieResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Discographie> discographieList = response.body().getResults();
                    saveList(discographieList);
                    showList(discographieList);
                }else {
                    showError();
                }
             }

             @Override
             public void onFailure(Call<RestDiscographieResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<Discographie> discographieList) {

        String jsonString = gson.toJson(discographieList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_DISC_LIST, jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
