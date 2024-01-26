package com.lanier.gameclient.ext

import com.lanier.gameclient.flowbus.FlowBus

/**
 * Created by 幻弦让叶
 * Date 2024/1/26 0:30
 */
inline fun <reified T> T.post() {
    post(T::class.java.simpleName, this)
}

inline fun <reified T> T.post(value: T) {
    post(T::class.java.simpleName, value)
}

fun <T> T.post(key: String, value: T) {
    FlowBus.post(key, value)
}

inline fun <reified T> collect(
    crossinline action: (T) -> Unit
) {
    FlowBus.each<T>(T::class.java.simpleName) {
        action.invoke(it)
    }
}

suspend inline fun <reified T> collect(
    key: String,
    crossinline action: (T) -> Unit
) {
    FlowBus.collect<T>(key) {
        action.invoke(it)
    }
}
