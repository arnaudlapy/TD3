package com.example.td3;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.td3.data.DiscographieApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static DiscographieApi discApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance == null){

            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }return gsonInstance;
    }

    public static DiscographieApi getDiscApi(){
        if(discApiInstance == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
            discApiInstance = retrofit.create(DiscographieApi.class);
        }return discApiInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferencesInstance == null){

            sharedPreferencesInstance = context.getSharedPreferences(Constants.KEP_APP_ESIEA, Context.MODE_PRIVATE);
        }return sharedPreferencesInstance;
    }
}
