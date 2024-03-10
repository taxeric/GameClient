package com.lanier.gameclient

import android.app.Application
import com.lanier.gameclient.base.ActivityLifecycleListener
import com.lanier.gameclient.base.ActivityManager
import com.lanier.gameclient.client.OkWebSocketClientManager
import com.lanier.gameclient.utils.Cube

/**
 * Created by 幻弦让叶
 * Date 2024/1/27 21:03
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ActivityManager.init(this)
        OkWebSocketClientManager.init()
        Cube.onCreate(this)
    }
}