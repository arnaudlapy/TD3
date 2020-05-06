package com.example.td3.data;

import com.example.td3.presentation.presentation.model.RestDiscographieResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DiscographieApi {

    @GET("FakeApiDaftPunk.json")
    Call<RestDiscographieResponse> getDiscographieResponse();
}
