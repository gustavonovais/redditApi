package com.novais.gustavo.redditapi.retrofit;

import com.novais.gustavo.redditapi.model.TimeLine;

import retrofit2.Call;
import retrofit2.http.POST;


public class RequestsEndPoints {

    public static final String URI = "/r/Android/new/.json";

    public interface EndPoints {

        @POST(URI)
        Call<TimeLine> listPosts();

    }
}