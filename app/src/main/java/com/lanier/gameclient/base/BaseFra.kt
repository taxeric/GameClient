package com.lanier.gameclient.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFra<VDB : ViewDataBinding> : Fragment() {

    protected lateinit var viewbinding: VDB

    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewbinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
        initData()
    }

    open protected fun onBind() {}
    open protected fun initData() {}
}