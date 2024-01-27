package com.lanier.gameclient.client

import com.lanier.gameclient.ext.collect
import com.lanier.gameclient.message.WebSocketMessage
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

/**
 * Created by 幻弦让叶
 * Date 2024/1/27 20:56
 */
object OkWebSocketClientManager {

    init {
        collect<WebSocketMessage> {
            if (it.action == WebSocketMessage.MSG_ACTION_OPEN) {
                isConnected = true
            }
            if (it.action == WebSocketMessage.MSG_ACTION_CLOSE) {
                isConnected = false
            }
        }
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .pingInterval(10, TimeUnit.SECONDS)
            .build()
    }

    var isConnected : Boolean = false
        private set

    private var webSocket: WebSocket? = null

    fun newWebSocket(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()
        webSocket = client.newWebSocket(request, OkWebSocketClientListener())
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "exit")
        webSocket = null
    }
}