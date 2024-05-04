package com.lanier.gameclient.utils

import android.app.Application
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

/**
 * Created by huanggua
 * 2024/5/4 20:43
 *
 * Coil 工具类
 */
object CoilUtil {

    fun init(application: Application) {
        val loader = ImageLoader.Builder(application)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        Coil.setImageLoader(loader)
    }
}