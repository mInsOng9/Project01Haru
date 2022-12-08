package com.song.project01haru.main.expinc

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.song.project01haru.G
import com.song.project01haru.R
import com.song.project01haru.RetrofitService
import com.song.project01haru.databinding.ExpincRecyclerItemBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class ExpIncAdapter constructor(var context:Context, var items:MutableList<ExpIncItem>) : RecyclerView.Adapter<ExpIncAdapter.VH>() {

    var detailItems=mutableListOf<ExpIncDetailItem>()
    val daySdf= SimpleDateFormat("dd EE")


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


        holder.binding.recyclerDetail.adapter=ExpIncDetailAdapter(context, items[position].detailItems)

    }

    override fun getItemCount(): Int =items.size


}