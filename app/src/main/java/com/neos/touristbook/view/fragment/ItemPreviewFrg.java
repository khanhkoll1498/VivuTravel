package com.neos.touristbook.view.fragment;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.neos.touristbook.R;
import com.neos.touristbook.view.base.BaseFragment;

public class ItemPreviewFrg extends BaseFragment {
    private String image;
    private ImageView ivPreview;

    public ItemPreviewFrg(String image) {
        this.image = image;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_preview;
    }

    @Override
    protected void initView() {
        ivPreview = (ImageView) findViewById(R.id.iv_preview);
        Glide.with(getContext()).load(image).into(ivPreview);
    }
}
