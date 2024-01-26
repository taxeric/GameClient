package com.lanier.gameclient.message

/**
 * Created by 幻弦让叶
 * Date 2024/1/26 0:44
 */
data class WebSocketMessage(
    @MessageType val type: String = MSG_TYPE_COMMON,
    @MessageAction val action: Int = MSG_ACTION_NONE,
    val text: String? = null,
) {

    companion object {
        const val MSG_TYPE_COMMON = "common"
        const val MSG_TYPE_PACKAGE = "pkcg"
        const val MSG_TYPE_UNHANDLE = "unhandle"

        const val MSG_ACTION_NONE = 0
        const val MSG_ACTION_CLOSE = 1
        const val MSG_ACTION_CONNECTING = 2
        const val MSG_ACTION_OPEN = 3
    }
}