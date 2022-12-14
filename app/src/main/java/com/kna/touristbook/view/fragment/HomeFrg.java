package com.kna.touristbook.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kna.touristbook.R;
import com.kna.touristbook.event.TourCallback;
import com.kna.touristbook.model.Tour;
import com.kna.touristbook.presenter.TourPresenter;
import com.kna.touristbook.view.activity.DetailTourAct;
import com.kna.touristbook.view.adapter.PreviewAdapter;
import com.kna.touristbook.view.adapter.TourAdapter;
import com.kna.touristbook.view.base.BaseFragment;
import com.kna.touristbook.view.dialog.SearchDialog;
import com.kna.touristbook.view.dialog.TourCategoryDialog;
import com.kna.touristbook.view.event.OnActionCallback;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import static com.kna.touristbook.view.adapter.TourAdapter.KEY_CLICK_ITEM;

public class HomeFrg extends BaseFragment<TourPresenter> implements OnActionCallback, TourCallback {
    public static final String KEY_NOTHERN = "cate_01";
    public static final String KEY_CENTRAL = "cate_02";
    public static final String KEY_SOUTH = "cate_03";
    public static String TAG = HomeFrg.class.getName();
    private ViewPager vpPreview;
    private PreviewAdapter adapter;
    private TourAdapter adapterTourHot;
    private List<Tour> tourHotList;
    private TourAdapter adapterRecent;
    private List<Tour> recentList;
    private PageIndicatorView pageIndicatorView;

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_home;
    }

    @Override
    protected void initView() {
        findViewById(R.id.ln_central, this);
        findViewById(R.id.ln_northern, this);
        findViewById(R.id.ln_south, this);
        findViewById(R.id.edt_search, this);
        initPreView();
        initHotTour();
        initRecentTour();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edt_search:
                searchTour();
                break;
            case R.id.ln_northern:
                loadTour(KEY_NOTHERN);
                break;
            case R.id.ln_central:
                loadTour(KEY_CENTRAL);
                break;
            case R.id.ln_south:
                loadTour(KEY_SOUTH);
                break;
        }
    }

    private void searchTour() {
        SearchDialog dialog = new SearchDialog(getContext(), R.style.AppTheme);
        dialog.show();
    }

    private void loadTour(String key) {
        TourCategoryDialog dialog = new TourCategoryDialog(getContext(), R.style.AppTheme);
        dialog.setKey(key);
        dialog.show();
    }

    private void initHotTour() {
        tourHotList = new ArrayList<>();
        RecyclerView rvHotTour = (RecyclerView) findViewById(R.id.rv_hot_tour);
        rvHotTour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterTourHot = new TourAdapter(tourHotList, getContext());
        adapterTourHot.setmCallback(this);
        rvHotTour.setAdapter(adapterTourHot);
        mPresenter.loadHotTour();
    }

    private void initRecentTour() {
        recentList = new ArrayList<>();
        RecyclerView rvHotTour = (RecyclerView) findViewById(R.id.rv_recent_tour);
        rvHotTour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterRecent = new TourAdapter(recentList, getContext());
        adapterRecent.setmCallback(this);
        rvHotTour.setAdapter(adapterRecent);
        mPresenter.loadRecentList();
    }

    private void initPreView() {
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(3);
        vpPreview = (ViewPager) findViewById(R.id.vp_preview);
        List<String> imageList = new ArrayList<>();
        imageList.add("//android_asset/preview/preview1.jpg");
        imageList.add("//android_asset/preview/preview2.jpg");
        imageList.add("//android_asset/preview/preview3.jpg");
        adapter = new PreviewAdapter(getChildFragmentManager(), imageList);
        vpPreview.setAdapter(adapter);
        vpPreview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


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
        tourHotList.clear();
        tourHotList.addAll(tourList);
        adapterTourHot.notifyDataSetChanged();
    }

    @Override
    public void onResultRecentList(List<Tour> list) {
        recentList.clear();
        recentList.addAll(list);
        adapterRecent.notifyDataSetChanged();
    }
}
