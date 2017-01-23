package com.novais.gustavo.redditapi.retrofit;

import com.novais.gustavo.redditapi.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class DataRequest {

    private final Retrofit retrofit;
    private final RequestsEndPoints.EndPoints api;

    public DataRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.retrofit = retrofit;
        api = this.retrofit.create(RequestsEndPoints.EndPoints.class);
    }

    private static OkHttpClient getOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.readTimeout(60, TimeUnit.SECONDS);
            builder.connectTimeout(60, TimeUnit.SECONDS);
            builder.interceptors().add(new RetrofitLogInterceptor(true));

            OkHttpClient okHttpClient = builder.build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public RequestsEndPoints.EndPoints getApi() {
        return api;
    }
}
