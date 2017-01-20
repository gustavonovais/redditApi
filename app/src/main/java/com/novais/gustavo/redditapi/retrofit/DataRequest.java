package com.novais.gustavo.redditapi.retrofit;

import com.novais.gustavo.redditapi.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class DataRequest {

    private final Retrofit retrofit;
    private final RequestsEndPoints.EndPoints api;

    public DataRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.retrofit = retrofit;
        api = this.retrofit.create(RequestsEndPoints.EndPoints.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public RequestsEndPoints.EndPoints getApi() {
        return api;
    }
}
