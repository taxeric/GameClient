package com.lanier.gameclient.message

import androidx.annotation.IntDef

/**
 * Created by 幻弦让叶
 * Date 2024/1/26 0:52
 */
@IntDef(
    WebSocketMessage.MSG_ACTION_NONE,
    WebSocketMessage.MSG_ACTION_OPEN,
    WebSocketMessage.MSG_ACTION_CONNECTING,
    WebSocketMessage.MSG_ACTION_CLOSE,
)
annotation class MessageAction()
