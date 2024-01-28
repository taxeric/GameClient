package com.lanier.gameclient.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

/**
 * Created by 幻弦让叶
 * Date 2024/1/28 18:35
 */
object APIHelper {

    private lateinit var client : OkHttpClient

    fun init() {
        client = OkHttpClient.Builder()
            .build()
    }

    suspend fun request(url: String, method: String, body: RequestBody? = null) : Response {
        val request = Request.Builder()
            .url(url)
            .method(method, body)
            .build()
        val response = withContext(Dispatchers.Default) {
            client.newCall(request).execute()
        }
        return response
    }
}