package com.neos.touristbook.presenter

import android.annotation.SuppressLint
import android.os.Environment
import android.os.Message
import com.neos.touristbook.event.MediaCallback
import com.neos.touristbook.model.Image
import java.io.File


class MediaPresenter(mCallBack: MediaCallback) :
        BasePresenter<MediaCallback>(mCallBack) {
    private val KEY_MEDIA_LIST: String = "KEY_MEDIA_LIST"

    private val UPDATE_MEDIA_LIST: Int = 101
    private lateinit var photoList: MutableList<Image>


    override fun excuteHandler(msg: Message) {
        if (msg.what == UPDATE_MEDIA_LIST) {
            mCallback.onResultMedia(photoList)
        }
    }

    @SuppressLint("Recycle")
    fun loadPhotoList() {
        photoList = ArrayList()
        Thread(Runnable {
            val path = Environment.getExternalStorageDirectory().toString()
            val file = File(path)
            loadAllPhoto(file)
            updateHandler(UPDATE_MEDIA_LIST)
        }).start()
    }

    private fun loadAllPhoto(f: File?) {
        val files: Array<File> = f?.listFiles()!!
        for (file in files) {
            if (photoList.size == 150) {
                return
            }
            if (file.isDirectory()) {
                loadAllPhoto(file)
                continue
            }
            if (isPhoto(file)) {
                photoList.add(Image(file.path))
            }
        }
    }

    private fun isPhoto(file: File): Boolean {
        if (file.name.toLowerCase().endsWith(".jpg")) {
            return true
        }
        return false
    }

}
