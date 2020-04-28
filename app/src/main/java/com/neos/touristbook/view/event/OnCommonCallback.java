package com.neos.touristbook.view.event;

import android.view.animation.Animation;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

abstract public class OnCommonCallback implements Animation.AnimationListener, BottomNavigationBar.OnTabSelectedListener {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onTabSelected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
