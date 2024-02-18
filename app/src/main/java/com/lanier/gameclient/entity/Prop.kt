package org.lanier.gameserve2.entity

/**
 * 道具
 *
 * 数据表中[realPropId]和[type]两列确定唯一
 */
data class Prop(
    val propId: Int = 0,

    /**
     * 实际道具id
     */
    val realPropId: Int = 0,

    /**
     * 道具类型
     */
    val type: Int = 0,

    /**
     * 道具名称
     */
    val propName: String = "",

    /**
     * 道具图片链接
     */
    val propPic: String = "",
)
