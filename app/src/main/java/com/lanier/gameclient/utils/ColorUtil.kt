package com.lanier.gameclient.utils

import android.graphics.Color
import kotlin.random.Random

object ColorUtil {

    fun randomColor() : Int {
        val random = Random(System.currentTimeMillis())
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)
        return Color.argb(255, r, g, b)
    }
}