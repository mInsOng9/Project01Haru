package com.song.project01haru.main.home

import android.os.Bundle
import android.util.Log
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
        date=SimpleDateFormat("yyyy-MM-dd").format(Date())
        loadDB(date)
        recyclerView.adapter= HomeAdapter(requireActivity(),items)
        recyclerView.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)

    }


    fun loadDB(date:String){
        val builder=Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        val call: Call<String> = builder.getHomeItem(G.act,date)

        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                Log.e("res",response.body().toString())

                var exp=0.0
                var diary:String=""
                var jo:JSONObject= JSONObject(response.body())
                if(!jo.isNull("exp")) {
                    val expJo=jo.getJSONObject("exp")
                    exp=expJo.get("amount").toString().toDouble()
                }
                var todoJo=jo.getJSONArray("todo")
                var skdJo=jo.getJSONArray("skd")
                if(!jo.isNull("diary")){
                    var diaryJo=jo.getJSONObject("diary")
                    diary=diaryJo.get("content").toString()

                }


                var todo:String =" "
                var time:String =" "
                for(i in 0 until todoJo.length()){
                    var todoJo2= todoJo.getJSONObject(i)

                    todo= todoJo2.get("todo") as String
                    time= todoJo2.get("time") as String
                }

                var skd:String=" "
                var skdTime:String=" "
                var note:String=" "
                var evt:String=""

                if(!jo.isNull("evt")){
                    var evtJo=jo.getJSONObject("evt")
                    evt=evtJo.get("event") as String
                }
                for(i in 0 until skdJo.length()){
                    var skdJo2=skdJo.getJSONObject(i)
//                    if(skdJo2.equals("")) {
//                        todo=" "
//                        time=""
//                        break;
//                    }
                    skd=skdJo2.get("skd") as String
                    skdTime=skdJo2.get("time") as String
                    note= skdJo2.get("note") as String
                }

                var aaa:List<String> = date.split("-")
                val a:GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt()-1, aaa[2].toInt())

                items.add(HomeItem(daySdf.format(a.time)," ", evt, skd,note, skdTime,todo,exp,diary," "))

                binding.recycler.adapter = HomeAdapter(requireActivity(), items)

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
        loadDB(date)
    }//changeDay(..)

    fun changeDays(days:MutableList<String>){
        items.clear()
        days.forEach{ day->
            date=day
            loadDB(date)
        }

    }//changeDays(..)
}