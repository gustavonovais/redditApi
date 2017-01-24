package com.novais.gustavo.redditapi.retrofit;

import com.novais.gustavo.redditapi.BuildConfig;
import com.novais.gustavo.redditapi.model.TimeLine;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public class RequestsEndPoints {

    private static final String URI = "/r/Android/new/.json?";

    public interface EndPoints {

        @POST(URI + "limit=" + BuildConfig.LIMIT)
        Call<TimeLine> listPosts();

        @POST(URI)
        Call<TimeLine> listPosts(@QueryMap Map<String, String> filters);


    }
}