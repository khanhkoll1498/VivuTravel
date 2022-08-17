package com.kna.touristbook.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kna.touristbook.R;
import com.kna.touristbook.model.Tour;
import com.kna.touristbook.view.base.BaseActivity;
import com.kna.touristbook.view.base.BaseAdapter;
import com.kna.touristbook.view.base.BaseViewHolder;

import java.util.List;


public class TourAdapter extends BaseAdapter {
    public static final String KEY_CLICK_ITEM = "KEY_CLICK_ITEM";
    public static final String BASE_URL = "https://www.saigontourist.net/";
    private List<Tour> mList;
    private boolean isPreview = true;

    public TourAdapter(List<Tour> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
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
        Tour item = mList.get(position);
        if (!isPreview) {
            holder.itemView.getLayoutParams().width = BaseActivity.displayMetrics.widthPixels / 2 - 16;
            holder.itemView.requestLayout();
        }
        holder.tvPrice.setText(item.getPrice());
        holder.tvTourName.setText(item.getTitle());
        Glide.with(mContext).load(item.getImageList().get(0).getImage()).into(holder.ivPreview);
        holder.itemView.setTag(item);
    }

    @Override
    protected int getCount() {
        return mList.size();
    }

    public void updateList(List<Tour> soundList) {
        this.mList = soundList;
        notifyDataSetChanged();
    }

    public void isPreview(boolean b) {
        this.isPreview = b;
    }


    class MyViewHolder extends BaseViewHolder implements View.OnClickListener {

        private TextView tvPrice;
        private TextView tvTourName;
        private ImageView ivPreview;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            itemView.setOnClickListener(this);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTourName = itemView.findViewById(R.id.tv_tour_name);
            ivPreview = itemView.findViewById(R.id.iv_preview);
        }

        @Override
        public void onClick(View v) {
            mCallback.callback(KEY_CLICK_ITEM, itemView.getTag());
        }
    }
}
