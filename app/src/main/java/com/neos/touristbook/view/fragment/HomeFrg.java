package com.neos.touristbook.view.fragment;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.neos.touristbook.R;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.view.adapter.PreviewAdapter;
import com.neos.touristbook.view.adapter.TourAdapter;
import com.neos.touristbook.view.base.BaseFragment;
import com.neos.touristbook.view.event.OnActionCallback;

import java.util.ArrayList;

public class HomeFrg extends BaseFragment implements OnActionCallback {
    public static String TAG = HomeFrg.class.getName();
    private ViewPager vpPreview;
    private PreviewAdapter adapter;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_home;
    }

    @Override
    protected void initView() {
        initPreView();
        initHotTour();
        initRecentTour();
    }

    private void initHotTour() {
        RecyclerView rvHotTour = (RecyclerView) findViewById(R.id.rv_hot_tour);
        rvHotTour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        TourAdapter adapter = new TourAdapter(new ArrayList<Tour>(), getContext());
        adapter.setmCallback(this);
        rvHotTour.setAdapter(adapter);
    }

    private void initRecentTour() {
        RecyclerView rvHotTour = (RecyclerView) findViewById(R.id.rv_recent_tour);
        rvHotTour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        TourAdapter adapter = new TourAdapter(new ArrayList<Tour>(), getContext());
        adapter.setmCallback(this);
        rvHotTour.setAdapter(adapter);
    }

    private void initPreView() {
        vpPreview = (ViewPager) findViewById(R.id.vp_preview);
        adapter = new PreviewAdapter(getChildFragmentManager(), new String[]{
                "//android_asset/preview/preview1.jpg",
                "//android_asset/preview/preview2.jpg",
                "//android_asset/preview/preview3.jpg"
        });
        vpPreview.setAdapter(adapter);
        smoothPreview();
    }

    private int index = 0;

    private void smoothPreview() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                vpPreview.setCurrentItem(index, true);
                if (index == 2) {
                    index = 0;
                }
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }


    @Override
    public void callback(String key, Object data) {

    }
}
