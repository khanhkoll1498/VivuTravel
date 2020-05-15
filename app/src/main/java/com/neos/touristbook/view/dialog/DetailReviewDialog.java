package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.neos.touristbook.R;
import com.neos.touristbook.model.ContentReview;
import com.neos.touristbook.model.Description;
import com.neos.touristbook.model.Image;
import com.neos.touristbook.model.Review;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.view.adapter.PreviewAdapter;
import com.neos.touristbook.view.adapter.ReviewDetailAdapter;
import com.neos.touristbook.view.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

public class DetailReviewDialog extends BaseDialog {
    private Tour tour;
    private List<ContentReview> mList;
    private TextView tvTitle;


    public DetailReviewDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_back, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mList = new ArrayList<>();
        RecyclerView rvReview = (RecyclerView) findViewById(R.id.rv_review);
        ReviewDetailAdapter adapter = new ReviewDetailAdapter(mList, getContext());
        rvReview.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            dismiss();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_detail_review;
    }


    public void setData(Review review) {
        tvTitle.setText(review.getTitle());
        List<Image> imageList = review.getImageList();
        List<Description> desList = review.getDescription();
        int n = desList.size();
        if (imageList.size() > desList.size()) {
            n = imageList.size();
        }
        for (int i = 0; i < n; i++) {
            String image = "";
            try {
                image = imageList.get(i).getImage();
            } catch (Exception e) {

            }
            String content = "";
            try {
                content = desList.get(i).getContent();
            } catch (Exception e) {
            }
            mList.add(new ContentReview(image, content));
        }

    }
}
