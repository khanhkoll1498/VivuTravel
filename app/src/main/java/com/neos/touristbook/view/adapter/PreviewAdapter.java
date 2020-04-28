package com.neos.touristbook.view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.neos.touristbook.view.base.BaseFragment;
import com.neos.touristbook.view.event.OnActionCallback;
import com.neos.touristbook.view.fragment.ItemPreviewFrg;

public class PreviewAdapter extends FragmentPagerAdapter implements OnActionCallback {

    private String[] previewList;

    public PreviewAdapter(FragmentManager fm, String[] previewList) {
        super(fm);
        this.previewList = previewList;
    }

    private OnActionCallback mCallback;

    public void setmCallback(OnActionCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return previewList.length;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = new ItemPreviewFrg(previewList[position]);
        fragment.setmCallback(this);
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    public void updateList(String[] previewList) {
        this.previewList = previewList;
        notifyDataSetChanged();
    }



    @Override
    public void callback(String key, Object data) {

    }
}
