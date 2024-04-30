package com.lanier.gameclient.module.backpack

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.BaseLoadMoreModule
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lanier.gameclient.R
import com.lanier.gameclient.databinding.RvItemBackapckBinding
import com.lanier.gameclient.entity.Backpack

/**
 * Created by 幻弦让叶
 * Date 2024/3/16 1:31
 */
class BackpackAdapter : BaseQuickAdapter<Backpack, BaseDataBindingHolder<RvItemBackapckBinding>>(
    R.layout.rv_item_backapck
), LoadMoreModule {
    override fun convert(holder: BaseDataBindingHolder<RvItemBackapckBinding>, item: Backpack) {
        holder.dataBinding?.item = item
    }

    override fun addLoadMoreModule(baseQuickAdapter: BaseQuickAdapter<*, *>): BaseLoadMoreModule {
        return super.addLoadMoreModule(baseQuickAdapter)
    }
}