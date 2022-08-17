package com.kna.touristbook.event

import com.kna.touristbook.model.Image

interface MediaCallback : OnCallback {
    fun onResultMedia(mediaList: MutableList<Image>) {

    }

}
