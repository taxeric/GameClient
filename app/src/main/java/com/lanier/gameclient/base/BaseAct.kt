package com.lanier.gameclient.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding

abstract class BaseAct<VB : ViewBinding> : AppCompatActivity() {

    protected val viewbinding : VB  by lazy {
        DataBindingUtil.setContentView(this, layoutId)
    }

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBind()
        initData()
    }

    protected open fun onBind() {}

    protected open fun initData() {}
}