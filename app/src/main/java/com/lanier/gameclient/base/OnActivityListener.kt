package com.lanier.gameclient.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

class OnActivityListener : Application.ActivityLifecycleCallbacks {

    private var foreActivityCount : Int = 0
    private var topActivity: WeakReference<Activity>? = null

    private val lifecycleListeners = mutableListOf<OnLifecycleListener>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        foreActivityCount ++
        if (foreActivityCount == 1) {
            lifecycleListeners.forEach {
                it.onAppToForeground()
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        topActivity = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        foreActivityCount --
        if (foreActivityCount == 0) {
            lifecycleListeners.forEach {
                it.onAppToBackground()
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    interface OnLifecycleListener {

        fun onAppToForeground()

        fun onAppToBackground()
    }
}