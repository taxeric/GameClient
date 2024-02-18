package org.lanier.gameserve2.entity

/**
 * Created by 黄瓜
 * Date 2024/2/14 17:36
 */
data class Crop(
    val cropId: Int = 0,

    val type: Int = 1,

    val name: String = "",

    /**
     * 最大可收成次数
     */
    val maxHarvestCount: Int = 1,

    /**
     * 单个成熟作物收获的经验值
     */
    val harvestExp: Int = 0,

    /**
     * 单次收获的作物数量
     */
    val harvestAmount: Int = 0,

    /**
     * 售价
     */
    val sellPrice: Int = 0,

    /**
     * 阶段序列
     *
     * 内容为{stageName:[阶段1, 阶段2, 阶段3],stageSustainTime:[1800, 1800, 1800]}, 分别表示阶段名称 & 阶段持续时间, 数组长度一一对应
     */
    val stageInfo: String = ""
)
