package com.kna.touristbook.view.dialog

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.kna.touristbook.R
import com.kna.touristbook.event.MediaCallback
import com.kna.touristbook.model.Image
import com.kna.touristbook.presenter.MediaPresenter
import com.kna.touristbook.view.adapter.MediaAdapter
import com.kna.touristbook.view.adapter.MediaAdapter.Companion.KEY_CLICK_MEDIA
import com.kna.touristbook.view.base.BaseDialog
import com.kna.touristbook.view.event.OnActionCallback
import kotlinx.android.synthetic.main.dialog_pick_image.*

class PickImageDialog(context: Context, style: Int) : BaseDialog<MediaPresenter>(context, style),
        MediaCallback, OnActionCallback {
    override fun initPresenter() {
        mPresenter = MediaPresenter(this)
    }

    companion object {
         val KEY_AVATAR = "KEY_AVATAR"
    }

    override fun initView() {
        mList = ArrayList()
        iv_back.setOnClickListener(this)
        rv_media.layoutManager = GridLayoutManager(context, 4)
        adapter = MediaAdapter(mList, context)
        adapter.setmCallback(this)
        rv_media.adapter = adapter
        mPresenter?.loadPhotoList()
    }

    override fun onClick(v: View) {
        dismiss()
    }

    private lateinit var mList: MutableList<Image>
    private lateinit var adapter: MediaAdapter

    override fun onResultMedia(photoList: MutableList<Image>) {
        mList.clear()
        mList.addAll(photoList)
        adapter.notifyDataSetChanged()
    }

    override fun callback(key: String?, data: Any?) {
        if (key.equals(KEY_CLICK_MEDIA)) {
            val media = data as Image
            mCallback?.callback(KEY_AVATAR, media)
            dismiss()
        }
    }


    override fun onBackPressed() {
        dismiss()
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_pick_image
    }
}