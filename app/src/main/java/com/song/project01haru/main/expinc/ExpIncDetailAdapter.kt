package com.song.project01haru.main.expinc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.song.project01haru.R
import com.song.project01haru.databinding.ExpincDetailRecyclerItemBinding

class ExpIncDetailAdapter(var context:Context, var items: MutableList<ExpIncDetailItem>?):RecyclerView.Adapter<ExpIncDetailAdapter.VH>() {

    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        var binding=ExpincDetailRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view=LayoutInflater.from(context).inflate(R.layout.expinc_detail_recycler_item,parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(items?.get(position)?.account).into(holder.binding.ivAccount)
        holder.binding.tvType.setText(items?.get(position)?.type  )
        holder.binding.tvCategory.setText(items?.get(position)?.category )
        holder.binding.tvExpincNote.setText(items?.get(position)?.note)
        holder.binding.tvAmount.setText(items?.get(position)?.amount.toString())    }

    override fun getItemCount(): Int = items?.size!!
}