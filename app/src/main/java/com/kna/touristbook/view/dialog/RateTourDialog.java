package com.kna.touristbook.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.kna.touristbook.R;
import com.kna.touristbook.event.TourCallback;
import com.kna.touristbook.model.Tour;
import com.kna.touristbook.presenter.TourPresenter;
import com.kna.touristbook.view.base.BaseDialog;
import com.ymb.ratingbar_lib.RatingBar;

public class RateTourDialog extends BaseDialog<TourPresenter> implements TourCallback {
    private RatingBar rating;
    private ImageView ivPreview;
    private Tour tour;

    public RateTourDialog(@NonNull Context context, int style) {
        super(context, style);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected void initView() {
        rating = (RatingBar) findViewById(R.id.rating);
        findViewById(R.id.tv_rate, this);
        ivPreview = (ImageView) findViewById(R.id.iv_preview);
    }

    @Override
    public void onClick(View v) {
        rateTour();
    }

    private void rateTour() {
        mPresenter.rateTour(tour, rating.getRating());
        dismiss();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_rate;
    }

    public void setData(Tour tour) {
        this.tour = tour;
        Glide.with(getContext()).load(tour.getImageList().get(0).getImage()).into(ivPreview);
    }
}
