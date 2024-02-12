package com.lanier.gameclient.utils.cube

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object LocalDisplay {

    @JvmField
    var SCREEN_WIDTH_PIXELS = 0

    @JvmField
    var SCREEN_HEIGHT_PIXELS = 0
    var SCREEN_DENSITY = 0f
    var SCREEN_WIDTH_DP = 0
    var SCREEN_HEIGHT_DP = 0

    @JvmStatic
    fun init(context: Context) {
        val dm = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        SCREEN_WIDTH_PIXELS = dm.widthPixels
        SCREEN_HEIGHT_PIXELS = dm.heightPixels
        SCREEN_DENSITY = dm.density
        SCREEN_WIDTH_DP = (SCREEN_WIDTH_PIXELS / dm.density).toInt()
        SCREEN_HEIGHT_DP = (SCREEN_HEIGHT_PIXELS / dm.density).toInt()
    }
}