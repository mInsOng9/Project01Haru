package com.song.project01haru.main.expinc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.song.project01haru.G
import com.song.project01haru.databinding.FragmentExpIncBinding
import com.song.project01haru.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class ExpIncFragment:Fragment() {

    lateinit var binding:FragmentExpIncBinding
    var expincItems = mutableListOf<ExpIncItem>()
    val recyclerView by lazy {binding.recycler}

    val daySdf= SimpleDateFormat("dd EE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

          //  day=arguments?.getStringArray("date").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentExpIncBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        day = arguments?.getString("date").toString()
//
        date=SimpleDateFormat("yyyy-MM-dd").format(Date())
        loadDB()

        recyclerView.adapter = ExpIncAdapter(requireActivity(), expincItems)

    }
    fun loadDB(){
        val builder= Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitService::class.java)

        val call: Call<ArrayList<ExpIncItem>> = builder.getExpIncItem(G.act,date)

        call.enqueue(object : Callback<ArrayList<ExpIncItem>> {
            override fun onResponse(
                call: Call<ArrayList<ExpIncItem>>,
                response: Response<ArrayList<ExpIncItem>>
            ) {
                val items: ArrayList<ExpIncItem> = response.body()!!

                for (item in items) {
                    var aaa:List<String> = item.date.split("-")
                    val a:GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt(), aaa[2].toInt())
                    item.date= daySdf.format( a.time )

                    expincItems.add(ExpIncItem(G.act,item.date,item.totalInc,item.totalExp,item.total,item.amount,item.account,item.type,item.category,item.note))


                }

                recyclerView.adapter= ExpIncAdapter(requireActivity(), expincItems)

            }
            override fun onFailure(call: Call<ArrayList<ExpIncItem>>, t: Throwable) {
            }
        })
    }

    lateinit var date:String
    fun changeDay(day:Date){
        expincItems.clear()
        date=SimpleDateFormat("yyyy-MM-dd").format(day)
        loadDB()
        recyclerView.adapter = ExpIncAdapter(requireActivity(), expincItems)
    }

    fun changeDays(days:MutableList<String>){
        expincItems.clear()
        days.forEach{ day->
            date=day
            loadDB()
        }
        recyclerView.adapter = ExpIncAdapter(requireActivity(), expincItems)

    }
}