package com.song.project01haru.main.expinc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

class ExpIncDetailFragment:Fragment() {

    lateinit var binding: ExpincRecyclerItemBinding
    var items = mutableListOf<ExpIncDetailItem>()
    val recyclerView by lazy {binding.recyclerDetail}

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
        binding= ExpincRecyclerItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        day = arguments?.getString("date").toString()
//
        date= SimpleDateFormat("yyyy-MM-dd").format(Date())
        items.add(ExpIncDetailItem("2022-10-23",200.0,R.drawable.type_cash,"NH","Hi","hihi"))
        loadDB(date)
        recyclerView.adapter = ExpIncDetailAdapter(requireActivity(), items)

    }
    fun loadDB(date:String){
        val builder= Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)

        val call: Call<String> = builder.getExpIncItem(G.act,date)

        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                Log.e("res",response.body().toString())

                var exp=0.0
                var inc=0.0
                var category=""
                var type=""
                var account= R.drawable.type_card
                var note=""

                var aaa:List<String> = date.split("-")
                val a: GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt(), aaa[2].toInt())

                var jo: JSONObject = JSONObject(response.body())

                if(!jo.isNull("exp")) {
                    var expJo = jo.getJSONArray("exp")
                    for (i in 0 until expJo.length()) {
                        val expJo2 = expJo.getJSONObject(i)
                        exp = expJo2.get("amount").toString().toDouble()
                        category = expJo2.get("category") as String
                        type = expJo2.get("type") as String
                        account = expJo2.get("account").toString().toInt()
                        note = expJo2.get("note") as String
                        items.add(ExpIncDetailItem(daySdf.format(a.time),exp,account,type,category,note))

                    }

                }

                if(!jo.isNull("inc")){
                    var incJo=jo.getJSONArray("inc")
                    for( i in 0 until incJo.length()){
                        val incJo2=incJo.getJSONObject(i)
                        inc=incJo2.get("amount").toString().toDouble()
                        category=incJo2.get("category") as String
                        type=incJo2.get("type") as String
                        account=incJo2.get("account").toString().toInt()
                        note=incJo2.get("note") as String
                        items.add(ExpIncDetailItem(daySdf.format(a.time),inc,account,type,category,note))

                    }
                }

                recyclerView.adapter = ExpIncDetailAdapter(requireActivity(), items)

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    lateinit var date:String
    fun changeDay(day: Date){
        items.clear()
        date= SimpleDateFormat("yyyy-MM-dd").format(day)
        loadDB(date)
        recyclerView.adapter = ExpIncDetailAdapter(requireActivity(), items)
    }

    fun changeDays(days:MutableList<String>){
        items.clear()
        days.forEach{ day->
            date=day
            loadDB(date)
        }
        recyclerView.adapter =ExpIncDetailAdapter(requireActivity(), items)

    }
}