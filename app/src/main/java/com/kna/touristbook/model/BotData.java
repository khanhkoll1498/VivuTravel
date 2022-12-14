package com.kna.touristbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BotData {
    @SerializedName("ques")
    @Expose
    private String ques;
    @SerializedName("ans")
    @Expose
    private List<String> ans = null;

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public List<String> getAns() {
        return ans;
    }

    public void setAns(List<String> ans) {
        this.ans = ans;
    }
}
