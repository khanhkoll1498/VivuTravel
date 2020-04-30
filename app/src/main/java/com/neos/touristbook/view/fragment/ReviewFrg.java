package com.neos.touristbook.view.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.view.adapter.ReviewAdapter;
import com.neos.touristbook.view.base.BaseFragment;

import java.util.ArrayList;

public class ReviewFrg extends BaseFragment {
    public static String TAG = ReviewFrg.class.getName();

    @Override
    protected void initPresenter() {
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
        RecyclerView rvReview = (RecyclerView) findViewById(R.id.rv_review);
        rvReview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ReviewAdapter adapter = new ReviewAdapter(new ArrayList<>(), getContext());
        rvReview.setAdapter(adapter);
    }
}
