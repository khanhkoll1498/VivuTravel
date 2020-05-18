package com.neos.touristbook.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.TourOrder;
import com.neos.touristbook.presenter.TourPresenter;
import com.neos.touristbook.view.adapter.TourOrderAdapter;
import com.neos.touristbook.view.base.BaseDialog;
import com.neos.touristbook.view.event.OnActionCallback;

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

    }



    @Override
    public void onResultTourOrderList(List<TourOrder> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
