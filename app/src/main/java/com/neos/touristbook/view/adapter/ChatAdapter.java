package com.neos.touristbook.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neos.touristbook.R;
import com.neos.touristbook.model.Message;
import com.neos.touristbook.view.base.BaseAdapter;
import com.neos.touristbook.view.base.BaseViewHolder;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;
    private List<Message> mList;
    private Context context;

    public ChatAdapter(List<Message> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    protected RecyclerView.ViewHolder viewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_left, parent, false);
        if (viewType == TYPE_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_right, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvMessage.setText(mList.get(position).getMessage());
    }

    @Override
    protected int getCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType() == 0 ? TYPE_LEFT : TYPE_RIGHT;
    }

    class ViewHolder extends BaseViewHolder {
        private TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            tvMessage = itemView.findViewById(R.id.tv_message);
        }
    }
}
