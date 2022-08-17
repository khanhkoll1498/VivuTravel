package com.kna.touristbook.chatbot;

import com.google.gson.annotations.SerializedName;

public class IntentAnswer {
    @SerializedName("label")
    private String label;
    @SerializedName("confidence")
    private double confidence;

    public String getLabel() {
        return label;
    }

    public double getConfidence() {
        return confidence;
    }
}
