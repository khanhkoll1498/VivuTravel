package com.kna.touristbook.view.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kna.touristbook.R;
import com.kna.touristbook.event.TourCallback;
import com.kna.touristbook.model.Tour;
import com.kna.touristbook.presenter.TourPresenter;
import com.kna.touristbook.view.activity.DetailTourAct;
import com.kna.touristbook.view.adapter.TourAdapter;
import com.kna.touristbook.view.base.BaseFragment;
import com.kna.touristbook.view.dialog.SearchDialog;
import com.kna.touristbook.view.event.OnActionCallback;

import java.util.ArrayList;
import java.util.List;

import static com.kna.touristbook.view.adapter.TourAdapter.KEY_CLICK_ITEM;

public class TourFrg extends BaseFragment<TourPresenter> implements OnActionCallback, TourCallback {
    public static String TAG = TourFrg.class.getName();
    private List<Tour> mList;
    private TourAdapter adapter;

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_tour;
    }

    @Override
    protected void initView() {
        initTour();
    }

    private void initTour() {
        findViewById(R.id.edt_search, this);
        mList = new ArrayList<>();
        RecyclerView rvTour = (RecyclerView) findViewById(R.id.rv_tour);
        rvTour.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new TourAdapter(mList, getContext());
        adapter.setmCallback(this);
        adapter.isPreview(false);
        rvTour.setAdapter(adapter);
        mPresenter.loadTour();
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
    public void callback(String key, Object data) {
        if (key.equals(KEY_CLICK_ITEM)) {
            Tour tour = (Tour) data;
            showDetailTour(tour);
        }
    }

    private void showDetailTour(Tour tour) {
        Intent intent = new Intent(getContext(), DetailTourAct.class);
        intent.putExtra("data", tour);
        startActivity(intent);
    }

    @Override
    public void onResultTourList(List<Tour> tourList) {
        mList.clear();
        mList.addAll(tourList);
        adapter.notifyDataSetChanged();
    }
}
