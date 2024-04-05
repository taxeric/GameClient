package com.lanier.gameclient.module.plant

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lanier.gameclient.R
import com.lanier.gameclient.databinding.RvItemLandBinding
import com.lanier.gameclient.entity.Land

/**
 * Created by 幻弦让叶
 * Date 2024/4/4 15:50
 */
class LandAdapter : BaseQuickAdapter<Land, BaseDataBindingHolder<RvItemLandBinding>>(
    R.layout.rv_item_land
) {

    init {
        addChildClickViewIds(R.id.btnOperation)
    }

    override fun convert(holder: BaseDataBindingHolder<RvItemLandBinding>, item: Land) {
        holder.dataBinding?.land = item
    }
}