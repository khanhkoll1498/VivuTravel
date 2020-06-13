package com.neos.touristbook.event;

import com.neos.touristbook.model.Review;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.model.TourOrder;

import java.util.List;

public interface TourCallback extends OnCallback {
    default void onResultTourList(List<Tour> tourList) {

    }

    default void onResultRecentList(List<Tour> list) {

    }

    default void onResultReviewList(List<Review> reviewList) {

    }

    default void isFavorite(boolean b) {

    }

    default void onResultTourOrderList(List<TourOrder> list) {

    }

    default void onResultRate(Float numStar, long numRate) {

    }
}
