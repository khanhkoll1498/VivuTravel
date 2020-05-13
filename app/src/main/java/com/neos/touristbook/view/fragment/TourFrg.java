package com.neos.touristbook.view.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.presenter.TourPresenter;
import com.neos.touristbook.view.adapter.TourAdapter;
import com.neos.touristbook.view.base.BaseFragment;
import com.neos.touristbook.view.dialog.DetailTourDialog;
import com.neos.touristbook.view.event.OnActionCallback;

import java.util.ArrayList;
import java.util.List;

import static com.neos.touristbook.view.adapter.TourAdapter.KEY_CLICK_ITEM;

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
    public void callback(String key, Object data) {
        if (key.equals(KEY_CLICK_ITEM)) {
            Tour tour= (Tour) data;
            showDetailTour(tour);
        }
    }

    private void showDetailTour(Tour tour) {
        DetailTourDialog dialog = new DetailTourDialog(context, R.style.AppTheme);
        dialog.setTour(tour);
        dialog.show();
    }

    @Override
    public void onResultTourList(List<Tour> tourList) {
        mList.clear();
        mList.addAll(tourList);
        adapter.notifyDataSetChanged();
    }
}
