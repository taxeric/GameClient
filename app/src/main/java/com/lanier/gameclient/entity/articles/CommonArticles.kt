package com.lanier.gameclient.entity.articles

/**
 * 通用物件
 */
open class CommonArticles {

    /**
     * 名称
     */
    var name: String = ""

    /**
     * 效果
     */
    var effect: String = ""

    /**
     * 价格
     */
    var cost: Int = 0

    /**
     * 造成影响的值
     */
    var value: Int = 0

    /**
     * 所属季节
     */
    var season: Int = 0

    /**
     * 图片链接
     */
    var pic: String = ""

    /**
     * 使用等级
     */
    var useLevel: Int = 0

    /**
     * 是否限定
     */
    var LE: Boolean = false
}