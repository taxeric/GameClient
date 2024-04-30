package com.lanier.gameclient.entity

data class LandCropStageInfo(
    /**
     * 下一阶段的开始时间, 单位ms
     */
    val nextStageAllTime: List<Long> = emptyList()
)