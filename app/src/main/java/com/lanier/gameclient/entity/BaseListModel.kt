package com.lanier.gameclient.entity

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * Created by 幻弦让叶
 * Date 2024/3/1 23:38
 */
class BaseListModel<D> {
    var hasNext: Boolean = false
    var list: List<D> = emptyList()
    @JsonIgnore
    var total: Int = 0
}