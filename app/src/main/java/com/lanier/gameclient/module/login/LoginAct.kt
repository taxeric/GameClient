package com.lanier.gameclient.module.login

import androidx.activity.viewModels
import com.github.florent37.viewanimator.ViewAnimator
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.databinding.ActivityLoginBinding
import com.lanier.gameclient.ext.invisible
import com.lanier.gameclient.ext.listenAllPreferences
import com.lanier.gameclient.ext.saveToPreferences
import com.lanier.gameclient.ext.startAct
import com.lanier.gameclient.ext.toast
import com.lanier.gameclient.ext.visible
import com.lanier.gameclient.module.dialog.BSDFEditHost
import com.lanier.gameclient.module.register.RegisterAct
import com.lanier.gameclient.net.API
import com.lanier.gameclient.preference.getStringValue
import com.lanier.gameclient.utils.cube.LocalDisplay

class LoginAct(
    override val layoutId: Int = R.layout.activity_login
) : BaseAct<ActivityLoginBinding>() {

    private val vm by viewModels<LoginVM>()

    private var enterAnimator: ViewAnimator? = null
    private var exitAnimator: ViewAnimator? = null
    private var settingAnimator: ViewAnimator? = null

    override fun onBind() {
        viewbinding.viewmodel = vm

        viewbinding.btnLogin.setOnClickListener {
            vm.login()
        }
        viewbinding.ivTips.setOnClickListener {
            showModifyDialog()
        }
        viewbinding.tvRegister.setOnClickListener {
            startAct<RegisterAct> {  }
        }
        viewbinding.ivSetting.setOnClickListener {
            showModifyDialog(false)
        }

        vm.error.observe(this) {
            if (it.isNotEmpty()) {
                toast(it)
            }
        }

        listenAllPreferences {
            val url = it.getStringValue("baseUrl")
            if (url.isEmpty()) {
                tipsViewEnterAnim()
            } else {
                API.requestHostUrl = url
                settingAnim()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        enterAnimator?.cancel()
        exitAnimator?.cancel()
        settingAnimator?.cancel()
    }

    private fun showModifyDialog(withCatAnim: Boolean = true) {
        BSDFEditHost.show(supportFragmentManager, API.requestHostUrl) {
            if (it.isNotEmpty()) {
                saveToPreferences("baseUrl", it)
                if (withCatAnim) {
                    tipsViewExitAnim()
                }
            }
        }
    }

    private fun settingAnim() {
        settingAnimator = ViewAnimator
            .animate(viewbinding.ivSetting)
            .alpha(0f, 1f)
            .duration(1000L)
            .onStart {
                viewbinding.ivSetting.visible()
            }
            .start()
    }

    private fun tipsViewEnterAnim() {
        enterAnimator = ViewAnimator
            .animate(viewbinding.ivTips)
            .translationY(LocalDisplay.SCREEN_HEIGHT_PIXELS + 0f, 0f)
            .duration(1000L)
            .onStart {
                viewbinding.ivTips.visible()
            }
            .start()
    }

    private fun tipsViewExitAnim() {
        exitAnimator = ViewAnimator
            .animate(viewbinding.ivTips)
            .translationY(0f, LocalDisplay.SCREEN_HEIGHT_PIXELS + 0f)
            .duration(1000L)
            .onStop {
                viewbinding.ivTips.invisible()
            }
            .start()
    }
}