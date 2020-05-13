package com.neos.touristbook.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TourPresenter extends BasePresenter<TourCallback> {
    private static final String KEY_RECENT = "KEY_RECENT";

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
}
