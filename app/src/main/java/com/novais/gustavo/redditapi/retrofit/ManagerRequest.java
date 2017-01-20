package com.novais.gustavo.redditapi.retrofit;

import android.content.Context;

import com.novais.gustavo.redditapi.model.TimeLine;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;

public class ManagerRequest<T> {

    private final RequestsEndPoints.EndPoints mApi;

    public ManagerRequest(Context mContext) {
        Context context = mContext;
        DataRequest dataRequest = new DataRequest(mContext);
        mApi = dataRequest.getApi();
    }

    public void listPosts(Callback<TimeLine> timeline) {
        Call<TimeLine> call = mApi.listPosts();
        call.enqueue(timeline);
    }

}



