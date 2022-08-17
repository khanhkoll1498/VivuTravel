package com.kna.touristbook.chatbot;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataAnswerResponse {
    @SerializedName("intents")
    private List<IntentAnswer> intentAnswerResponse;

    public List<IntentAnswer> getIntentAnswerResponse() {
        return intentAnswerResponse;
    }
}

