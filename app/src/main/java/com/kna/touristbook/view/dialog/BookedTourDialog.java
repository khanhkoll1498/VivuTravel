package com.kna.touristbook.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kna.touristbook.R;
import com.kna.touristbook.event.TourCallback;
import com.kna.touristbook.model.TourOrder;
import com.kna.touristbook.presenter.TourPresenter;
import com.kna.touristbook.view.adapter.TourOrderAdapter;
import com.kna.touristbook.view.base.BaseDialog;
import com.kna.touristbook.view.event.OnActionCallback;

import java.util.ArrayList;
import java.util.List;

public class BookedTourDialog extends BaseDialog<TourPresenter> implements OnActionCallback, TourCallback {
    private TourOrderAdapter adapter;
    private ArrayList<TourOrder> mList;

    public BookedTourDialog(@NonNull Context context, int style) {
        super(context, style);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_back, this);
        initTour();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }

    private void initTour() {
        mList = new ArrayList<>();
        RecyclerView rvTour = (RecyclerView) findViewById(R.id.rv_tour);
        rvTour.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TourOrderAdapter(mList, getContext());
        adapter.setmCallback(this);
        adapter.isPreview(false);
        rvTour.setAdapter(adapter);
        mPresenter.loadBookedTour();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_booked_tour;
    }

    @Override
    public void callback(String key, Object data) {
        TourOrder tourOrder = (TourOrder) data;
        if (tourOrder.isRate()) {
            rateTour(tourOrder);
        } else {
            cancelTour(tourOrder);
        }
    }

    private void cancelTour(TourOrder tourOrder) {
        adapter.notifyItemRemoved(mList.indexOf(tourOrder));
        mList.remove(tourOrder);
        mPresenter.updateTourOrder(mList);
    }

    private void rateTour(TourOrder tourOrder) {
        RateTourDialog dialog = new RateTourDialog(getContext(), R.style.AppTheme);
        dialog.setData(tourOrder.getTour());
        dialog.show();
    }

    @Override
    public void onResultTourOrderList(List<TourOrder> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
