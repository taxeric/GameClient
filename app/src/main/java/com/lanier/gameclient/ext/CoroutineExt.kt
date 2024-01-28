package com.lanier.gameclient.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by 幻弦让叶
 * Date 2024/1/27 20:45
 */
fun LifecycleOwner.launchSafe(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    error: ((Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit,
) {
    lifecycleScope.launch(
        context = Dispatchers.Main + CoroutineExceptionHandler { _, throwable -> error?.invoke(throwable) },
        start = start,
    ) {
        block.invoke(this)
    }
}

fun CoroutineScope.launchSafe(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    error: ((Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit,
) {
    launch(
        context = Dispatchers.Main + CoroutineExceptionHandler { _, throwable -> error?.invoke(throwable) },
        start = start,
    ) {
        block.invoke(this)
    }
}

fun ViewModel.launchSafe(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    error: ((Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit,
) {
    viewModelScope.launch(
        context = Dispatchers.Main + CoroutineExceptionHandler { _, throwable -> error?.invoke(throwable) },
        start = start,
    ) {
        block.invoke(this)
    }
}
