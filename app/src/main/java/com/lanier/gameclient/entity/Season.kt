package org.lanier.gameserve2.entity

/**
 * Created by 幻弦让叶
 * Date 2024/2/3 1:06
 */
data class Season(
    val id: Int = NONE,
    val name: String = "",
) {

    companion object {

        /**
         * 春季
         */
        const val SPRING = 1

        /**
         * 夏季
         */
        const val SUMMER = 2

        /**
         * 秋季
         */
        const val AUTUMN = 3

        /**
         * 冬季
         */
        const val WINTER = 4

        /**
         * 无(不区分季节)
         */
        const val NONE = 5
    }
}
