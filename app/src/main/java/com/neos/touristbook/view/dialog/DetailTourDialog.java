package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.presenter.TourPresenter;
import com.neos.touristbook.view.adapter.TourAdapter;
import com.neos.touristbook.view.base.BaseDialog;
import com.neos.touristbook.view.event.OnActionCallback;
import com.neos.touristbook.view.fragment.HomeFrg;

import java.util.ArrayList;
import java.util.List;

public class DetailTourDialog extends BaseDialog<TourPresenter> implements OnActionCallback, TourCallback {
    private Tour tour;

    public DetailTourDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void initPresenter() {
        mPresenter=new TourPresenter(this);
    }

    @Override
    protected void initView() {
    }

    private void updateUI() {

    }

    @Override
    public void callback(String key, Object data) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_detail_tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
        mPresenter.saveRecentTour(tour);
    }


    @Override
    public void onResultTourList(List<Tour> tourList) {

    }
}
