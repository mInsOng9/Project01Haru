package com.song.project01haru

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.song.project01haru.databinding.HomeRecyclerItemBinding

class HomeAdapter constructor(val context: Context,var items:MutableList<HomeItem>): RecyclerView.Adapter<HomeAdapter.VH>() {



    lateinit var binding:HomeRecyclerItemBinding
    inner class VH constructor(binding: HomeRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){


        var day=binding.tvDay
        var holiday=binding.tvHoliday
        var event=binding.tvEvent
        var schd=binding.tvSchd
        var schdNote=binding.tvSchdNote
        var schdTime=binding.tvSchdTime
        var todo=binding.tvTodo
        var exp=binding.tvExp
        var diary=binding.etDiary
        var feels=binding.ivDiaryFeels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView=LayoutInflater.from(context).inflate(R.layout.home_recycler_item,parent,false)
        return VH(HomeRecyclerItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:HomeItem=items.get(position)
        holder.day.setText(item.day)
        holder.holiday.setText(item.holiday)
        holder.event.setText(item.event)
        holder.schd.setText(item.schd)
        holder.schdNote.setText(item.schdNote)
        holder.schdTime.setText(item.schdTime)
        holder.todo.setText(item.todo)
        holder.exp.setText(item.exp)
        holder.diary.setText(item.diary)
        Glide.with(context).load(item.feels).into(holder.feels)
    }

    override fun getItemCount(): Int = items.size
}