package com.example.td3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DiscographieApi {

    @GET("FakeApiDaftPunk.json")
    Call<RestDiscographieResponse> getDiscographieResponse();
}
