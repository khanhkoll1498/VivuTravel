package com.kna.touristbook.view.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kna.touristbook.App;
import com.kna.touristbook.StorageCommon;

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {
    private View rootView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.rootView = itemView;
        initView();
    }

    protected abstract void initView();

    protected StorageCommon getStorageCommon() {
        return App.getInstance().getStorageCommon();
    }

}
