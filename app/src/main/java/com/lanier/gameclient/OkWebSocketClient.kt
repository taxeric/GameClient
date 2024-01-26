package com.lanier.gameclient

import com.lanier.gameclient.ext.post
import com.lanier.gameclient.message.WebSocketMessage
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

/**
 * Created by 幻弦让叶
 * Date 2024/1/25 23:53
 */
object OkWebSocketClient : WebSocketListener() {

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        WebSocketMessage(
            action = WebSocketMessage.MSG_ACTION_CLOSE
        ).post()
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        WebSocketMessage(
            text = text
        ).post()
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        WebSocketMessage(
            action = WebSocketMessage.MSG_ACTION_OPEN
        ).post()
    }
}