package com.neos.touristbook.chatbot;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TravelAppClient {
    private static TravelAppClient instance;
    private static final long CONNECTION_TIMEOUT = 60;
    private static final long READ_TIMEOUT = 30;
    private ApiService mApiService;

    public ApiService getApiService() {
        return mApiService;
    }

    public static TravelAppClient getInstance() {
        if (instance == null) {
            instance = new TravelAppClient();
        }
        return instance;
    }

    private TravelAppClient() {
        mApiService = provideApi();
    }

    public void resetInterceptor() {
        mApiService = instance.provideApi();
    }

    private ApiService provideApi() {
        return provideRetrofit().create(ApiService.class);
    }

    private Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://v3-api.fpt.ai/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(provideOkHttpClient())
                .build();
    }

    private OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(provideInterceptor())
                .addInterceptor(logging)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private Interceptor provideInterceptor() {
        return new InterceptorImp();
    }
}
