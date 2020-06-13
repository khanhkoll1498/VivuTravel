package com.neos.touristbook.view.base;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.App;
import com.neos.touristbook.StorageCommon;

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
