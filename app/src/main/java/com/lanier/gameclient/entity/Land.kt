package org.lanier.gameserve2.entity

/**
 * Created by 黄瓜
 * Date 2024/2/14 17:40
 */
data class Land(
    val landId: Int = 0,
    val petId: Int = 0,
    val status: Int = UNLOCK,
    val cropId: Int = 0,

    /**
     * 到下一阶段的剩余时间
     */
    val nextStageRemainTime: Long = 0L,

    /**
     * 上次收获时间
     */
    val lastHarvestTime: Long = 0L,

    /**
     * 最大收获次数
     */
    val maxHarvestCount: Int = 1,

    /**
     * 已收获次数
     */
    val harvestCount: Int = 0,
) {

    companion object {

        //未解锁
        const val UNLOCK = 1

        //种植中
        const val PLANTING = 2

        //空闲
        const val IDLE = 3
    }
}
