package com.neos.touristbook.view.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.neos.touristbook.R;
import com.neos.touristbook.model.Tour;
import com.neos.touristbook.model.TourOrder;
import com.neos.touristbook.utils.CommonUtils;
import com.neos.touristbook.view.base.BaseActivity;
import com.neos.touristbook.view.base.BaseAdapter;
import com.neos.touristbook.view.base.BaseViewHolder;

import java.util.List;


public class TourOrderAdapter extends BaseAdapter {
    public static final String KEY_CLICK_ITEM = "KEY_CLICK_ITEM";
    private List<TourOrder> mList;
    private boolean isPreview = true;

    public TourOrderAdapter(List<TourOrder> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder viewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tour_order, parent, false);
        return new MyViewHolder(view);
    }

    private void strikeThroughText(TextView price) {
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        TourOrder tourOrder = mList.get(position);
        Tour item = tourOrder.getTour();
        holder.tvPrice.setText(Html.fromHtml(tourOrder.getTotalPrice()));
        holder.tvTourName.setText(item.getTitle());
        Glide.with(mContext).load(item.getImageList().get(0).getImage()).into(holder.ivPreview);
        holder.tvName.setText(Html.fromHtml("<b>Tên</b>: " + tourOrder.getName()));
        holder.tvDate.setText(Html.fromHtml("<b>Ngày dặt Tour</b>: " + CommonUtils.getInstance().formatTimeToDate(tourOrder.getId())));
        holder.tvPhone.setText(Html.fromHtml("<b>SDT</b>: " + tourOrder.getPhone()));
        holder.itemView.setTag(item);
        if (tourOrder.isRate()) {
            holder.tvRate.setText("Đánh giá");
            holder.tvRate.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
        } else {
            holder.tvRate.setText("Hủy tour");
            holder.tvRate.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    @Override
    protected int getCount() {
        return mList.size();
    }


    public void isPreview(boolean b) {
        this.isPreview = b;
    }


    class MyViewHolder extends BaseViewHolder implements View.OnClickListener {

        private TextView tvPrice;
        private TextView tvTourName, tvName, tvPhone, tvDate, tvRate;
        private ImageView ivPreview;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTourName = itemView.findViewById(R.id.tv_tour_name);
            ivPreview = itemView.findViewById(R.id.iv_preview);
            tvRate = itemView.findViewById(R.id.tv_rate);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvRate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallback.callback(KEY_CLICK_ITEM, itemView.getTag());
        }
    }
}
