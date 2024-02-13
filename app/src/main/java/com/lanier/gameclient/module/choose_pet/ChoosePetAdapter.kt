package com.lanier.gameclient.module.choose_pet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.lanier.gameclient.R
import com.lanier.gameclient.entity.Pet
import com.lanier.gameclient.module.callback.OnItemClickListener
import com.lanier.gameclient.utils.ColorUtil

class ChoosePetAdapter : RecyclerView.Adapter<ChoosePetAdapter.ViewHolder>() {

    private val _data : MutableList<Pet> = mutableListOf()
    var data: List<Pet>
        set(value) {
            _data.clear()
            _data.addAll(value)
            notifyDataSetChanged()
        }
        get() = _data

    var onItemClickListener : OnItemClickListener<Pet>? = null

    private var currentSelectedIndex = -1

    fun getSelectedPet() = if (currentSelectedIndex < 0) Pet.def else _data[currentSelectedIndex]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_choose_pet, parent, false)
        )
    }

    override fun getItemCount() = _data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.avatar.setBackgroundColor(ColorUtil.randomColor())
        holder.name.text = _data[position].name
        if (_data[position].selected) {
            holder.rootLayout.setBackgroundResource(R.drawable.shape_transparent_corner_8_all_border_primary_2)
        } else {
            holder.rootLayout.background = null
        }
        holder.itemView.setOnClickListener {
            if (currentSelectedIndex == position) {
                return@setOnClickListener
            }
            if (currentSelectedIndex != -1) {
                _data[currentSelectedIndex].selected = false
                notifyItemChanged(currentSelectedIndex, Any())
            }
            currentSelectedIndex = position
            _data[position].selected = true
            notifyItemChanged(currentSelectedIndex, Any())
            onItemClickListener?.onItemClick(position, _data[position])
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val rootLayout = view.findViewById<LinearLayout>(R.id.rootLayout)
        val avatar = view.findViewById<ShapeableImageView>(R.id.ivAvatar)
        val name = view.findViewById<TextView>(R.id.tvName)
    }
}