package com.neos.touristbook.chatbot;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("predict")
    Single<AnswerResponse> getAnswer(@Body BodyRequest bodyRequest);
}
