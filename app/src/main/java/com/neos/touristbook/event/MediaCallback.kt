package com.neos.touristbook.event

import com.neos.touristbook.model.Image

interface MediaCallback : OnCallback {
    fun onResultMedia(mediaList: MutableList<Image>) {

    }

}
