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

    public <T extends View> T findViewById(int id) {

        return findViewById(id, null, null);
    }

    public <T extends View> T findViewById(int id, View.OnClickListener event) {
        return findViewById(id, event, null);
    }

    public <T extends View> T findViewById(int id, View.OnClickListener event, Typeface typeface) {
        T v = rootView.findViewById(id);
        if (event != null) {
            v.setOnClickListener(event);
        }
        if (v instanceof TextView) {
            if (typeface == null) {
                typeface = App.getInstance().getFontRegular();
            }
            ((TextView) v).setTypeface(typeface);
        }
        return v;
    }

    public <T extends View> T findViewById(int id, Typeface typeface) {
        return findViewById(id, null, typeface);
    }
    protected StorageCommon getStorageCommon() {
        return App.getInstance().getStorageCommon();
    }

}
