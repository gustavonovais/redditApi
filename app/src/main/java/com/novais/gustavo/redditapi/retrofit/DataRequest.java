package com.novais.gustavo.redditapi.retrofit;

import android.content.Context;

import com.novais.gustavo.redditapi.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRequest {

    Context context;
    Retrofit retrofit;
    RequestsEndPoints.EndPoints api;

    public DataRequest(Context context) {
        this.context = context;

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
