package com.lanier.gameclient.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.lanier.gameclient.module.dialog.CommonLoading

abstract class BaseAct<VB : ViewBinding> : AppCompatActivity() {

    protected val viewbinding : VB  by lazy {
        DataBindingUtil.setContentView(this, layoutId)
    }

    private var loadingDialog : CommonLoading? = null

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBind()
        initData()
    }

    protected open fun onBind() {}

    protected open fun initData() {}

    protected open fun showLoading() {
        loadingDialog?.dismiss()
        loadingDialog = CommonLoading.newInstance()
        loadingDialog?.show(supportFragmentManager, CommonLoading::class.java.simpleName)
    }

    protected open fun dismissLoading() {
        loadingDialog?.dismiss()
    }
}