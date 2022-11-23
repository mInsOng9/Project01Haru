package com.song.project01haru.edit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.song.project01haru.R
import com.song.project01haru.databinding.DialogPhotoBinding
import com.song.project01haru.databinding.DialogPhotoPagerBinding
import okhttp3.MultipartBody
import retrofit2.http.Multipart

class DiaryPagerAdapter(var context: Context, var items:MutableList<String>):
    RecyclerView.Adapter<DiaryPagerAdapter.VH>() {

    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        var binding=DialogPhotoBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view=LayoutInflater.from(context).inflate(R.layout.dialog_photo,parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(items.get(position)).into(holder.binding.ivPager)
    }

    override fun getItemCount(): Int = items.size
}