package com.lanier.gameclient.utils

import android.content.Context
import com.lanier.gameclient.utils.cube.LocalDisplay

object Cube {

    fun onCreate(context: Context) {
        LocalDisplay.init(context)
    }
}