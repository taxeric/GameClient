package com.lanier.gameclient.entity

/**
 * Created by 幻弦让叶
 * Date 2024/2/25 3:04
 */
data class SeedStageInfo(
    /**
     * 当前阶段名称
     */
    val stageName: List<String> = emptyList(),

    /**
     * 到下一阶段的时间, 也是本阶段持续时间, 单位s
     */
    val stageSustainTime: List<Int> = emptyList(),
) {

    fun maxStageSize(): Int {
        if (stageName.size != stageSustainTime.size) {
            return -1
        }
        return stageName.size
    }

    fun getStageInfo(index: Int): Pair<String, Int> {
        require(!(index < 0 || stageName.isEmpty())) { "empty data" }
        return Pair(stageName[index], stageSustainTime[index])
    }
}
