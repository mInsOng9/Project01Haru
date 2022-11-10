package com.song.project01haru.main.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.project01haru.R
import com.song.project01haru.databinding.Todo2ndRecyclerItemBinding

class TodoAdapter2 constructor(var context:Context,var items:MutableList<TodoItem>):
    RecyclerView.Adapter<TodoAdapter2.VH>() {

    inner class VH(itemView:View): RecyclerView.ViewHolder(itemView){
        val binding=Todo2ndRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view=LayoutInflater.from(context).inflate(R.layout.todo_2nd_recycler_item,parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.
    }

    override fun getItemCount(): Int = items.size
}