package com.song.project01haru

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.project01haru.databinding.RecyclerItemBinding

class HomeAdapter constructor(val context: Context,var items:HomeItem): RecyclerView.Adapter<HomeAdapter.VH>() {



    lateinit var binding:RecyclerItemBinding
    inner class VH constructor(itemView:View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}