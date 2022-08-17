package com.kna.touristbook.event;

import com.kna.touristbook.model.Review;
import com.kna.touristbook.model.Tour;
import com.kna.touristbook.model.TourOrder;

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

    default void onReply(String botData){

    }
}
