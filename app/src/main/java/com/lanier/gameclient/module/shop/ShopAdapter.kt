package com.lanier.gameclient.module.shop

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.BaseLoadMoreModule
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.lanier.gameclient.R
import com.lanier.gameclient.databinding.RvItemMarketBinding
import com.lanier.gameclient.entity.MarketItem

/**
 * Created by 幻弦让叶
 * Date 2024/3/2 16:00
 */
class ShopAdapter : BaseQuickAdapter<MarketItem, BaseDataBindingHolder<RvItemMarketBinding>>(
    R.layout.rv_item_market
), LoadMoreModule {
    override fun convert(holder: BaseDataBindingHolder<RvItemMarketBinding>, item: MarketItem) {
        holder.dataBinding?.tvName?.text = item.name
    }

    override fun addLoadMoreModule(baseQuickAdapter: BaseQuickAdapter<*, *>): BaseLoadMoreModule {
        return super.addLoadMoreModule(baseQuickAdapter)
    }
}