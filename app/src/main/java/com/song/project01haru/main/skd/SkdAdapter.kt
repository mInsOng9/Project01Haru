package com.song.project01haru.main.skd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.project01haru.R
import com.song.project01haru.databinding.SkdRecyclerItemBinding

class SkdAdapter constructor(var context:Context, var items:MutableList<SkdItem>):RecyclerView.Adapter<SkdAdapter.VH>() {

    inner class VH (itemView:View):RecyclerView.ViewHolder(itemView){
        val binding=SkdRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView:View=LayoutInflater.from(context).inflate(R.layout.skd_recycler_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvDay.setText(items[position].day)
        holder.binding.tvHoliday.setText(items[position].holiday)
        holder.binding.tvEvent.setText(items[position].event)
        holder.binding.tvSchd.setText(items[position].skd)
        holder.binding.tvSchdNote.setText(items[position].note)
        holder.binding.tvSchdTime.setText(items[position].time)
    }

    override fun getItemCount(): Int = items.size


}