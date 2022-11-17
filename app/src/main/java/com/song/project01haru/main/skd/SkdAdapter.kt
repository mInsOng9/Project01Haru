package com.song.project01haru.main.skd

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
import com.song.project01haru.databinding.SkdRecyclerItemBinding
import com.song.project01haru.main.expinc.ExpIncAdapter
import com.song.project01haru.main.expinc.ExpIncItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class SkdAdapter constructor(var context:Context, var items:MutableList<SkdEvtItem>):RecyclerView.Adapter<SkdAdapter.VH>() {

    inner class VH (itemView:View):RecyclerView.ViewHolder(itemView){
        val binding=SkdRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView:View=LayoutInflater.from(context).inflate(R.layout.skd_recycler_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvDay.setText(items[position].date)
        holder.binding.tvHoliday.setText(items[position].holiday)
        holder.binding.tvEvent.setText(items[position].event)
        holder.binding.tvSchd.setText(items[position].skd)
        holder.binding.tvSchdNote.setText(items[position].note)
        holder.binding.tvSchdTime.setText(items[position].time)
        holder.binding.ivDelete.setOnClickListener({
            deleteDB(position)
        })
    }

    override fun getItemCount(): Int = items.size

    fun deleteDB(a:Int) {
        val builder = Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitService::class.java)

        val call: Call<String> = builder.deleteSkdItem(G.act, items[a].date)

        call.enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                Toast.makeText(context, ""+response.body(), Toast.LENGTH_SHORT).show()
                Log.e("e",response.body()!!)
                items.removeAt(a)

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }

}