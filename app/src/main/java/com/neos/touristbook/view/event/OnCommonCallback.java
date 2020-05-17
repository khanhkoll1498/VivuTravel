package com.neos.touristbook.view.event;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

 abstract public class OnCommonCallback implements Animation.AnimationListener, BottomNavigationBar.OnTabSelectedListener , TextWatcher {
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

     @Override
     public void beforeTextChanged(CharSequence s, int start, int count, int after) {

     }

     @Override
     public void onTextChanged(CharSequence s, int start, int before, int count) {

     }

     @Override
     public void afterTextChanged(Editable s) {

     }
 }
