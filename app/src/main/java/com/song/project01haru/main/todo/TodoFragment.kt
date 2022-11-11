package com.song.project01haru.main.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.song.project01haru.databinding.FragmentTodoBinding
import com.song.project01haru.edit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TodoFragment : Fragment() {

    lateinit var binding:FragmentTodoBinding
    var todoItems:MutableList<TodoItem> = mutableListOf()
    val recyclerView by lazy{binding.recycler}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView.adapter= TodoAdapter(requireActivity(),todoItems)
    }

    fun loadDB(){
        val builder=Retrofit.Builder().baseUrl("http://mins22.dothome.co.kr").addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build()
        val retrofitService: RetrofitService = builder.create(RetrofitService::class.java)

//        val call:Call<String> =retrofitService.getTodoItem2(date)
//        call.enqueue(object:Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                val s=response.body()
//                AlertDialog.Builder(requireContext()).setMessage(s).show()
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
//            }
//
//        })
        val call: Call<ArrayList<TodoItem>> = retrofitService.getTodoItem(date)
        call.enqueue(object : Callback<ArrayList<TodoItem>> {
            override fun onResponse(
                call: Call<ArrayList<TodoItem>>,
                response: Response<ArrayList<TodoItem>>
            ) {
                val items: ArrayList<TodoItem> = response.body()!!

                for (item in items) {
                    var aaa:List<String> = item.date.split("-")
                    val a:GregorianCalendar = GregorianCalendar(aaa[0].toInt(), aaa[1].toInt(), aaa[2].toInt())
                    item.date= SimpleDateFormat("dd EE").format( a.time )

                    Toast.makeText(requireActivity(), ""+date+"Dfsd  "+item.date, Toast.LENGTH_SHORT).show()
                   // todoItems.add(TodoItem(item.date, item.time,item.todo ))
                    //if(item.date.equals(date)){
                        todoItems.add(TodoItem(item.date,item.time,item.todo))
                    //}
                }

               recyclerView.adapter= TodoAdapter(requireActivity(),todoItems)

            }
            override fun onFailure(call: Call<ArrayList<TodoItem>>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    lateinit var date:String
    fun changeDay(day:Date) {

        date=SimpleDateFormat("yyyy-MM-dd").format(day)
        todoItems.clear()
       //todoItems.add(TodoItem(day,time,todo))
        loadDB()
        recyclerView.adapter= TodoAdapter(requireActivity(),todoItems)
    }

    fun changeDays(days:MutableList<String>){
        todoItems.clear()
        days.forEach{ day->
            date=day
            loadDB()
        }

        recyclerView.adapter= TodoAdapter(requireActivity(),todoItems)
    }
}