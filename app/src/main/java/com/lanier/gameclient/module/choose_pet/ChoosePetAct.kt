package com.lanier.gameclient.module.choose_pet

import androidx.recyclerview.widget.GridLayoutManager
import com.github.florent37.viewanimator.ViewAnimator
import com.lanier.gameclient.R
import com.lanier.gameclient.base.BaseAct
import com.lanier.gameclient.base.data.UserData
import com.lanier.gameclient.databinding.ActivityChoosePetBinding
import com.lanier.gameclient.entity.Pet
import com.lanier.gameclient.ext.startAct
import com.lanier.gameclient.ext.toast
import com.lanier.gameclient.ext.visible
import com.lanier.gameclient.module.callback.OnItemClickListener
import com.lanier.gameclient.module.main.MainAct
import com.lanier.gameclient.utils.cube.LocalDisplay

class ChoosePetAct(
    override val layoutId: Int = R.layout.activity_choose_pet
) : BaseAct<ActivityChoosePetBinding>() {

    private var chooseCompletedAnim: ViewAnimator? = null

    private val mAdapter = ChoosePetAdapter().apply {
        onItemClickListener = object : OnItemClickListener<Pet> {
            override fun onItemClick(index: Int, data: Pet) {
                UserData.notifyPet(data)
                if (chooseCompletedAnim == null) {
                    showCompletedView()
                }
            }
        }
    }

    override fun onBind() {
        viewbinding.btnCompleted.setOnClickListener {
            if (UserData.curPet.def) {
                toast("请选择宠物～")
                return@setOnClickListener
            }
            startAct<MainAct> {  }
            finish()
        }
    }

    override fun initData() {
        viewbinding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(this@ChoosePetAct, 4)
        }
        mAdapter.data = UserData.pets
    }

    override fun onDestroy() {
        super.onDestroy()
        chooseCompletedAnim?.cancel()
    }

    private fun showCompletedView() {
        chooseCompletedAnim = ViewAnimator.animate(viewbinding.btnCompleted)
            .translationY(LocalDisplay.SCREEN_HEIGHT_PIXELS + 0f, 0f)
            .duration(1000L)
            .onStart { viewbinding.btnCompleted.visible() }
            .start()
    }
}