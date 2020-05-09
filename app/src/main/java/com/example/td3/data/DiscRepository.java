package com.example.td3.data;

import android.content.SharedPreferences;
import com.example.td3.Constants;
import com.example.td3.presentation.presentation.model.Discographie;
import com.example.td3.presentation.presentation.model.RestDiscographieResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscRepository {

    private final Gson gson;
    private DiscographieApi discApi;
    private SharedPreferences sharedPreferences;

    public DiscRepository(DiscographieApi discApi, SharedPreferences sharedPreferences, Gson gson){

        this.discApi = discApi;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public void getDiscographieResponse(final DiscCallback callback){

        List<Discographie> list = getDataFromCache();

        if(list != null){
            callback.onSuccess(list);
        }else {
            discApi.getDiscographieResponse().enqueue(new Callback<RestDiscographieResponse>() {
                @Override
                public void onResponse(Call<RestDiscographieResponse> call, Response<RestDiscographieResponse> response) {

                    if(response.isSuccessful() && response.body() != null){
                        callback.onSuccess(response.body().getResults());
                    }else{ callback.onFailed();}
                }
                @Override
                public void onFailure(Call<RestDiscographieResponse> call, Throwable t) {
                    callback.onFailed();
                }
            });
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
}
