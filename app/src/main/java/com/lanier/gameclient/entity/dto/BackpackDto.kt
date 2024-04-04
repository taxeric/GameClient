package com.lanier.gameclient.entity.dto

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 18:52
 */
data class BackpackDto(
    val bpkId: Int = 0, // 对应背包内的id
    val propId: Int = 0, // 对应道具id
    val name: String = "",
    val effect: String = "",
    val pic: String = "",
    val useLevel: Int = 0,
    val LE: Boolean = false,
    val amount: Int = 0,
    val userId: Int = 0,
    val petId: Int = 0,
    val sellPrice: Int = 0,
)
