package com.song.project01haru.edit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.song.project01haru.R
import com.song.project01haru.databinding.BsdRecyclerItemBinding

class BsdAdapter(var context: Context, var actItems:MutableList<BsdItem> ): RecyclerView.Adapter<BsdAdapter.VH>() {

    inner class VH (itemView: View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {

                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION && itemClickListner != null){
                    itemClickListner.onItemClick(binding.tvAct,pos)
                }
            }
        }
        val binding:BsdRecyclerItemBinding= BsdRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view=LayoutInflater.from(context).inflate(R.layout.bsd_recycler_item,parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvAct.text=actItems[position].act
//        holder.binding.tvAct.setOnClickListener({
//            var intent: Intent =Intent(context,EditExpFragment::class.java)
//            intent.putExtra("act",actItems[position].act)
//            ContextCompat.startActivity(context,intent,null)
//        })
    }

    override fun getItemCount(): Int = actItems.size

    interface OnItemClickListner{
        fun onItemClick(view: View, position: Int)
    }
    private lateinit var itemClickListner: OnItemClickListner

    fun setOnItemclickListner(onItemClickListner: OnItemClickListner){
        itemClickListner = onItemClickListner
    }
}