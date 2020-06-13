package com.neos.touristbook.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neos.touristbook.R
import com.neos.touristbook.model.Image
import com.neos.touristbook.view.base.BaseAdapter
import com.neos.touristbook.view.base.BaseViewHolder

class MediaAdapter(var mList: MutableList<Image>, var context: Context) : BaseAdapter() {
    companion object {
        val KEY_CLICK_MEDIA: String = "KEY_CLICK_MEDIA"
    }

    var isLongClick: Boolean = false

    override fun viewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_media, parent, false)
        return MyViewHolder(v)
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun onBindView(viewHolder: RecyclerView.ViewHolder?, position: Int) {
        val item: Image = mList.get(position)
        val holder = viewHolder as MyViewHolder
        holder.itemView.tag = item
        Glide.with(context).load(item.image).fitCenter().into(holder.ivMedia)
    }


    inner class MyViewHolder(rootView: View) : BaseViewHolder(rootView), View.OnClickListener {
        lateinit var ivMedia: ImageView
        lateinit var ivCheck: ImageView

        override fun initView() {
            itemView.setOnClickListener(this)
            ivMedia = itemView.findViewById(R.id.iv_media)
            ivCheck = itemView.findViewById(R.id.iv_check)
        }

        override fun onClick(v: View) {
            mCallback?.callback(KEY_CLICK_MEDIA, itemView.tag)
        }

    }
}