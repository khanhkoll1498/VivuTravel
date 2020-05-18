package com.neos.touristbook.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.Review;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.model.TourOrder;
import com.neos.touristbook.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TourPresenter extends BasePresenter<TourCallback> {
    private static final String KEY_RECENT = "KEY_RECENT";
    private static final String KEY_FAVORITE = "KEY_FAVORITE";
    private static final String KEY_BOOKED = "KEY_BOOKED";

    public TourPresenter(TourCallback mCallback) {
        super(mCallback);
    }

    public void loadTour() {
        List<Tour> tourList = getAllTour();
        mCallback.onResultTourList(tourList);
    }

    private List<Tour> getAllTour() {
        String data = CommonUtils.getInstance().readFileFromAssets("tour.json");
        return new Gson().fromJson(data, new TypeToken<List<Tour>>() {
        }.getType());
    }

    public void loadHotTour() {
        List<Tour> list = getAllTour();
        List<Tour> tourList = new ArrayList<>();
        Random rd = new Random();
        for (int i = 0; i < 5; i++) {
            int index = rd.nextInt(list.size());
            tourList.add(list.get(index));
            list.remove(index);
        }
        mCallback.onResultTourList(tourList);
    }

    public void loadTour(String key) {
        List<Tour> list = getAllTour();
        List<Tour> tourList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCategory().equals(key)) {
                tourList.add(list.get(i));
            }
        }
        mCallback.onResultTourList(tourList);
    }

    public void saveRecentTour(Tour tour) {
        List<Tour> list = getRecentTourList();
        list.add(0, tour);
        CommonUtils.getInstance().savePref(KEY_RECENT, new Gson().toJson(list));
    }

    private List<Tour> getRecentTourList() {
        String data = CommonUtils.getInstance().getValuePref(KEY_RECENT, "");
        if (data.isEmpty()) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(data, new TypeToken<List<Tour>>() {
        }.getType());
    }

    public void loadRecentList() {
        List<Tour> list = getRecentTourList();
        mCallback.onResultRecentList(list);
    }

    public void loadReviewList() {
        List<Review> reviewList = getReviewList();
        mCallback.onResultReviewList(reviewList);
    }

    private List<Review> getReviewList() {
        String data = CommonUtils.getInstance().readFileFromAssets("review.json");
        return new Gson().fromJson(data, new TypeToken<List<Review>>() {
        }.getType());

    }

    public void checkFavorite(Tour tour) {
        List<Tour> list = getFavoriteTourList();
        if (!exits(tour, list)) {
            list.add(0, tour);
            mCallback.isFavorite(true);
        } else {
            removeTour(tour, list);
            mCallback.isFavorite(false);
        }
        CommonUtils.getInstance().savePref(KEY_FAVORITE, new Gson().toJson(list));
    }

    public void detackFavorite(Tour tour) {
        List<Tour> list = getFavoriteTourList();
        if (!exits(tour, list)) {
            mCallback.isFavorite(false);
        } else {
            mCallback.isFavorite(true);
        }
    }

    private void removeTour(Tour tour, List<Tour> list) {
        for (int i = 0; i < list.size(); i++) {
            if (tour.getId().equals(list.get(i).getId())) {
                list.remove(i);
                break;
            }
        }
    }

    private boolean exits(Tour tour, List<Tour> list) {
        for (int i = 0; i < list.size(); i++) {
            if (tour.getId().equals(list.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

    private List<Tour> getFavoriteTourList() {
        String data = CommonUtils.getInstance().getValuePref(KEY_FAVORITE, "");
        if (data.isEmpty()) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(data, new TypeToken<List<Tour>>() {
        }.getType());
    }

    public void loadFavoriteList() {
        List<Tour> list = getFavoriteTourList();
        mCallback.onResultTourList(list);
    }

    public void searchTour(String text) {
        List<Tour> tourList = getAllTour();
        List<Tour> resultList = searchTour(text, tourList);
        mCallback.onResultTourList(resultList);
    }

    private List<Tour> searchTour(String text, List<Tour> tourList) {
        List<Tour> list = new ArrayList<>();
        for (int i = 0; i < tourList.size(); i++) {
            if (tourList.get(i).getTitle().toLowerCase().contains(text.toLowerCase())) {
                list.add(tourList.get(i));
            }
        }
        return list;
    }

    public void saveBookedTour(TourOrder tour) {
        List<TourOrder> list = getBookedTourList();
        list.add(0, tour);
        CommonUtils.getInstance().savePref(KEY_BOOKED, new Gson().toJson(list));
    }

    private List<TourOrder> getBookedTourList() {
        String data = CommonUtils.getInstance().getValuePref(KEY_BOOKED, "");
        if (data.isEmpty()) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(data, new TypeToken<List<TourOrder>>() {
        }.getType());
    }

    public void loadBookedTour() {
        List<TourOrder> list = getBookedTourList();
        mCallback.onResultTourOrderList(list);
    }
}
