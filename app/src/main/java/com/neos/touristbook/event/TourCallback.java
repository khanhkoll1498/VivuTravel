package com.neos.touristbook.event;

import com.neos.touristbook.model.Tour;

import java.util.List;

public interface TourCallback extends OnCallback {
    void onResultTourList(List<Tour> tourList);

    default void onResultRecentList(List<Tour> list){

    }
}
