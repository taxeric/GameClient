package com.lanier.gameclient.message

import androidx.annotation.StringDef

/**
 * Created by 幻弦让叶
 * Date 2024/1/26 0:48
 */
@StringDef(
    WebSocketMessage.MSG_TYPE_COMMON,
    WebSocketMessage.MSG_TYPE_PACKAGE,
    WebSocketMessage.MSG_TYPE_UNHANDLE,
)
annotation class MessageType()
