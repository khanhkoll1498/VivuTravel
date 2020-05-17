package com.neos.touristbook.view.activity;

import android.Manifest;
import android.content.Intent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.neos.touristbook.R;
import com.neos.touristbook.view.base.BaseActivity;
import com.neos.touristbook.view.event.OnCommonCallback;

import static com.neos.touristbook.view.fragment.AccountFrg.KEY_SIGN_OUT;

public class MainActivity extends BaseActivity {
    private BottomNavigationBar bottomBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    protected void initView() {
        initBottomBar();
        showFragment(TAG[0]);
    }

    private void initBottomBar() {
        bottomBar = findViewById(R.id.bottom_navigation_bar);
        bottomBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home, "Home").setActiveColorResource(R.color.colorLightBlue))
                .addItem(new BottomNavigationItem(R.drawable.ic_tour, "Tour").setActiveColorResource(R.color.colorLightBlue))
                .addItem(new BottomNavigationItem(R.drawable.ic_review, "Review").setActiveColorResource(R.color.colorLightBlue))
                .addItem(new BottomNavigationItem(R.drawable.ic_favourite, "favorite").setActiveColorResource(R.color.colorLightBlue))
                .addItem(new BottomNavigationItem(R.drawable.ic_account, "Account").setActiveColorResource(R.color.colorLightBlue))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomBar.setTabSelectedListener(new OnCommonCallback() {
            @Override
            public void onTabSelected(int position) {
                loadView(position);
            }
        });
    }

    private void loadView(int position) {
        showFragment(TAG[position]);
    }

    @Override
    public void callback(String key, Object data) {
        if (key.equals(KEY_SIGN_OUT)) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginAct.class));
            finish();
        }
    }

}
