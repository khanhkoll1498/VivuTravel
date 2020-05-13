package com.neos.touristbook.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.model.Review;
import com.neos.touristbook.view.base.BaseAdapter;
import com.neos.touristbook.view.base.BaseViewHolder;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {
    public static final String KEY_CLICK_TOUR = "KEY_CLICK_TOUR";
    private List<Review> mList;

    public ReviewAdapter(List<Review> mList, Context context) {
        this.mList = mList;
        this.mContext = context;

    }

    @Override
    protected RecyclerView.ViewHolder viewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_review, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    protected void onBindView(RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
    }

    @Override
    protected int getCount() {
        return 6;
    }

    public void updateList(List<Review> soundList) {
        this.mList = soundList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends BaseViewHolder implements View.OnClickListener {
//        private TextView tvOldPrice;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
//            tvOldPrice = findViewById(R.id.tv_old_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallback.callback(KEY_CLICK_TOUR, itemView.getTag());
        }
    }
}
