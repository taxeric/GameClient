package com.lanier.gameclient.entity.dto

/**
 * Created by 幻弦让叶
 * Date 2024/4/4 21:57
 */
data class PlantDto(
    val petId: Int,
    val landId: Int,
    val bpkId: Int? = null,
    val seedId: Int? = null,
)