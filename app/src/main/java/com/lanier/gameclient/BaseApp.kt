package com.lanier.gameclient

import android.app.Application
import com.lanier.gameclient.client.OkWebSocketClientManager
import com.lanier.gameclient.net.APIHelper

/**
 * Created by 幻弦让叶
 * Date 2024/1/27 21:03
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        OkWebSocketClientManager.init()
        APIHelper.init()
    }
}