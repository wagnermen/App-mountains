package com.example.touristplaces.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.MountainsListModel
import com.example.touristplaces.databinding.ItemMountainsBinding

class MountainsAdapter(private var mountains: List<MountainsListModel>, private val listener:
    (MountainsListModel) -> Unit) : RecyclerView.Adapter<MountainsAdapter.MountainsHolder>() {

    inner class MountainsHolder(mViewBinding: ItemMountainsBinding) : RecyclerView.ViewHolder(mViewBinding.root) {
        val imgMountain: ImageView = mViewBinding.imgMountain
        val title: TextView = mViewBinding.tvTitle
        val climber: TextView = mViewBinding.tvFirstClimber
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MountainsHolder = MountainsHolder(ItemMountainsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: MountainsHolder,
        position: Int
    ) {
        val item = mountains[position]
        holder.imgMountain.load("${item.image}")
        holder.title.text = item.name
        holder.climber.text = "Pais:" + item.country

        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount(): Int {
        return mountains.size
    }

}