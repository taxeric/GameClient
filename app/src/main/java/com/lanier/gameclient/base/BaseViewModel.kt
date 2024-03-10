package com.lanier.gameclient.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lanier.gameclient.ext.toast
import com.lanier.gameclient.net.APIWrapperHelper
import com.lanier.gameclient.net.AppNetErr
import okhttp3.RequestBody

/**
 * Created by 幻弦让叶
 * Date 2024/3/2 17:24
 */
open class BaseViewModel : ViewModel() {

    protected val _viewStatus = MutableLiveData<ViewStatus>()
    val viewStatus : LiveData<ViewStatus> = _viewStatus

    suspend inline fun <reified T> successCatch(
        url: String,
        requestBody: RequestBody? = null,
        showToast: Boolean = true,
        showLoading: Boolean = true,
    ): T? {
        val result = kotlin.runCatching { mapResp<T>(url, requestBody) }
            .onSuccess {  }
            .onFailure { if (showToast) it.message?.toast() }
        if (result.isSuccess) {
            return result.getOrNull()
        }
        return null
    }

    suspend inline fun <reified T> mapResp(
        url: String,
        requestBody: RequestBody? = null,
    ): T? {
        val result = APIWrapperHelper.postSync<T>(url, requestBody)
        if (result.isSuccess()) {
            return result.getResponse()
        }
        throw AppNetErr(result.message)
    }

    protected fun setStatus(
        status: ViewStatus? = null
    ) {
        status?.let {
            _viewStatus.value = it
        }
    }
}