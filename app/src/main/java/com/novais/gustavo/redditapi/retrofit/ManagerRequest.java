package com.novais.gustavo.redditapi.retrofit;

import com.novais.gustavo.redditapi.BuildConfig;
import com.novais.gustavo.redditapi.model.ParamKey;
import com.novais.gustavo.redditapi.model.TimeLine;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ManagerRequest<T> {

    private final RequestsEndPoints.EndPoints mApi;

    public ManagerRequest() {
        DataRequest dataRequest = new DataRequest();
        mApi = dataRequest.getApi();
    }

    public void listPosts(Callback<TimeLine> timeline) {
        Call<TimeLine> call = mApi.listPosts();
        call.enqueue(timeline);
    }

    public void listPostsPagination(Callback<TimeLine> timeline, String after) {
        Map<String, String> data = new HashMap<>();
        data.put(ParamKey.LIMIT, String.valueOf(BuildConfig.LIMIT));
        data.put(ParamKey.AFTER, after);

        Call<TimeLine> call = mApi.listPosts(data);
        call.enqueue(timeline);
    }

}



