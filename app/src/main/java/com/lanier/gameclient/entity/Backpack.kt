package com.lanier.gameclient.entity

import com.lanier.gameclient.utils.ColorUtil

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 18:52
 */
data class Backpack(
    val bpkId: Int = 0, // 对应背包内的id
    val realPropId: Int = 0, // 对应道具id
    val userId: Int = 0,
    val petId: Int = 0,
    val name: String = "",
    val pic: String = "",
    val useLevel: Int = 0,
    val le: Boolean = false,
    val amount: Int = 0,
    val effect: String = "",
    val effectValue: Int? = 0,
    val sellPrice: Int = 0,
) {

    fun amountStr() = if (amount > 99) "99+" else "$amount"

    fun randomPIC() = ColorUtil.randomColor()
}
