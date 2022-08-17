package com.kna.touristbook.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kna.touristbook.R;
import com.kna.touristbook.model.Review;
import com.kna.touristbook.view.base.BaseAdapter;
import com.kna.touristbook.view.base.BaseViewHolder;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {
    public static final String KEY_CLICK_ITEM = "KEY_CLICK_TOUR";
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
        Review review = mList.get(position);
        Glide.with(mContext).load(review.getImageList().get(0).getImage()).into(holder.ivPreview);
        holder.tvTitle.setText(review.getTitle());

        holder.itemView.setTag(review);
    }

    @Override
    protected int getCount() {
        return mList.size();
    }

    public void updateList(List<Review> soundList) {
        this.mList = soundList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends BaseViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private ImageView ivPreview;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivPreview = itemView.findViewById(R.id.iv_preview);
        }

        @Override
        public void onClick(View v) {
            mCallback.callback(KEY_CLICK_ITEM, itemView.getTag());
        }
    }
}
