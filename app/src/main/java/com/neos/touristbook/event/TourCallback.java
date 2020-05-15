package com.neos.touristbook.event;

import com.neos.touristbook.model.Review;
import com.neos.touristbook.model.Tour;

import java.util.List;

public interface TourCallback extends OnCallback {
    default void onResultTourList(List<Tour> tourList){

    }

    default void onResultRecentList(List<Tour> list){

    }

    default void onResultReviewList(List<Review> reviewList){

    }

    default void isFavorite(boolean b){

    }
}
