package com.lanier.gameclient.base

import android.app.Application

/**
 * Created by 幻弦让叶
 * Date 2024/3/2 16:52
 */
object ActivityManager {

    val lifecycleListener by lazy { ActivityLifecycleListener() }

    val topAct
        get() = lifecycleListener.topActivity?.get()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(lifecycleListener)
    }
}