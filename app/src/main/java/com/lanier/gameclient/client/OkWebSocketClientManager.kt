package com.lanier.gameclient.client

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

/**
 * Created by 幻弦让叶
 * Date 2024/1/27 20:56
 */
object OkWebSocketClientManager {

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .pingInterval(10, TimeUnit.SECONDS)
            .build()
    }

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