package com.song.project01haru.main.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.song.project01haru.G
import com.song.project01haru.databinding.FragmentDiaryBinding
import com.song.project01haru.RetrofitService
import com.song.project01haru.main.todo.TodoAdapter
import com.song.project01haru.main.todo.TodoItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class DiaryFragment : Fragment(){

    lateinit var binding:FragmentDiaryBinding
    var diaryItems:MutableList<DiaryItem> = mutableListOf()
    val recyclerView by lazy {binding.recycler}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDiaryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        date=SimpleDateFormat("yyyy-MM-dd").format(Date())
        loadDB()
        recyclerView.adapter= DiaryAdapter(requireActivity(),diaryItems)
    }

    fun loadDB(){
        val builder=Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr").addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build()
        val retrofitService: RetrofitService = builder.create(RetrofitService::class.java)

        val call: Call<ArrayList<DiaryItem>> = retrofitService.getDiaryItem(G.act,date)
        call.enqueue(object : Callback<ArrayList<DiaryItem>> {
            override fun onResponse(
                call: Call<ArrayList<DiaryItem>>,
                response: Response<ArrayList<DiaryItem>>
            ) {
                val items: ArrayList<DiaryItem> = response.body()!!

                for (item in items) {
                    var aaa:List<String> = item.date.split("-")
                    val a:GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt(), aaa[2].toInt())
                    item.date= SimpleDateFormat("dd EE").format( a.time )

                    // todoItems.add(TodoItem(item.date, item.time,item.todo ))
                    //if(item.date.equals(date)){
                    diaryItems.add(DiaryItem(G.act,item.date,item.icon,item.content,item.img,item.map_lat,item.map_long))
                    //}
                }

                recyclerView.adapter= DiaryAdapter(requireActivity(),diaryItems)

            }
            override fun onFailure(call: Call<ArrayList<DiaryItem>>, t: Throwable) {
            }
        })
    }
    lateinit var date:String
    fun changeDay(day:Date){
        date=SimpleDateFormat("yyyy-MM-dd").format(day)
        diaryItems.clear()
        loadDB()
        recyclerView.adapter= DiaryAdapter(requireActivity(),diaryItems)
    }

    fun changeDays(days:MutableList<String>){
        diaryItems.clear()
        days.forEach{ day->
            date=day
            loadDB()
        }
        recyclerView.adapter= DiaryAdapter(requireActivity(),diaryItems)
    }
}