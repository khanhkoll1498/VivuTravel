package com.neos.touristbook.view.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.view.adapter.TourAdapter;
import com.neos.touristbook.view.base.BaseFragment;
import com.neos.touristbook.view.event.OnActionCallback;

import java.util.ArrayList;

public class FavouriteFrg extends BaseFragment implements OnActionCallback {
    public static String TAG = FavouriteFrg.class.getName();

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_favourite;
    }

    @Override
    protected void initView() {
        initTour();
    }

    private void initTour() {
        RecyclerView rvTour = (RecyclerView) findViewById(R.id.rv_tour);
        rvTour.setLayoutManager(new GridLayoutManager(getContext(), 2));
        TourAdapter adapter = new TourAdapter(new ArrayList<Tour>(), getContext());
        adapter.setmCallback(this);
        adapter.isPreview(false);
        rvTour.setAdapter(adapter);
    }

    @Override
    public void callback(String key, Object data) {

    }
}
