package com.song.project01haru.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.song.project01haru.G
import com.song.project01haru.R
import com.song.project01haru.RetrofitService
import com.song.project01haru.databinding.FragmentHomeBinding
import com.song.project01haru.main.expinc.ExpIncAdapter
import com.song.project01haru.main.expinc.ExpIncItem
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var items:MutableList<HomeItem> = mutableListOf<HomeItem>()

    val daySdf= SimpleDateFormat("dd EE")
    //recyclerview
    val recyclerView by lazy {binding.recycler}


    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //add items
//        items.add(HomeItem("24 MON","hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))
//        items.add(HomeItem("24 MON","hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))
       items.add(HomeItem(daySdf.format(Date()),"hi","HI","Nothing","non","20:00","Sleep","20,0000","How areyouuu",null))

        recyclerView.adapter= HomeAdapter(requireActivity(),items)
        recyclerView.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)

    }


    fun loadDB(){
        val builder=Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        val call: Call<String> = builder.getHomeItem(G.act,date)

        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                var jsonArray: JSONArray = JSONArray(response.body())

                for( i in 0 until jsonArray.length()){
                    var jo: JSONObject =jsonArray.getJSONObject(i)
                    var aaa:List<String> = jo.get("date").toString().split("-")
                    val a:GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt(), aaa[2].toInt())
                    var date= SimpleDateFormat("dd EE").format( a.time )

                    var event:String=jo.get("event").toString()
                    var skd:String=jo.get("skd").toString()
                    var skdNote:String = jo.get("note").toString()
//                    var skdTime:String = jo.getJSONObject("")
                    var todo:String=jo.get("todo").toString()
                    var exp:String=jo.get("expTotal").toString()
                    var diary:String=jo.get("content").toString()
//                    var feels:String=jo.get("locdate").toString()
                    items.add(HomeItem(date,"",event,skd,skdNote,"",todo,exp,diary,""))
                    Toast.makeText(requireContext(), ""+skd+"  "+event, Toast.LENGTH_SHORT).show()
                }
                recyclerView.adapter= HomeAdapter(requireActivity(),items)

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }


    lateinit var date:String
    fun changeDay(day:Date){
        date=SimpleDateFormat("yyyy-MM-dd").format(day)
        items.clear()
        loadDB()
        binding.recycler.adapter = HomeAdapter(requireActivity(), items)
    }//changeDay(..)

    fun changeDays(days:MutableList<String>){
        items.clear()
        days.forEach{ day->
            date=day
            loadDB()
        }
        binding.recycler.adapter = HomeAdapter(requireActivity(), items)

    }//changeDays(..)
}