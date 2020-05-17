package com.neos.touristbook.view.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.Review;
import com.neos.touristbook.presenter.TourPresenter;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.adapter.ReviewAdapter;
import com.neos.touristbook.view.base.BaseFragment;
import com.neos.touristbook.view.dialog.DetailReviewDialog;
import com.neos.touristbook.view.dialog.SearchDialog;
import com.neos.touristbook.view.event.OnActionCallback;

import java.util.ArrayList;
import java.util.List;

public class ReviewFrg extends BaseFragment<TourPresenter> implements TourCallback, OnActionCallback {
    public static String TAG = ReviewFrg.class.getName();
    private ReviewAdapter adapter;
    private List<Review> mList;

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_review;
    }

    @Override
    protected void initView() {
        initReview();
    }

    private void initReview() {
        findViewById(R.id.edt_search, this);
        mList = new ArrayList<>();
        RecyclerView rvReview = (RecyclerView) findViewById(R.id.rv_review);
        rvReview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ReviewAdapter(mList, getContext());
        adapter.setmCallback(this);
        rvReview.setAdapter(adapter);
        mPresenter.loadReviewList();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.edt_search){
            searchTour();
        }
    }
    private void searchTour() {
        SearchDialog dialog = new SearchDialog(getContext(), R.style.AppTheme);
        dialog.show();
    }


    @Override
    public void onResultReviewList(List<Review> reviewList) {
        mList.clear();
        mList.addAll(reviewList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void callback(String key, Object data) {
        if (key.equals(ReviewAdapter.KEY_CLICK_ITEM)) {
            Review review = (Review) data;
            showDetail(review);
        }
    }

    private void showDetail(Review review) {
        DetailReviewDialog dialog = new DetailReviewDialog(context, R.style.AppTheme);
        dialog.setData(review);
        dialog.show();
    }
}
