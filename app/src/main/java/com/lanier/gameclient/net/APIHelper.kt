package com.lanier.gameclient.net

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

/**
 * Created by 幻弦让叶
 * Date 2024/1/28 18:35
 */
object APIHelper {

    val client by lazy {
        OkHttpClient.Builder()
            .build()
    }

    var commonScheme = ""

    fun buildUrl(url: String, builder: Request.Builder? = null) : Request.Builder {
        if (builder == null) {
            return Request.Builder().url("$commonScheme$url")
        }
        return builder.url("$commonScheme$url")
    }

    inline fun <reified T> get(
        url: String,
        crossinline onSuccess: (T) -> Unit,
        crossinline onFailure: () -> Unit,
    ) {
        val request = buildUrl(url)
            .build()
        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    onFailure.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful.not()) {
                        onFailure.invoke()
                    }
                    val jacksonMapper = jacksonObjectMapper()
                    val result = response.body?.string()
                    result?.let {
                        onSuccess.invoke(jacksonMapper.readValue(it, T::class.java))
                    }?: onFailure.invoke()
                }
            })
    }

    inline fun getNotHandle(
        url: String,
        crossinline onSuccess: (String) -> Unit,
        crossinline onFailure: () -> Unit,
    ) {
        val request = buildUrl(url)
            .build()
        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    onFailure.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful.not()) {
                        onFailure.invoke()
                    }
                    val result = response.body?.string()
                    result?.let {
                        onSuccess.invoke(it)
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
        val request = buildUrl(url)
            .method("POST", body)
            .build()
        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    onFailure.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful.not()) {
                        onFailure.invoke()
                    }
                    val jacksonMapper = jacksonObjectMapper()
                    val result = response.body?.string()
                    result?.let {
                        onSuccess.invoke(jacksonMapper.readValue(it, T::class.java))
                    }?: onFailure.invoke()
                }
            })
    }

    inline fun postNotHandle(
        url: String,
        body: RequestBody? = null,
        crossinline onSuccess: (String) -> Unit,
        crossinline onFailure: () -> Unit,
    ) {
        val request = buildUrl(url)
            .method("POST", body)
            .build()
        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    onFailure.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful.not()) {
                        onFailure.invoke()
                    }
                    val result = response.body?.string()
                    println(">>>> $result ${Thread.currentThread().name}")
                    result?.let {
                        onSuccess.invoke(it)
                    }?: onFailure.invoke()
                }
            })
    }

    suspend inline fun <reified T> getSync(
        url: String,
    ) : T? = withContext(Dispatchers.IO) {
        val request = buildUrl(url)
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

    suspend inline fun getSyncNotHandle(
        url: String,
    ) : String? = withContext(Dispatchers.IO) {
        val request = buildUrl(url)
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful.not()) {
            return@withContext null
        }
        val body = response.body?.string()
        if (body.isNullOrEmpty()) {
            return@withContext null
        }
        return@withContext body
    }

    suspend inline fun <reified T> postSync(
        url: String,
        body: RequestBody?
    ) : T? = withContext(Dispatchers.IO) {
        val request = buildUrl(url)
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

    suspend inline fun postSyncNotHandle(
        url: String,
        body: RequestBody?
    ) : String? = withContext(Dispatchers.IO) {
        val request = buildUrl(url)
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
        return@withContext respBody
    }

    suspend inline fun postJsonSyncNotHandle(
        url: String,
        body: RequestBody
    ) : String? = withContext(Dispatchers.IO) {
        val request = buildUrl(url)
            .addHeader("Content-Type", "application/json")
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
        return@withContext respBody
    }
}