package com.neos.touristbook.chatbot;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class InterceptorImp implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = initHeader(chain);
        Request request = builder.build();
        return chain.proceed(request);
    }

    private Request.Builder initHeader(Interceptor.Chain chain) {
        Request originRequest = chain.request();
        return originRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "a8c9893d55daa2f564e7d2f8fd1a6d7a")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Cache-Control", "no-store")
                .method(originRequest.method(), originRequest.body());
    }
}
