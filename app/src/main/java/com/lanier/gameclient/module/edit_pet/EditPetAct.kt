package com.lanier.gameclient.module.edit_pet

import androidx.activity.viewModels
import coil.load
import com.github.florent37.viewanimator.ViewAnimator
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.databinding.ActivityEditPetBinding
import com.lanier.gameclient.ext.disabled
import com.lanier.gameclient.ext.enabled
import com.lanier.gameclient.ext.launchSafe
import com.lanier.gameclient.ext.startAct
import com.lanier.gameclient.ext.toast
import com.lanier.gameclient.ext.visible
import com.lanier.gameclient.module.main.MainAct

class EditPetAct(
    override val layoutId: Int = R.layout.activity_edit_pet
) : BaseAct<ActivityEditPetBinding>() {

    private val vm by viewModels<EditPetVM>()

    private var animator: ViewAnimator? = null

    override fun onBind() {
        viewbinding.viewmodel = vm

        launchSafe {
            vm.spiritAction.collect {
                if (it.def.not() && it.idle.isNotEmpty()) {
                    viewbinding.ivEgg.load(it.idle)
                    animator = ViewAnimator
                        .animate(viewbinding.etSetNicknameLayout, viewbinding.btnOK)
                        .alpha(0f, 1f)
                        .duration(800L)
                        .onStart {
                            viewbinding.etSetNicknameLayout.visible()
                            viewbinding.btnOK.visible()
                        }
                        .start()
                }
            }
        }

        viewbinding.ivEgg.setOnClickListener {
            vm.randomSpirit()
        }

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

    override fun onDestroy() {
        super.onDestroy()
        animator?.cancel()
    }
}