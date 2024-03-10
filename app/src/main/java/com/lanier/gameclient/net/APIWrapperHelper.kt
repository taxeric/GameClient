package com.lanier.gameclient.net

import android.app.ProgressDialog
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lanier.gameclient.entity.BaseEntity
import com.lanier.gameclient.entity.BaseListModel
import com.lanier.gameclient.ext.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.RequestBody

object APIWrapperHelper {

    inline fun <reified T> get(
        url: String,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFailure: () -> Unit,
    ) {
        APIHelper.getNotHandle(
            url,
            onSuccess = { json ->
                val jacksonMapper = jacksonObjectMapper()
                val type = object : TypeReference<BaseEntity<T>>() {}
                val mData = jacksonMapper.readValue(json, type)
                val result = mData.data
                onSuccess.invoke(result)
            },
            onFailure = onFailure
        )
    }

    inline fun <reified T> post(
        url: String,
        body: RequestBody,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onFailure: (String?) -> Unit,
    ) {
        APIHelper.postNotHandle(
            url,
            body,
            onSuccess = { json ->
                val jacksonMapper = jacksonObjectMapper()
                val type = object : TypeReference<BaseEntity<T>>() {}
                val mData = jacksonMapper.readValue(json, type)
                if (mData.code == BaseEntity.CODE_SUCCESS) {
                    val result = mData.data
                    onSuccess.invoke(result)
                } else {
                    onFailure.invoke(mData.message)
                }
            },
            onFailure = {
                onFailure.invoke("unknown")
            }
        )
    }

    suspend inline fun <reified T> getSync(
        url: String,
        noinline onFailure: ((String) -> Unit)? = null,
    ): T? {
        val json = withContext(Dispatchers.IO) { APIHelper.getSyncNotHandle(url) }
        println(">>>> $json")
        val jacksonMapper = jacksonObjectMapper()
        val type = object : TypeReference<BaseEntity<T>>() {}
        val mData = jacksonMapper.readValue<BaseEntity<T>>(json, type)
        if (mData.code != BaseEntity.CODE_SUCCESS) {
            onFailure?.invoke(mData.message)
            return null
        }
        return mData.data
    }

    suspend inline fun <reified T> getSync(
        url: String,
        requestBody: FormBody,
        noinline onFailure: ((String) -> Unit)? = null,
    ): T? {
        val tempUrl = buildString {
            append(url)
            if (requestBody.size != 0) {
                append("?")
                for (index in 0 until requestBody.size - 1) {
                    append(requestBody.name(index))
                    append("=")
                    append(requestBody.value(index))
                    if (index != requestBody.size - 1) {
                        append("&")
                    }
                }
            }
        }
        val json = withContext(Dispatchers.IO) { APIHelper.getSyncNotHandle(tempUrl) }
        println(">>>> $json")
        val jacksonMapper = jacksonObjectMapper()
        val type = object : TypeReference<BaseEntity<T>>() {}
        val mData = jacksonMapper.readValue(json, type)
        if (mData.code != BaseEntity.CODE_SUCCESS) {
            onFailure?.invoke(mData.message)
            return null
        }
        return mData.data
    }

    suspend inline fun <reified T> postSync(
        url: String,
        requestBody: RequestBody? = null,
        noinline onFailure: ((String) -> Unit)? = null,
    ): T? {
        val json = withContext(Dispatchers.IO) { APIHelper.postSyncNotHandle(url, requestBody) }
        println(">>>> $json")
        val jacksonMapper = jacksonObjectMapper()
        val type = object : TypeReference<BaseEntity<T>>() {}
        val mData = jacksonMapper.readValue(json, type)
        if (mData.code != BaseEntity.CODE_SUCCESS) {
            onFailure?.invoke(mData.message)
            return null
        }
        return mData.data
    }

    suspend inline fun <reified T> postSync(
        url: String,
        requestBody: RequestBody? = null,
    ): BaseEntity<T> {
        val json = withContext(Dispatchers.IO) { APIHelper.postSyncNotHandle(url, requestBody) }
        println(">>>> $json")
        val jacksonMapper = jacksonObjectMapper()
        val type = object : TypeReference<BaseEntity<T>>() {}
        return jacksonMapper.readValue(json, type)
    }

    inline fun <reified T> combineType(jacksonMapper: ObjectMapper): JavaType {
        return jacksonMapper.typeFactory.constructParametricType(
            BaseEntity::class.java,
            T::class.java
        )
    }
}