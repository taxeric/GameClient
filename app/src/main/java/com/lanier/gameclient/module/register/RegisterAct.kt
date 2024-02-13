package com.lanier.gameclient.module.register

import androidx.activity.viewModels
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.databinding.ActivityRegisterBinding
import com.lanier.gameclient.ext.disabled
import com.lanier.gameclient.ext.enabled
import com.lanier.gameclient.ext.toast

class RegisterAct(
    override val layoutId: Int = R.layout.activity_register
) : BaseAct<ActivityRegisterBinding>() {

    private val vm by viewModels<RegisterVM>()

    override fun onBind() {
        viewbinding.viewmodel = vm
        viewbinding.btnRegister.setOnClickListener {
            vm.register(
                onStart = {
                    viewbinding.btnRegister.disabled()
                },
                onSuccess = {
                    toast("注册成功 ~ ")
                    finish()
                },
                onFailure = {
                    toast(it)
                },
                onCompleted = {
                    viewbinding.btnRegister.enabled()
                }
            )
        }
        viewbinding.tvBack.setOnClickListener {
            finish()
        }

        vm.error.observe(this) {
            if (it.isNullOrEmpty().not()) {
                toast(it)
            }
        }
    }
}