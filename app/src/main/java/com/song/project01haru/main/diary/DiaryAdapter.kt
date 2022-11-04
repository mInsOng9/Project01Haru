package com.song.project01haru.main.diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.song.project01haru.R
import com.song.project01haru.databinding.DiaryRecyclerItemBinding

class DiaryAdapter constructor(var context: Context, var items:MutableList<DiaryItem>):RecyclerView.Adapter<DiaryAdapter.VH>() {


    inner class VH (itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding:DiaryRecyclerItemBinding=DiaryRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemview=LayoutInflater.from(context).inflate(R.layout.diary_recycler_item,parent,false)
        return VH(itemview)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvDay.setText(items[position].day )
        Glide.with(context).load(items[position].feels).into(holder.binding.ivDiaryFeels)
        holder.binding.etDiary.setText(items[position].contents )
//        Glide.with(context).load(items[position].photos).into(holder.binding.ivDiaryPhoto)
    }

    override fun getItemCount(): Int =items.size

}