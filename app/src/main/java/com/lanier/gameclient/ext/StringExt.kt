package com.lanier.gameclient.ext

import android.widget.Toast
import com.lanier.gameclient.base.ActivityManager

/**
 * Created by 幻弦让叶
 * Date 2024/3/2 16:49
 */
fun String.toast() {
    ActivityManager.topAct?.let {
        Toast.makeText(it, this, Toast.LENGTH_SHORT).show()
    }
}
