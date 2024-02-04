package com.lanier.gameclient.net

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

/**
 * Created by 幻弦让叶
 * Date 2024/1/28 18:35
 */
object APIHelper {

    val client = OkHttpClient.Builder()
        .build()

    inline fun <reified T> get(
        url: String,
        crossinline onSuccess: (T) -> Unit,
        crossinline onFailure: () -> Unit,
    ) {
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    onFailure.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    val jacksonMapper = jacksonObjectMapper()
                    val result = response.body?.string()
                    result?.let {
                        onSuccess.invoke(jacksonMapper.readValue(it, T::class.java))
                    }?: onFailure.invoke()
                }
            })
    }

    inline fun <reified T> post(
        url: String,
        body: RequestBody? = null,
        crossinline onSuccess: (T) -> Unit,
        crossinline onFailure: () -> Unit,
    ) {
        val request = Request.Builder()
            .url(url)
            .method("POST", body)
            .build()
        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    onFailure.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    val jacksonMapper = jacksonObjectMapper()
                    val result = response.body?.string()
                    result?.let {
                        onSuccess.invoke(jacksonMapper.readValue(it, T::class.java))
                    }?: onFailure.invoke()
                }
            })
    }

    suspend inline fun <reified T> getSync(
        url: String,
    ) : T? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful.not()) {
            return@withContext null
        }
        val body = response.body?.string()
        if (body.isNullOrEmpty()) {
            return@withContext null
        }
        return@withContext jacksonObjectMapper().readValue<T>(body, T::class.java)
    }

    suspend inline fun <reified T> postSync(
        url: String,
        body: RequestBody?
    ) : T? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(url)
            .method("POST", body)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful.not()) {
            return@withContext null
        }
        val respBody = response.body?.string()
        if (respBody.isNullOrEmpty()) {
            return@withContext null
        }
        return@withContext jacksonObjectMapper().readValue<T>(respBody, T::class.java)
    }
}