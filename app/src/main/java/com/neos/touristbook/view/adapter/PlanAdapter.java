package com.neos.touristbook.view.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.model.Plan;
import com.neos.touristbook.view.base.BaseAdapter;
import com.neos.touristbook.view.base.BaseViewHolder;

import java.util.List;

public class PlanAdapter extends BaseAdapter {
    private List<Plan> mList;

    public PlanAdapter(List<Plan> mList, Context context) {
        this.mList = mList;
        this.mContext = context;

    }

    @Override
    protected RecyclerView.ViewHolder viewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_plan, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    protected void onBindView(RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        Plan plan = mList.get(position);
        holder.tvTitle.setText(Html.fromHtml("<b>"+plan.getHeader().replace(":",":</b>")));
        holder.tvContent.setText(plan.getContent());
    }

    @Override
    protected int getCount() {
        return mList.size();
    }


    class MyViewHolder extends BaseViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvContent;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
