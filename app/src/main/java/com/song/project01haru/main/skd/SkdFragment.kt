package com.song.project01haru.main.skd

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.song.project01haru.G
import com.song.project01haru.RestItem
import com.song.project01haru.databinding.FragmentSchdBinding
import com.song.project01haru.RetrofitService
import com.song.project01haru.main.expinc.ExpIncItem
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SkdFragment :Fragment() {

    lateinit var binding:FragmentSchdBinding
    var skdevtItems:MutableList<SkdEvtItem> = mutableListOf()
    val recycler by lazy { binding.recycler }
    val daySdf=SimpleDateFormat("dd EE")



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
        loadDB(date)
        recycler.adapter= SkdAdapter(requireActivity(),skdevtItems)


//
    }

    fun loadDB(date:String){
        val builder= Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
        val retrofitService: RetrofitService = builder.create(RetrofitService::class.java)

        val call: Call<String> = retrofitService.getSkdItem(G.act,date)
        call.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {

                Log.e("res",response.body().toString())

                var jo:JSONObject= JSONObject(response.body())

                var evt:String=""
                var skd:String=""
                var note:String=""
                var time:String=""

                var skdJo=jo.getJSONArray("skd")
                if(!jo.isNull("evt")){
                    var evtJo=jo.getJSONObject("evt")
                    evt=evtJo.get("event") as String
                }

                for(i in 0 until skdJo.length()){
                    var skdJo2=skdJo.getJSONObject(i)
                    skd=skdJo2.get("skd") as String
                    note=skdJo2.get("note") as String
                    time=skdJo2.get("time") as String
                }

                //Date
                var aaa:List<String> = date.split("-")
                val a:GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt(), aaa[2].toInt())

                skdevtItems.add(SkdEvtItem(G.act,daySdf.format(a.time),time, skd,note,evt,""))


                recycler.adapter= SkdAdapter(requireActivity(),skdevtItems)

            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }//loadDB()




    lateinit var date:String
    fun changeDay(day:Date){
        date=SimpleDateFormat("yyyy-MM-dd").format(day)
        skdevtItems.clear()
        loadDB(date)
    }

    fun changeDays(days:MutableList<String>){
        skdevtItems.clear()
        days.forEach{ day->
            date=day
            loadDB(date)
        }
    }
}