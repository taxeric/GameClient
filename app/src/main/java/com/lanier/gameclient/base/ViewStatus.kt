package com.lanier.gameclient.base

/**
 * Created by 幻弦让叶
 * Date 2024/3/2 17:26
 */
sealed interface ViewStatus {
    data object Loading: ViewStatus
    data object Completed: ViewStatus
}