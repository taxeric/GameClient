package com.lanier.gameclient.entity.spirit

/**
 * Created by 黄瓜
 * Date 2024/5/2 12:37
 */
data class SpiritAction(
    val idle: String = "",
    val enter: String = "",
    val exit: String = "",
    val miss: String = "",
    val def: Boolean = false,
) {
    companion object {
        val dd = SpiritAction(def = true)
    }
}
