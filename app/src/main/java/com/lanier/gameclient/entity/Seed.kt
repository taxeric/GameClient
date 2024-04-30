package com.lanier.gameclient.entity

/**
 * Created by 黄瓜
 * Date 2024/2/14 17:36
 */
data class Seed(
    val seedId: Int = -1,

    val name: String = "",

    val type: Int = -1,

    val season : Int = 0,

    /**
     * 最大收获次数
     */
    val seedMaxHarvestCount : Int = 0,

    /**
     * 预计收获获得的总经验值 = (单词收获的数量 * 单个作物收获的经验值) * 最大收获次数
     */
    val totalHarvestExp : Int = 0,

    /**
     * 单个成熟作物收获的经验值
     */
    val harvestExp : Int = 0,

    /**
     * 单次收获的作物数量
     */
    val harvestAmount : Int = 0,

    /**
     * 阶段序列
     * 内容为{stageName:[阶段1, 阶段2, 阶段3],stageSustainTime:[1800, 1800, 1800]}, 分别表示阶段名称 & 阶段持续时间, 数组长度一一对应
     */
    val stageInfo: String = "",
) {

    var seedStageInfo: SeedStageInfo? = null
}
