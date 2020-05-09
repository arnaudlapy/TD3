package com.example.td3.presentation.presentation.controller;

import android.content.SharedPreferences;
import com.example.td3.Constants;
import com.example.td3.Singletons;
import com.example.td3.presentation.presentation.model.Discographie;
import com.example.td3.presentation.presentation.model.RestDiscographieResponse;
import com.example.td3.presentation.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;


    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {

        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){


        List<Discographie> discographieList = getDataFromCache();

        if(discographieList != null){
            view.showList(discographieList);
        }else{
            makeApiCall();
        }
    }

    private void makeApiCall(){


        Call<RestDiscographieResponse> call = Singletons.getDiscApi().getDiscographieResponse();
        call.enqueue(new Callback<RestDiscographieResponse>() {

            @Override
            public void onResponse(Call<RestDiscographieResponse> call, Response<RestDiscographieResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Discographie> discographieList = response.body().getResults();
                    saveList(discographieList);
                    view.showList(discographieList);
                }else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestDiscographieResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Discographie> discographieList) {

        String jsonString = gson.toJson(discographieList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_DISC_LIST, jsonString)
                .apply();
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

    public void onItemClick(Discographie Disc){

    }
}
