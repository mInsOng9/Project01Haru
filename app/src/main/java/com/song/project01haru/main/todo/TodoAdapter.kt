package com.song.project01haru.main.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.project01haru.R
import com.song.project01haru.databinding.TodoRecyclerItemBinding

class TodoAdapter constructor(var context: Context, var items:MutableList<TodoItem> ) : RecyclerView.Adapter<TodoAdapter.VH>() {



    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding=TodoRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView=LayoutInflater.from(context).inflate(R.layout.todo_recycler_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.binding.tvDay.setText(items[position].day)
        holder.binding.tvTodo.setText(items[position].todo)

    }

    override fun getItemCount(): Int = items.size
}