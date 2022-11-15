package com.song.project01haru.main.expinc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.song.project01haru.R
import com.song.project01haru.databinding.ExpincRecyclerItemBinding

class ExpIncAdapter constructor(var context:Context, var items:MutableList<ExpIncItem>) : RecyclerView.Adapter<ExpIncAdapter.VH>() {

    inner class VH constructor(itemView:View) : RecyclerView.ViewHolder(itemView){
        val binding=ExpincRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView=LayoutInflater.from(context).inflate(R.layout.expinc_recycler_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvDay.setText(items[position].date)
        holder.binding.tvMosInc.setText("+"+items[position].totalInc)
        holder.binding.tvMosExp.setText("-"+items[position].totalExp)
        holder.binding.tvMosTotal.setText(items[position].total)
        Glide.with(context).load(items[position].account).into(holder.binding.ivAccount)
        holder.binding.tvType.setText(items[position].type)
        holder.binding.tvCategory.setText(items[position].category)
        holder.binding.tvExpincNote.setText(items[position].note)
        holder.binding.tvAmount.setText(items[position].amount)
    }

    override fun getItemCount(): Int =items.size
}