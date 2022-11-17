package com.song.project01haru.main.skd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.song.project01haru.G
import com.song.project01haru.databinding.FragmentSchdBinding
import com.song.project01haru.RetrofitService
import com.song.project01haru.main.expinc.ExpIncItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SkdFragment :Fragment() {

    lateinit var binding:FragmentSchdBinding
    var skdevtItems:MutableList<SkdEvtItem> = mutableListOf()
    val recycler by lazy { binding.recycler }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSchdBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        date=SimpleDateFormat("yyyy-MM-dd").format(Date())
        loadDB()
        recycler.adapter= SkdAdapter(requireActivity(),skdevtItems)
    }

    fun loadDB(){
        val builder= Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
        val retrofitService: RetrofitService = builder.create(RetrofitService::class.java)

        val call: Call<ArrayList<SkdEvtItem>> = retrofitService.getSkdItem(G.act,date)
        call.enqueue(object : Callback<ArrayList<SkdEvtItem>> {
            override fun onResponse(
                call: Call<ArrayList<SkdEvtItem>>,
                response: Response<ArrayList<SkdEvtItem>>
            ) {
                val items: ArrayList<SkdEvtItem> = response.body()!!

                for (item in items) {
                    var aaa:List<String> = item.date.split("-")
                    val a: GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt(), aaa[2].toInt())
                    item.date= SimpleDateFormat("dd EE").format( a.time )

                    skdevtItems.add(SkdEvtItem(G.act,item.date,item.time,item.skd,item.note,"",""))

                }
                recycler.adapter= SkdAdapter(requireActivity(),skdevtItems)

            }
            override fun onFailure(call: Call<ArrayList<SkdEvtItem>>, t: Throwable) {
            }
        })
    }//loadDB()




    lateinit var date:String
    fun changeDay(day:Date){
        date=SimpleDateFormat("yyyy-MM-dd").format(day)
        skdevtItems.clear()
        loadDB()
        recycler.adapter= SkdAdapter(requireActivity(),skdevtItems)
    }

    fun changeDays(days:MutableList<String>){
        skdevtItems.clear()
        days.forEach{ day->
            date=day
            loadDB()
        }

        recycler.adapter= SkdAdapter(requireActivity(),skdevtItems)
    }
}