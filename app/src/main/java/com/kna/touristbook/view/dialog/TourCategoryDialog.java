package com.kna.touristbook.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kna.touristbook.R;
import com.kna.touristbook.event.TourCallback;
import com.kna.touristbook.model.Tour;
import com.kna.touristbook.presenter.TourPresenter;
import com.kna.touristbook.view.activity.DetailTourAct;
import com.kna.touristbook.view.adapter.TourAdapter;
import com.kna.touristbook.view.base.BaseDialog;
import com.kna.touristbook.view.event.OnActionCallback;
import com.kna.touristbook.view.fragment.HomeFrg;

import java.util.ArrayList;
import java.util.List;

import static com.kna.touristbook.view.adapter.TourAdapter.KEY_CLICK_ITEM;

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
        switch (key) {
            case HomeFrg.KEY_NOTHERN:
                tvTitle.setText("Miền Bắc");
                break;
            case HomeFrg.KEY_CENTRAL:
                tvTitle.setText("Miền Trung");
                break;
            case HomeFrg.KEY_SOUTH:
                tvTitle.setText("Miền Nam");
                break;
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
        if (key.equals(KEY_CLICK_ITEM)) {
            Tour tour = (Tour) data;
            showDetailTour(tour);
        }
    }
    private void showDetailTour(Tour tour) {
        Intent intent = new Intent(getContext(), DetailTourAct.class);
        intent.putExtra("data", tour);
        getContext().startActivity(intent);
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
