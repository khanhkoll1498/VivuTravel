package com.neos.touristbook.view.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.App;
import com.neos.touristbook.StorageCommon;
import com.neos.touristbook.view.event.OnActionCallback;

abstract public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected OnActionCallback mCallback;
    protected  Context mContext;

    public void setmCallback(OnActionCallback mCallback) {
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewHolder(parent, viewType);
    }

    protected abstract RecyclerView.ViewHolder viewHolder(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindView(holder, position);
    }

    protected abstract void onBindView(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return getCount();
    }

    protected abstract int getCount();

    protected StorageCommon getStorageCommon() {
        return App.getInstance().getStorageCommon();
    }

}
