package com.lanier.gameclient.ext

import android.content.Context
import android.content.Intent

/**
 * Created by 幻弦让叶
 * Date 2024/1/28 16:09
 */
inline fun <reified T> Context.startAct(
    crossinline action: ((Intent) -> Unit)
) {
    val intent = Intent(this, T::class.java).apply {
        action.invoke(this)
    }
    startActivity(intent)
}
