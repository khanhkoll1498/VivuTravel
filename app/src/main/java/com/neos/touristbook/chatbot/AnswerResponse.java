package com.neos.touristbook.chatbot;

import com.google.gson.annotations.SerializedName;

public class AnswerResponse {
    @SerializedName("data")
    private DataAnswerResponse dataAnswerResponse;

    public DataAnswerResponse getDataAnswerResponse() {
        return dataAnswerResponse;
    }
}
