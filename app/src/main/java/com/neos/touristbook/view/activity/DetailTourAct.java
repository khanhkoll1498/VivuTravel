package com.neos.touristbook.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.neos.touristbook.R;
import com.neos.touristbook.event.TourCallback;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.presenter.TourPresenter;
import com.neos.touristbook.view.adapter.PlanAdapter;
import com.neos.touristbook.view.adapter.PreviewAdapter;
import com.neos.touristbook.view.base.BaseActivity;
import com.neos.touristbook.view.event.OnActionCallback;

import java.util.ArrayList;
import java.util.List;

import static com.neos.touristbook.view.adapter.TourAdapter.BASE_URL;

public class DetailTourAct extends BaseActivity<TourPresenter> implements OnActionCallback, TourCallback {
    private Tour tour;
    private TextView tvTitle;
    private ImageView ivFavorite;
    private TextView tvAddress, tvPrice2,tvPrice, tvDes;
    private RecyclerView rvPlan;
    private ViewPager vpPreview;
    private PreviewAdapter adapter;

    @Override
    protected void initPresenter() {
        mPresenter = new TourPresenter(this);
    }

    @Override
    protected void initView() {
        mapView();
        tour = (Tour) getIntent().getSerializableExtra("data");
        mPresenter.saveRecentTour(tour);
        updateUI();
    }

    private void mapView() {
        tvTitle = findViewById(R.id.tv_title);
        tvAddress = findViewById(R.id.tv_address);
        tvPrice = findViewById(R.id.tv_price);
        tvPrice2 = findViewById(R.id.tv_price2);
        tvDes = findViewById(R.id.tv_description);
        rvPlan = findViewById(R.id.rv_plan);
        ivFavorite = findViewById(R.id.iv_favorite, this);
        findViewById(R.id.iv_back, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.iv_favorite) {
            mPresenter.checkFavorite(tour);
        }
    }

    private void initPreView() {
        vpPreview = (ViewPager) findViewById(R.id.vp_preview);
        List<String> imageList = new ArrayList<>();
        for (int i = 0; i < tour.getImageList().size(); i++) {
            imageList.add(BASE_URL + tour.getImageList().get(i).getImage());
        }
        adapter = new PreviewAdapter(getSupportFragmentManager(), imageList);
        vpPreview.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_detail_tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;

    }

    private void updateUI() {
        initPreView();
        tvTitle.setText(tour.getTitle());
        tvAddress.setText(tour.getAddress());
        tvPrice.setText(tour.getPrice());
        tvPrice2.setText(tour.getPrice());
        tvDes.setText(tour.getDescription());
        for (int i = 0; i < tour.getPlans().size(); i++) {
            String str = tour.getPlans().get(i).getContent();
            String header = str.substring(0, str.indexOf(".") + 1);
            String content = str.substring(str.indexOf(".") + 1, str.length() - 1);
            tour.getPlans().get(i).setContent(content);
            tour.getPlans().get(i).setHeader(header);
        }
        mPresenter.detackFavorite(tour);
        initListPlan();
    }

    private void initListPlan() {
        rvPlan.setLayoutManager(new LinearLayoutManager(this));
        PlanAdapter adapter = new PlanAdapter(tour.getPlans(), this);
        rvPlan.setAdapter(adapter);
    }

    @Override
    public void isFavorite(boolean b) {
        if (b){
            ivFavorite.setImageResource(R.drawable.ic_favorite);
        }else {
            ivFavorite.setImageResource(R.drawable.ic_unfarvorite);
        }
    }
}
