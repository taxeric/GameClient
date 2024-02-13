package com.lanier.gameclient.module.edit_pet

import androidx.activity.viewModels
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.databinding.ActivityEditPetBinding
import com.lanier.gameclient.ext.disabled
import com.lanier.gameclient.ext.enabled
import com.lanier.gameclient.ext.startAct
import com.lanier.gameclient.ext.toast
import com.lanier.gameclient.module.main.MainAct

class EditPetAct(
    override val layoutId: Int = R.layout.activity_edit_pet
) : BaseAct<ActivityEditPetBinding>() {

    private val vm by viewModels<EditPetVM>()

    override fun onBind() {
        viewbinding.viewmodel = vm
        viewbinding.btnOK.setOnClickListener {
            vm.create(
                onSuccess = {
                    startAct<MainAct> { }
                    finish()
                },
                onFailed = { toast(it) },
                onStart = {
                    viewbinding.btnOK.disabled()
                },
                onCompleted = {
                    viewbinding.btnOK.enabled()
                }
            )
        }
    }
}