package com.song.project01haru.main.expinc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.song.project01haru.R
import com.song.project01haru.databinding.FragmentExpIncBinding
import java.text.SimpleDateFormat
import java.util.*

class ExpIncFragment:Fragment() {

    lateinit var binding:FragmentExpIncBinding
    var items = mutableListOf<ExpIncItem>()
    lateinit var item : ExpIncItem
    lateinit var day:String
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
        item = ExpIncItem()

        item.day = daySdf.format(Date())
        item.totalInc = "20,000"
        item.totalExp = "10,000"
        item.total = "10,000"
        item.account = R.drawable.type_card
        item.type = "WOORI"
        item.category = "HObby"
        item.note = "hihi"
        item.amount = "+20,000"
        items.add(item)
       recyclerView.adapter = ExpIncAdapter(requireActivity(), items)

    }
//    fun loadDB(){
//        val builder= Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr").addConverterFactory(
//            GsonConverterFactory.create()).build()
//        val retrofitService: RetrofitService = builder.create(RetrofitService::class.java)
//        val call: Call<ArrayList<TodoItem>> = retrofitService.getTodoItem()
//        call.enqueue(object : Callback<ArrayList<TodoItem>> {
//            override fun onResponse(
//                call: Call<ArrayList<TodoItem>>,
//                response: Response<ArrayList<TodoItem>>
//            ) {
//                val items: ArrayList<TodoItem> = response.body()!!
//
//                for (item in items) {
//                    var aaa:List<String> = item.date.split("-")
//                    val a:GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt(), aaa[2].toInt())
//                    item.date= SimpleDateFormat("dd EE").format( a.time )
//                    Toast.makeText(requireActivity(), ""+date+"Dfsd  "+item.date, Toast.LENGTH_SHORT).show()
//                    // todoItems.add(TodoItem(item.date, item.time,item.todo ))
//                    if(item.date.equals(date)){
//                        todoItems.add(TodoItem(item.date,item.time,item.todo))
//                    }
//                }
//
//                recyclerView.adapter= TodoAdapter(requireActivity(),todoItems)
//
//            }
//            override fun onFailure(call: Call<ArrayList<TodoItem>>, t: Throwable) {
//                //Toast.makeText(requireActivity(), "${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
    fun changeDay(day:String){
        items.clear()
        item = ExpIncItem()

        item.day = day
        item.totalInc = "20,000"
        item.totalExp = "10,000"
        item.total = "10,000"
        item.account = R.drawable.type_card
        item.type = "WOORI"
        item.category = "HObby"
        item.note = "hihi"
        item.amount = "+20,000"
        items.add(item)
        recyclerView.adapter = ExpIncAdapter(requireActivity(), items)
    }

    fun changeDays(days:MutableList<String>){
        items.clear()
        days.forEach{ day->
            item.day = day
            item.totalInc = "20,000"
            item.totalExp = "10,000"
            item.total = "10,000"
            item.account = R.drawable.type_card
            item.type = "WOORI"
            item.category = "HObby"
            item.note = "hihi"
            item.amount = "+20,000"
            items.add(item)
        }
        recyclerView.adapter = ExpIncAdapter(requireActivity(), items)

    }
}