package com.neos.touristbook.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.view.base.BaseAdapter;
import com.neos.touristbook.view.base.BaseViewHolder;

import java.util.List;

import static java.security.AccessController.getContext;

public class TourAdapter extends BaseAdapter {
    public static final String KEY_CLICK_TOUR = "KEY_CLICK_TOUR";
    private final DisplayMetrics displayMetrics;
    private List<Tour> mList;
    private boolean isPreview = true;

    public TourAdapter(List<Tour> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
        displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
    }

    @Override
    protected RecyclerView.ViewHolder viewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tour, parent, false);
        return new MyViewHolder(view);
    }

    private void strikeThroughText(TextView price) {
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        strikeThroughText(holder.tvOldPrice);
        if (!isPreview) {
            holder.itemView.getLayoutParams().width = displayMetrics.widthPixels / 2 - 16;
            holder.itemView.requestLayout();
        }
    }

    @Override
    protected int getCount() {
        return 20;
    }

    public void updateList(List<Tour> soundList) {
        this.mList = soundList;
        notifyDataSetChanged();
    }

    public void isPreview(boolean b) {
        this.isPreview = b;
    }


    class MyViewHolder extends BaseViewHolder implements View.OnClickListener {
        private TextView tvOldPrice;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            tvOldPrice = findViewById(R.id.tv_old_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallback.callback(KEY_CLICK_TOUR, itemView.getTag());
        }
    }
}
