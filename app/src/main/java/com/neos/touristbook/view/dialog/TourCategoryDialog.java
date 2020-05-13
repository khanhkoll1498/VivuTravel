package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.App;
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

public class TourCategoryDialog extends BaseDialog<TourPresenter> implements OnActionCallback, TourCallback {

    private String key;
    private TextView tvTitle;
    private TourAdapter adapter;
    private List<Tour> mList;

    public TourCategoryDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tv_title);
        findViewById(R.id.iv_back, this);
        initTour();
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    private void updateUI() {
        if (key.equals(HomeFrg.KEY_NOTHERN)) {
            tvTitle.setText("Miền Bắc");
        } else if (key.equals(HomeFrg.KEY_CENTRAL)) {
            tvTitle.setText("Miền Trung");
        } else if (key.equals(HomeFrg.KEY_SOUTH)) {
            tvTitle.setText("Miền Nam");
        }
    }

    private void initTour() {
        mList = new ArrayList<>();
        RecyclerView rvTour = findViewById(R.id.rv_tour);
        rvTour.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new TourAdapter(mList, getContext());
        adapter.setmCallback(this);
        adapter.isPreview(false);
        rvTour.setAdapter(adapter);
    }

    @Override
    public void callback(String key, Object data) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_tour_category;
    }

    public void setKey(String key) {
        this.key = key;
        updateUI();
        mPresenter.loadTour(key);
    }

    @Override
    public void onResultTourList(List<Tour> tourList) {
        mList.clear();
        mList.addAll(tourList);
        adapter.notifyDataSetChanged();
    }
}
