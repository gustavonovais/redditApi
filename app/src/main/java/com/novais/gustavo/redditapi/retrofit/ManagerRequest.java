package com.novais.gustavo.redditapi.retrofit;

import android.content.Context;

import com.novais.gustavo.redditapi.model.TimeLine;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ManagerRequest<T> {


    Retrofit mRetrofit;
    RequestsEndPoints.EndPoints mApi;
    Context mContext;

    public ManagerRequest(Context mContext) {
        this.mContext = mContext;
        DataRequest dataRequest = new DataRequest(mContext);
        mRetrofit = dataRequest.getRetrofit();
        mApi = dataRequest.getApi();
    }

    public void listPosts(Callback<TimeLine> timeline) throws JSONException {
        Call<TimeLine> call = mApi.listPosts();
        call.enqueue(timeline);
    }

}



