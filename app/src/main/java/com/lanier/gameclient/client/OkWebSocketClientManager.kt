package com.lanier.gameclient.client

import com.lanier.gameclient.ext.collect
import com.lanier.gameclient.message.WebSocketMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        GlobalScope.launch {
            collect<WebSocketMessage> {
                if (it.action == WebSocketMessage.MSG_ACTION_OPEN) {
                    isConnected = true
                    _currentUrl.tryEmit(currentUrl)
                    _isConnected.tryEmit(true)
                }
                if (it.action == WebSocketMessage.MSG_ACTION_CLOSE) {
                    isConnected = false
                    _currentUrl.tryEmit("")
                    _isConnected.tryEmit(false)
                }
                if (it.action == WebSocketMessage.MSG_ACTION_REQUEST_LINK) {
                    newWebSocket(it.text!!)
                }
                if (it.action == WebSocketMessage.MSG_ACTION_NONE) {
                    _pushMessage.tryEmit(it)
                }
            }
        }
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .pingInterval(10, TimeUnit.SECONDS)
            .build()
    }

    private val clientListener: OkWebSocketClientListener by lazy {
        OkWebSocketClientListener()
    }

    var isConnected : Boolean = false
        private set
    var currentUrl : String = ""
        private set

    private val _isConnected = MutableStateFlow(false)
    val isConnectedFlow : StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _currentUrl = MutableStateFlow("")
    val currentUrlFlow : StateFlow<String> = _currentUrl.asStateFlow()

    private val _pushMessage = MutableStateFlow(WebSocketMessage.DEF)
    val pushMessageFlow : StateFlow<WebSocketMessage> = _pushMessage.asStateFlow()

    private var webSocket: WebSocket? = null

    fun init() {}

    fun newWebSocket(url: String) {
        if (webSocket != null) {
            close()
        }
        this.currentUrl = url
        val request = Request.Builder()
            .url(url)
            .build()
        webSocket = client.newWebSocket(request, clientListener)
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "exit")
        webSocket = null
    }
}