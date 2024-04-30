package com.lanier.gameclient.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lanier.gameclient.ext.toast
import com.lanier.gameclient.net.APIWrapperHelper
import com.lanier.gameclient.net.AppNetErr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.RequestBody

/**
 * Created by 幻弦让叶
 * Date 2024/3/2 17:24
 */
open class BaseViewModel : ViewModel() {

    private val _viewStatus = MutableLiveData<ViewStatus>()
    val viewStatus : LiveData<ViewStatus> = _viewStatus

    suspend inline fun <reified T> successPostCatch(
        url: String,
        requestBody: RequestBody? = null,
        showToast: Boolean = false,
        showLoading: Boolean = true,
    ): T? {
        if (showLoading) {
            withContext(Dispatchers.Main) {
                setStatus(ViewStatus.Loading)
            }
        }
        delay(300)
        val result = kotlin.runCatching { mapPostResp<T>(url, requestBody) }
            .onSuccess {  }
            .onFailure { if (showToast) it.message?.toast() }
        delay(300)
        if (showLoading) {
            withContext(Dispatchers.Main) {
                setStatus(ViewStatus.Completed)
            }
        }
        if (result.isSuccess) {
            return result.getOrNull()
        }
        return null
    }

    suspend inline fun <reified T, D> successPostCatch(
        url: String,
        dto: D,
        showToast: Boolean = false,
        showLoading: Boolean = true,
    ): T? {
        if (showLoading) {
            withContext(Dispatchers.Main) {
                setStatus(ViewStatus.Loading)
            }
        }
        delay(300)
        val result = kotlin.runCatching { mapPostResp<T, D>(url, dto) }
            .onSuccess {  }
            .onFailure { if (showToast) it.message?.toast() }
        delay(300)
        if (showLoading) {
            withContext(Dispatchers.Main) {
                setStatus(ViewStatus.Completed)
            }
        }
        if (result.isSuccess) {
            return result.getOrNull()
        }
        return null
    }

    suspend inline fun <reified T> successGetCatch(
        url: String,
        requestBody: FormBody,
        showToast: Boolean = false,
        showLoading: Boolean = true,
    ): T? {
        if (showLoading) {
            withContext(Dispatchers.Main) {
                setStatus(ViewStatus.Loading)
            }
        }
        delay(300)
        val result = kotlin.runCatching { mapGetResp<T>(url, requestBody) }
            .onSuccess {  }
            .onFailure { if (showToast) it.message?.toast() }
        delay(300)
        if (showLoading) {
            withContext(Dispatchers.Main) {
                setStatus(ViewStatus.Completed)
            }
        }
        if (result.isSuccess) {
            return result.getOrNull()
        }
        return null
    }

    suspend inline fun <reified T> mapGetResp(
        url: String,
        requestBody: FormBody
    ): T? {
        val result = APIWrapperHelper.getSyncWrapper<T>(url, requestBody)
        if (result.isSuccess()) {
            return result.getResponse()
        }
        throw AppNetErr(result.message)
    }

    suspend inline fun <reified T> mapPostResp(
        url: String,
        requestBody: RequestBody? = null,
    ): T? {
        val result = APIWrapperHelper.postSync<T>(url, requestBody)
        if (result.isSuccess()) {
            return result.getResponse()
        }
        throw AppNetErr(result.message)
    }

    suspend inline fun <reified T, D> mapPostResp(
        url: String,
        dto: D,
    ): T? {
        val result = APIWrapperHelper.postSync<T, D>(url, dto)
        if (result.isSuccess()) {
            return result.getResponse()
        }
        throw AppNetErr(result.message)
    }

    fun setStatus(
        status: ViewStatus? = null
    ) {
        status?.let {
            _viewStatus.value = it
        }
    }
}